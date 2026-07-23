#!/bin/bash
# =============================================================
# Qi Hop 知识库索引构建脚本
# 功能：调用 Hop Web REST API 创建/重建 RAG 知识库索引
#
# 用法：
#   bash hop-build-kb-index.sh                          # 默认配置(端口 8080)
#   bash hop-build-kb-index.sh --host 192.168.1.100     # 自定义主机
#   bash hop-build-kb-index.sh --port 9090              # 自定义端口
#   bash hop-build-kb-index.sh --base-path /hop         # 自定义上下文路径
#   bash hop-build-kb-index.sh --user admin             # 自定义用户名(需认证)
#   bash hop-build-kb-index.sh --password admin@2026    # 自定义密码
#   bash hop-build-kb-index.sh --no-clear               # 跳过清理现有索引
#   bash hop-build-kb-index.sh --verify                 # 验证索引状态(不执行构建)
# =============================================================

set -Eeuo pipefail

# --------------------- 颜色与日志 ---------------------
RED='\033[0;31m'; GREEN='\033[0;32m'; YELLOW='\033[1;33m'; BLUE='\033[0;34m'; NC='\033[0m'
log()  { echo -e "${GREEN}[$(date '+%H:%M:%S')]${NC} $*"; }
warn() { echo -e "${YELLOW}[$(date '+%H:%M:%S')] WARN:${NC} $*"; }
err()  { echo -e "${RED}[$(date '+%H:%M:%S')] ERROR:${NC} $*" >&2; }
info() { echo -e "${BLUE}[$(date '+%H:%M:%S')]${NC} $*"; }

# --------------------- 默认配置 ---------------------
HOP_HOST="${HOP_HOST:-localhost}"
HOP_PORT="${HOP_PORT:-8080}"
HOP_BASE_PATH="${HOP_BASE_PATH:-}"
HOP_USER="${HOP_USER:-}"
HOP_PASSWORD="${HOP_PASSWORD:-}"
SKIP_CLEAR="${SKIP_CLEAR:-false}"
VERIFY_ONLY="${VERIFY_ONLY:-false}"

# --------------------- 参数解析 ---------------------
while [[ $# -gt 0 ]]; do
    case "$1" in
        --host)     HOP_HOST="$2"; shift 2 ;;
        --port)     HOP_PORT="$2"; shift 2 ;;
        --base-path) HOP_BASE_PATH="$2"; shift 2 ;;
        --user)     HOP_USER="$2"; shift 2 ;;
        --password) HOP_PASSWORD="$2"; shift 2 ;;
        --no-clear) SKIP_CLEAR="true"; shift ;;
        --verify)   VERIFY_ONLY="true"; shift ;;
        -h|--help) sed -n '2,18p' "$0"; exit 0 ;;
        *) err "未知参数: $1"; exit 1 ;;
    esac
done

# 构建 REST API URL
HOP_REST_URL="http://${HOP_HOST}:${HOP_PORT}${HOP_BASE_PATH}/api/v1"

# --------------------- 构建 curl 命令(支持认证) ---------------------
build_curl_cmd() {
    local cmd="curl -s"
    if [ -n "$HOP_USER" ] && [ -n "$HOP_PASSWORD" ]; then
        cmd="${cmd} -u ${HOP_USER}:${HOP_PASSWORD}"
    fi
    echo "$cmd"
}

CURL_CMD=$(build_curl_cmd)

# --------------------- 检查服务可用性 ---------------------
check_service() {
    log "检查 Hop Web 服务可用性..."
    info "REST API: ${HOP_REST_URL}"
    
    if ! ${CURL_CMD} -f "${HOP_REST_URL}/knowledgebase/status" >/dev/null 2>&1; then
        err "Hop Web 服务不可达: ${HOP_REST_URL}"
        err "请确认服务已启动: sudo bash hop-start.sh start <service-name>"
        exit 1
    fi
    log "Hop Web 服务可达"
}

# --------------------- 获取索引状态 ---------------------
get_status() {
    log "获取当前索引状态..."
    STATUS_RESP=$(${CURL_CMD} "${HOP_REST_URL}/knowledgebase/status")
    echo "$STATUS_RESP"
    
    if echo "$STATUS_RESP" | grep -q '"enabled":false'; then
        err "RAG 功能未启用(HOP_KB_ENABLED 未设置为 true)"
        err "请检查服务配置，需要设置环境变量 HOP_KB_ENABLED=true"
        exit 1
    fi
    
    if echo "$STATUS_RESP" | grep -q '"indexed":true'; then
        local chunks=$(echo "$STATUS_RESP" | python3 -c "import sys,json; d=json.load(sys.stdin); print(d.get('chunks', 0))")
        log "当前索引状态: 已索引 ${chunks} 个文档块"
    else
        log "当前索引状态: 未索引"
    fi
}

# --------------------- 清理现有索引 ---------------------
clear_index() {
    if [ "$SKIP_CLEAR" = "true" ]; then
        log "跳过清理现有索引(--no-clear)"
        return
    fi
    
    log "清理现有索引..."
    CLEAR_RESP=$(${CURL_CMD} -w "\n%{http_code}" -X DELETE "${HOP_REST_URL}/knowledgebase")
    
    CLEAR_HTTP_CODE=$(echo "$CLEAR_RESP" | tail -1)
    CLEAR_RESP_BODY=$(echo "$CLEAR_RESP" | sed '$d')
    
    log "HTTP 状态: ${CLEAR_HTTP_CODE}"
    log "响应: ${CLEAR_RESP_BODY}"
    
    if [ "$CLEAR_HTTP_CODE" -ne 200 ]; then
        warn "清理索引失败: ${CLEAR_RESP_BODY}"
        warn "继续执行索引构建..."
    else
        log "索引清理完成"
    fi
}

# --------------------- 构建索引 ---------------------
build_index() {
    log "开始构建索引..."
    BUILD_RESP=$(${CURL_CMD} -w "\n%{http_code}" -X POST "${HOP_REST_URL}/knowledgebase/index")
    
    HTTP_CODE=$(echo "$BUILD_RESP" | tail -1)
    RESP_BODY=$(echo "$BUILD_RESP" | sed '$d')
    
    log "HTTP 状态: ${HTTP_CODE}"
    log "响应: ${RESP_BODY}"
    
    if [ "$HTTP_CODE" -ne 200 ]; then
        err "索引构建失败!"
        exit 1
    fi
    
    log "索引构建完成!"
    CHUNKS=$(echo "$RESP_BODY" | python3 -c "import sys,json; d=json.load(sys.stdin); print(d.get('chunks', 0))")
    log "已索引 ${CHUNKS} 个文档块"
}

# --------------------- 主流程 ---------------------
main() {
    log "Qi Hop 知识库索引构建工具"
    info "REST API: ${HOP_REST_URL}"
    
    check_service
    get_status
    
    if [ "$VERIFY_ONLY" = "true" ]; then
        log "验证模式(--verify)，退出"
        exit 0
    fi
    
    clear_index
    build_index
    
    echo
    info "=================== 索引构建完成 ==================="
    info "  REST API    : ${HOP_REST_URL}"
    info "  索引状态    : 已构建"
    info "======================================================"
}

main "$@"
