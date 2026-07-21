#!/bin/bash
# =============================================================
# Qi Hop LLM 助手独立部署脚本（LiteLLM proxy + Qdrant 向量库）
# 总结：独立部署 LLM 助手栈（LiteLLM 代理 + Qdrant 向量库，需 Docker + 临时联网）
#
# 设计定位：
#   - 不在主离线包（hop-package.sh）内，单独执行
#   - 目标机器需要临时联网（用于 docker pull）
#   - 部署完成后可断网运行（除非上游 LLM API 需要公网）
#
# 与主部署的关系：
#   hop-deploy.sh    → 部署 Hop Web（主机原生，无网络）
#   hop-deploy-llm.sh → 部署 LLM 助手栈（Docker，需临时联网）  ← 本脚本
#
# 架构：
#   hop-web (主机, 8080) ──HTTP──> litellm (容器, 4000) ──> OpenAI-compatible endpoint
#                            └──HTTP──> qdrant (容器, 6333)
#
# 支持的上游提供商：
#   - vLLM (本地部署)
#   - DashScope OpenAI 兼容模式（阿里云通义千问）
#   - OpenAI API
#   - 任何符合 OpenAI /v1/chat/completions 规范的 LLM 服务
#
# 前置条件：
#   1. 已安装 Docker 24+ 与 docker compose v2（或 docker-compose v1）
#   2. 服务器可临时访问公网（ghcr.io、docker.io）
#   3. 已申请上游 LLM API Key（DashScope、OpenAI 等）或部署好 vLLM
#   4. Hop Web 已通过 hop-deploy.sh 部署完毕（可选，顺序无强约束）
#
# 用法：
#   ./hop-deploy-llm.sh                              # 交互式（引导填写 API Key）
#   ./hop-deploy-llm.sh --api-key sk-xxx             # 直接传入 API Key
#   ./hop-deploy-llm.sh --api-key sk-xxx \
#       --api-base http://vllm:8000/v1               # 指定上游端点（如 vLLM）
#   ./hop-deploy-llm.sh --api-base https://api.openai.com/v1  # 使用 OpenAI
#   ./hop-deploy-llm.sh --model qwen-plus            # 指定默认模型
#   ./hop-deploy-llm.sh --port 4001                  # litellm 端口
#   ./hop-deploy-llm.sh --base /opt/hop-llm          # 安装目录
#   ./hop-deploy-llm.sh --skip-pull                  # 跳过镜像拉取（已离线导入时）
#   ./hop-deploy-llm.sh --uninstall                  # 停止并清理 LLM 栈
#   ./hop-deploy-llm.sh --status                     # 查看 LLM 栈状态
# =============================================================

set -Euo pipefail

# --------------------- 颜色与日志 ---------------------
RED='\033[0;31m'; GREEN='\033[0;32m'; YELLOW='\033[1;33m'; BLUE='\033[0;34m'; NC='\033[0m'
log()  { echo -e "${GREEN}[$(date '+%H:%M:%S')]${NC} $*"; }
warn() { echo -e "${YELLOW}[$(date '+%H:%M:%S')] WARN:${NC} $*"; }
err()  { echo -e "${RED}[$(date '+%H:%M:%S')] ERROR:${NC} $*" >&2; }
info() { echo -e "${BLUE}[$(date '+%H:%M:%S')]${NC} $*"; }

# --------------------- 默认配置 ---------------------
INSTALL_BASE="/opt/hop-llm"
LLM_API_KEY=""
LLM_API_BASE="https://dashscope.aliyuncs.com/compatible-mode/v1"
DEFAULT_MODEL="qwen-plus"
LITELLM_PORT="4000"
QDRANT_HTTP_PORT="6333"
QDRANT_GRPC_PORT="6334"
SKIP_PULL=false
ACTION="deploy"
IMPORT_IMAGES_DIR=""

# 脚本所在目录（用于查找同目录的 docker-compose.llm.yml 与 litellm-config.yaml）
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# --------------------- 参数解析 ---------------------
while [[ $# -gt 0 ]]; do
    case "$1" in
        --api-key)    LLM_API_KEY="$2"; shift 2 ;;
        --api-base)   LLM_API_BASE="$2"; shift 2 ;;
        --model)      DEFAULT_MODEL="$2"; shift 2 ;;
        --port)       LITELLM_PORT="$2"; shift 2 ;;
        --qdrant-port) QDRANT_HTTP_PORT="$2"; shift 2 ;;
        --base)         INSTALL_BASE="$2"; shift 2 ;;
        --skip-pull)  SKIP_PULL=true; shift ;;
        --import-images) IMPORT_IMAGES_DIR="$2"; shift 2 ;;
        --uninstall)  ACTION="uninstall"; shift;;
        --status)     ACTION="status"; shift ;;
        -h|--help)    sed -n '2,43p' "$0"; exit 0 ;;
        *) err "未知参数: $1"; exit 1 ;;
    esac
done

# Docker compose 命令探测（v2 优先，回退 v1）
detect_compose_cmd() {
    if docker compose version >/dev/null 2>&1; then
        COMPOSE_CMD=(docker compose)
    elif command -v docker-compose >/dev/null 2>&1; then
        COMPOSE_CMD=(docker-compose)
    else
        err "未检测到 docker compose（v2）或 docker-compose（v1）"
        err "请安装 Docker Engine 24+ 或 docker-compose 独立二进制"
        exit 1
    fi
}

# --------------------- 前置检查 ---------------------
preflight() {
    log "前置检查..."

    if [ "$EUID" -ne 0 ]; then
        warn "建议使用 root 或 sudo 执行（安装目录: ${INSTALL_BASE}）"
    fi

    if ! command -v docker >/dev/null 2>&1; then
        err "未检测到 docker 命令，请先安装 Docker Engine 24+"
        exit 1
    fi

    detect_compose_cmd

    if ! docker info >/dev/null 2>&1; then
        err "docker daemon 未运行，或当前用户无 docker 权限"
        err "  检查: sudo systemctl status docker"
        err "  加入 docker 组: sudo usermod -aG docker \$USER && newgrp docker"
        exit 1
    fi

    local compose_tpl="${SCRIPT_DIR}/docker-compose.llm.yml"
    local litellm_cfg="${SCRIPT_DIR}/litellm-config.yaml"
    [ -f "$compose_tpl" ] || { err "未找到 compose 模板: $compose_tpl"; exit 1; }
    [ -f "$litellm_cfg" ] || { err "未找到 litellm 配置: $litellm_cfg"; exit 1; }

    log "前置检查通过（compose: ${COMPOSE_CMD[*]}）"
}

# --------------------- 交互式收集 API Key ---------------------
collect_credentials() {
    if [ -n "$LLM_API_KEY" ]; then
        if [ -z "$LLM_API_BASE" ] || [ "$LLM_API_BASE" = "https://dashscope.aliyuncs.com/compatible-mode/v1" ]; then
            if [ -f "${INSTALL_BASE}/.env" ]; then
                local prev_base
                prev_base=$(grep -E '^LLM_API_BASE=' "${INSTALL_BASE}/.env" 2>/dev/null | cut -d= -f2- || true)
                [ -n "$prev_base" ] && LLM_API_BASE="$prev_base"
            elif [ -f "${SCRIPT_DIR}/.env" ]; then
                local prev_base
                prev_base=$(grep -E '^LLM_API_BASE=' "${SCRIPT_DIR}/.env" 2>/dev/null | cut -d= -f2- || true)
                [ -n "$prev_base" ] && LLM_API_BASE="$prev_base"
            fi
        fi
        return 0
    fi

    if [ -f "${INSTALL_BASE}/.env" ]; then
        local prev_key
        prev_key=$(grep -E '^LLM_API_KEY=' "${INSTALL_BASE}/.env" 2>/dev/null | cut -d= -f2- || true)
        if [ -n "$prev_key" ]; then
            warn "检测到已存在的 .env，复用既有 API Key"
            LLM_API_KEY="$prev_key"
            local prev_base
            prev_base=$(grep -E '^LLM_API_BASE=' "${INSTALL_BASE}/.env" 2>/dev/null | cut -d= -f2- || true)
            [ -n "$prev_base" ] && LLM_API_BASE="$prev_base"
            return 0
        fi
    fi

    if [ -f "${SCRIPT_DIR}/.env" ]; then
        local prev_key
        prev_key=$(grep -E '^LLM_API_KEY=' "${SCRIPT_DIR}/.env" 2>/dev/null | cut -d= -f2- || true)
        if [ -n "$prev_key" ]; then
            warn "检测到脚本目录的 .env，复用既有 API Key"
            LLM_API_KEY="$prev_key"
            local prev_base
            prev_base=$(grep -E '^LLM_API_BASE=' "${SCRIPT_DIR}/.env" 2>/dev/null | cut -d= -f2- || true)
            [ -n "$prev_base" ] && LLM_API_BASE="$prev_base"
            return 0
        fi
    fi

    echo
    info "=========== LLM 上游服务配置 ==========="
    echo
    echo "  请准备以下信息（根据您的上游提供商）："
    echo "    1. API Key（形如 sk-xxx，vLLM 可留空）"
    echo "    2. API Base URL（OpenAI 兼容端点）"
    echo "       - vLLM: http://vllm:8000/v1"
    echo "       - DashScope: https://dashscope.aliyuncs.com/compatible-mode/v1"
    echo "       - OpenAI: https://api.openai.com/v1"
    echo "       默认值可直接回车使用"
    echo


    read -r -p "  API Key (sk-xxx): " LLM_API_KEY

    read -r -p "  API Base URL [${LLM_API_BASE}]: " input_base
    [ -n "$input_base" ] && LLM_API_BASE="$input_base"

    echo
}

# --------------------- 准备安装目录与配置 ---------------------
setup_install_dir() {
    log "准备安装目录: ${INSTALL_BASE}"
    mkdir -p "$INSTALL_BASE"

    cp "${SCRIPT_DIR}/docker-compose.llm.yml" "${INSTALL_BASE}/docker-compose.yml"
    cp "${SCRIPT_DIR}/litellm-config.yaml"    "${INSTALL_BASE}/litellm-config.yaml"

    cat > "${INSTALL_BASE}/.env" <<EOF
# Qi Hop LLM 助手环境变量（由 hop-deploy-llm.sh 生成）
# 警告：本文件包含 API Key，权限应为 600，禁止提交到 git

# LLM API Key (vLLM, DashScope, OpenAI, etc.)
LLM_API_KEY=${LLM_API_KEY}

# LLM API Base URL (OpenAI compatible endpoint)
# - vLLM: http://vllm:8000/v1
# - DashScope: https://dashscope.aliyuncs.com/compatible-mode/v1
# - OpenAI: https://api.openai.com/v1
LLM_API_BASE=${LLM_API_BASE}

LITELLM_PORT=${LITELLM_PORT}
QDRANT_HTTP_PORT=${QDRANT_HTTP_PORT}
QDRANT_GRPC_PORT=${QDRANT_GRPC_PORT}

DEFAULT_MODEL=${DEFAULT_MODEL}
EOF
    chmod 600 "${INSTALL_BASE}/.env"

    local hop_base="/opt/qi"
    local hop_setenv="${hop_base}/tomcat-run-qi-hop-001/bin/setenv.sh"
    cat > "${INSTALL_BASE}/hop-web-env.sh" <<EOF
# ===== 追加到 Hop Web 的 setenv.sh（${hop_setenv}）以启用 LLM 助手 =====
# 由 hop-deploy-llm.sh 生成，请手动追加（避免覆盖既有 setenv.sh）

# LLM 助手（通过 litellm 代理访问上游 LLM）
export HOP_LLM_ENABLED="true"
export HOP_LLM_API_URL="http://$(hostname -I 2>/dev/null | awk '{print $1}' || echo localhost):${LITELLM_PORT}/v1"
export HOP_LLM_API_KEY="sk-hop-litellm-dev"
export HOP_LLM_MODEL="${DEFAULT_MODEL}"

# 知识库 / RAG（Qdrant 向量库）
export HOP_KB_ENABLED="true"
export HOP_KB_QDRANT_URL="http://$(hostname -I 2>/dev/null | awk '{print $1}' || echo localhost):${QDRANT_HTTP_PORT}"
export HOP_KB_EMBEDDING_MODEL="text-embedding-v1"
EOF
    chmod 644 "${INSTALL_BASE}/hop-web-env.sh"

    log "配置文件已生成"
}

# --------------------- 拉取/导入镜像 ---------------------
pull_images() {
    if [ -n "$IMPORT_IMAGES_DIR" ] && [ -d "$IMPORT_IMAGES_DIR" ]; then
        log "从离线目录导入镜像: ${IMPORT_IMAGES_DIR}"
        local tar_files
        tar_files=$(ls "${IMPORT_IMAGES_DIR}"/*.tar.gz 2>/dev/null || true)
        if [ -z "$tar_files" ]; then
            warn "未找到 tar.gz 文件: ${IMPORT_IMAGES_DIR}"
        else
            for tar_file in $tar_files; do
                log "导入镜像: $(basename "$tar_file")"
                gunzip -c "$tar_file" | docker load >/dev/null 2>&1 || {
                    err "导入失败: ${tar_file}"; return 1
                }
            done
            log "镜像导入完成"
        fi
        return 0
    fi

    if [ "$SKIP_PULL" = "true" ]; then
        warn "跳过镜像拉取（--skip-pull）"
        warn "请确保以下镜像已通过 docker load 导入："
        warn "  - ghcr.io/berriai/litellm:main-stable"
        warn "  - qdrant/qdrant:v1.12.4"
        return 0
    fi

    log "拉取 Docker 镜像（需要临时联网）..."
    cd "$INSTALL_BASE"
    "${COMPOSE_CMD[@]}" pull
    log "镜像拉取完成"
}

# --------------------- 启动 LLM 栈 ---------------------
start_stack() {
    log "启动 LLM 助手栈..."
    cd "$INSTALL_BASE"
    "${COMPOSE_CMD[@]}" up -d

    log "等待服务就绪（最长 60s）..."
    local elapsed=0
    while [ "$elapsed" -lt 60 ]; do
        if curl -fsS "http://localhost:${LITELLM_PORT}/health/liveliness" >/dev/null 2>&1 \
           && curl -fsS "http://localhost:${QDRANT_HTTP_PORT}/healthz" >/dev/null 2>&1; then
            log "服务已就绪（耗时 ${elapsed}s）"
            return 0
        fi
        sleep 3
        elapsed=$((elapsed + 3))
        printf "."
    done
    echo
    warn "健康检查超时，请查看日志: cd ${INSTALL_BASE} && ${COMPOSE_CMD[*]} logs"
}

# --------------------- 卸载 ---------------------
do_uninstall() {
    log "停止并清理 LLM 助手栈..."
    cd "$INSTALL_BASE" 2>/dev/null || { warn "安装目录不存在: $INSTALL_BASE"; exit 0; }

    if [ -f "docker-compose.yml" ]; then
        "${COMPOSE_CMD[@]}" down -v
        log "已停止容器并删除卷"
    else
        warn "未找到 docker-compose.yml，跳过容器清理"
    fi

    read -r -p "是否删除安装目录 ${INSTALL_BASE}? [y/N]: " ans
    if [ "$ans" = "y" ] || [ "$ans" = "Y" ]; then
        rm -rf "$INSTALL_BASE"
        log "已删除: $INSTALL_BASE"
    else
        log "保留安装目录: $INSTALL_BASE"
    fi
}

# --------------------- 状态 ---------------------
do_status() {
    echo
    info "================== LLM 助手栈状态 =================="
    cd "$INSTALL_BASE" 2>/dev/null || { warn "安装目录不存在: $INSTALL_BASE"; exit 1; }
    "${COMPOSE_CMD[@]}" ps

    echo
    info "服务健康检查："
    if curl -fsS "http://localhost:${LITELLM_PORT}/health/liveliness" >/dev/null 2>&1; then
        log "  LiteLLM (${LITELLM_PORT}): ${GREEN}OK${NC}"
    else
        err "  LiteLLM (${LITELLM_PORT}): FAIL"
    fi
    if curl -fsS "http://localhost:${QDRANT_HTTP_PORT}/healthz" >/dev/null 2>&1; then
        log "  Qdrant   (${QDRANT_HTTP_PORT}): ${GREEN}OK${NC}"
    else
        err "  Qdrant   (${QDRANT_HTTP_PORT}): FAIL"
    fi

    echo
    info "Hop Web 集成配置（追加到 setenv.sh）："
    [ -f "${INSTALL_BASE}/hop-web-env.sh" ] && cat "${INSTALL_BASE}/hop-web-env.sh" | sed 's/^/  /'
    info "===================================================="
}

# --------------------- 部署摘要 ---------------------
print_summary() {
    local host_ip
    host_ip=$(hostname -I 2>/dev/null | awk '{print $1}' || echo "localhost")

    echo
    info "=================== LLM 助手栈部署摘要 ==================="
    info " 安装目录       : ${INSTALL_BASE}"
    info " LiteLLM 端口   : ${LITELLM_PORT}"
    info " Qdrant 端口    : ${QDRANT_HTTP_PORT} (HTTP) / ${QDRANT_GRPC_PORT} (gRPC)"
    info " 上游模型       : ${DEFAULT_MODEL}"
    info " API Base       : ${LLM_API_BASE}"
    info " 配置文件       : ${INSTALL_BASE}/.env (600)"
    info "========================================================="
    echo
    info "下一步：将以下环境变量追加到 Hop Web 的 setenv.sh 并重启："
    info "  setenv.sh 路径: /opt/qi/tomcat-run-qi-hop-001/bin/setenv.sh"
    info "  参考文件      : ${INSTALL_BASE}/hop-web-env.sh"
    info ""
    info "  追加命令示例："
    info "    sudo bash -c 'cat ${INSTALL_BASE}/hop-web-env.sh >> /opt/qi/tomcat-run-qi-hop-001/bin/setenv.sh'"
    info "    sudo systemctl restart qi-hop-qi-hop-001"
    echo
    info "验证 LLM 助手："
    info "  curl http://${host_ip}:${LITELLM_PORT}/health/liveliness"
    info "  curl http://${host_ip}:${QDRANT_HTTP_PORT}/healthz"
    info "========================================================="
}

# --------------------- 主流程 ---------------------
main() {
    case "$ACTION" in
        deploy)
            log "Qi Hop LLM 助手栈部署"
            preflight
            collect_credentials
            setup_install_dir
            pull_images
            start_stack
            print_summary
            ;;
        uninstall)
            preflight
            do_uninstall
            ;;
        status)
            detect_compose_cmd
            do_status
            ;;
        *)
            err "未知 action: $ACTION"; exit 1 ;;
    esac
}

main "$@"
