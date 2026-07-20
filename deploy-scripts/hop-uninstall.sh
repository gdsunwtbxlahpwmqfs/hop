#!/bin/bash
# =============================================================
# Qi Hop 卸载脚本
# 反向清理 hop-deploy.sh 部署的所有资源：
#   1. systemd 服务（stop + disable + 删除 unit 文件）
#   2. Tomcat 进程（catalina.base 精确匹配兜底 kill）
#   3. CATALINA_BASE（实例目录，默认删除）
#   4. CATALINA_HOME / JDK（共享资源，仅最后一个实例时提示）
#   5. hop 用户/组 / INSTALL_BASE（仅 --purge 模式）
#
# 用法：
#   ./hop-uninstall.sh                          # 交互式卸载当前实例
#   ./hop-uninstall.sh --instance bi            # 卸载多实例中的 bi
#   ./hop-uninstall.sh --yes                    # 跳过确认
#   ./hop-uninstall.sh --purge                  # 彻底清理（含用户/INSTALL_BASE）
#   ./hop-uninstall.sh --dry-run                # 仅预览，不实际执行
#   ./hop-uninstall.sh --base /opt/qi           # 指定 INSTALL_BASE
#
# 退出码：
#   0  成功
#   1  参数错误 / 前置检查失败
#   2  用户取消
# =============================================================

set -Euo pipefail

# --------------------- 颜色与日志 ---------------------
RED='\033[0;31m'; GREEN='\033[0;32m'; YELLOW='\033[1;33m'; BLUE='\033[0;34m'; NC='\033[0m'
log()  { echo -e "${GREEN}[$(date '+%H:%M:%S')]${NC} $*"; }
warn() { echo -e "${YELLOW}[$(date '+%H:%M:%S')] WARN:${NC} $*"; }
err()  { echo -e "${RED}[$(date '+%H:%M:%S')] ERROR:${NC} $*" >&2; }
info() { echo -e "${BLUE}[$(date '+%H:%M:%S')]${NC} $*"; }

# --------------------- 默认配置（对齐 hop-deploy.sh）---------------------
INSTALL_BASE="${INSTALL_BASE:-/opt/qi}"
HOP_USER="${HOP_USER:-qi}"
HOP_GROUP="${HOP_GROUP:-qi}"
INSTANCE_NAME="${INSTANCE_NAME:-qi-hop-001}"
SERVICE_NAME="${SERVICE_NAME:-}"
PURGE=false
YES=false
DRY_RUN=false

# --------------------- 参数解析 ---------------------
while [[ $# -gt 0 ]]; do
    case "$1" in
        --base)     INSTALL_BASE="$2"; shift 2 ;;
        --instance) INSTANCE_NAME="$2"; shift 2 ;;
        --service)  SERVICE_NAME="$2"; shift 2 ;;
        --user)     HOP_USER="$2"; shift 2 ;;
        --group)    HOP_GROUP="$2"; shift 2 ;;
        --purge)    PURGE=true; shift ;;
        --yes|-y)   YES=true; shift ;;
        --dry-run)  DRY_RUN=true; shift ;;
        -h|--help)  sed -n '2,23p' "$0"; exit 0 ;;
        *) err "未知参数: $1"; exit 1 ;;
    esac
done

# 多实例时 service 名带后缀（对齐 hop-deploy.sh）
if [ -z "$SERVICE_NAME" ]; then
    if [ -n "$INSTANCE_NAME" ]; then
        SERVICE_NAME="qi-hop-${INSTANCE_NAME}"
    else
        SERVICE_NAME="qi-hop"
    fi
fi

# CATALINA_HOME（共享软件） / CATALINA_BASE（运行实例）分离，对齐 hop-deploy.sh
JAVA_HOME="${INSTALL_BASE}/jdk21"
CATALINA_HOME="${INSTALL_BASE}/tomcat"
if [ -n "$INSTANCE_NAME" ]; then
    CATALINA_BASE="${INSTALL_BASE}/tomcat-run-${INSTANCE_NAME}"
else
    CATALINA_BASE="${INSTALL_BASE}/tomcat-run"
fi

# --------------------- 前置检查 ---------------------
if [ "$EUID" -ne 0 ]; then
    err "请使用 root 用户或 sudo 执行"
    exit 1
fi

# 执行动作的统一封装：dry-run 模式只打印
run() {
    if [ "$DRY_RUN" = "true" ]; then
        info "[DRY-RUN] $*"
    else
        eval "$@"
    fi
}

# --------------------- 收集实例信息 ---------------------
# 列出 INSTALL_BASE 下所有 tomcat-run* 目录（可排除当前 CATALINA_BASE）
# 用法：list_instances [exclude_path]
#   不传参：返回所有实例个数
#   传参：返回排除指定路径后的实例个数（用于判断"除了自己还有没有其他实例"）
list_instances() {
    local exclude="${1:-}"
    local count=0
    if [ -d "$INSTALL_BASE" ]; then
        local dirs
        dirs=$(find "$INSTALL_BASE" -maxdepth 1 -type d -name 'tomcat-run*' 2>/dev/null)
        if [ -n "$exclude" ]; then
            # 排除当前实例：用 basename 比较（避免 readlink 在 macOS 的差异）
            local exclude_base
            exclude_base=$(basename "$exclude")
            dirs=$(echo "$dirs" | grep -v "/${exclude_base}\$" 2>/dev/null || true)
        fi
        # 计数非空行（避免 grep -c 在空字符串下与 || echo 0 叠加产生 "0\n0"）
        if [ -n "$dirs" ]; then
            count=$(echo "$dirs" | wc -l | tr -d ' ')
        fi
    fi
    echo "$count"
}

# --------------------- 打印卸载计划 ---------------------
print_plan() {
    info "=================== 卸载计划 ==================="
    info " INSTALL_BASE   : ${INSTALL_BASE}"
    info " INSTANCE_NAME  : ${INSTANCE_NAME:-(default)}"
    info " CATALINA_BASE  : ${CATALINA_BASE}  $([ -d "$CATALINA_BASE" ] && echo '[存在]' || echo '[不存在]')"
    info " CATALINA_HOME  : ${CATALINA_HOME}  $([ -d "$CATALINA_HOME" ] && echo '[存在]' || echo '[不存在]')"
    info " JAVA_HOME      : ${JAVA_HOME}      $([ -d "$JAVA_HOME" ] && echo '[存在]' || echo '[不存在]')"
    info " SERVICE_NAME   : ${SERVICE_NAME}   $([ -f "/etc/systemd/system/${SERVICE_NAME}.service" ] && echo '[已注册]' || echo '[未注册]')"
    info " HOP_USER       : ${HOP_USER}       $(id "$HOP_USER" >/dev/null 2>&1 && echo '[存在]' || echo '[不存在]')"
    info " DRY_RUN        : ${DRY_RUN}"
    info " PURGE          : ${PURGE}（彻底清理 CATALINA_HOME/JDK/用户/INSTALL_BASE）"
    info "==============================================="
}

# --------------------- 1. 停止 systemd 服务 ---------------------
stop_systemd() {
    log "[1/5] 停止 systemd 服务..."

    if [ -f "/etc/systemd/system/${SERVICE_NAME}.service" ]; then
        if systemctl list-unit-files 2>/dev/null | grep -q "^${SERVICE_NAME}\.service"; then
            if systemctl is-active --quiet "$SERVICE_NAME" 2>/dev/null; then
                run "systemctl stop '${SERVICE_NAME}'" && log "  已停止服务: ${SERVICE_NAME}"
            else
                log "  服务未在运行: ${SERVICE_NAME}"
            fi
            if systemctl is-enabled --quiet "$SERVICE_NAME" 2>/dev/null; then
                run "systemctl disable '${SERVICE_NAME}'" && log "  已禁用开机自启"
            fi
        fi
        run "rm -f '/etc/systemd/system/${SERVICE_NAME}.service'"
        run "systemctl daemon-reload"
        run "systemctl reset-failed '${SERVICE_NAME}' 2>/dev/null || true"
        log "  已删除 unit 文件: /etc/systemd/system/${SERVICE_NAME}.service"
    else
        log "  无 systemd 服务文件（可能未注册或已清理）"
    fi
}

# --------------------- 2. 兜底杀进程 ---------------------
kill_processes() {
    log "[2/5] 清理残留 Tomcat 进程..."

    # 优先使用 PID 文件（对齐 hop-deploy.sh 的 PIDFile=${CATALINA_BASE}/temp/tomcat.pid）
    local pid_file="${CATALINA_BASE}/temp/tomcat.pid"
    local pids=""

    if [ -f "$pid_file" ]; then
        local p
        p=$(cat "$pid_file" 2>/dev/null || true)
        [ -n "$p" ] && kill -0 "$p" 2>/dev/null && pids="$p"
    fi

    # 用 catalina.base=<CATALINA_BASE> 精确匹配实例进程
    # （多实例共享 CATALINA_HOME，必须用 base 区分，避免误杀其他实例）
    if [ -z "$pids" ]; then
        pids=$(pgrep -f "catalina.base=${CATALINA_BASE}" 2>/dev/null | tr '\n' ' ' | sed 's/ $//')
    fi

    if [ -z "$pids" ]; then
        log "  无残留进程"
        return 0
    fi

    warn "  发现残留进程: ${pids}"
    # SIGTERM 优雅退出
    for pid in $pids; do
        run "kill -15 '${pid}' 2>/dev/null || true"
    done
    sleep 3

    # 仍未退出则 SIGKILL
    local alive=""
    for pid in $pids; do
        if kill -0 "$pid" 2>/dev/null; then
            alive="$alive $pid"
        fi
    done
    if [ -n "$alive" ]; then
        warn "  优雅退出超时，强制终止:${alive}"
        for pid in $alive; do
            run "kill -9 '${pid}' 2>/dev/null || true"
        done
    fi
    log "  进程清理完成"
}

# --------------------- 3. 删除 CATALINA_BASE（实例目录）---------------------
remove_catalina_base() {
    log "[3/5] 删除实例目录 CATALINA_BASE..."

    if [ -d "$CATALINA_BASE" ]; then
        # 校验路径合法性，避免误删（必须是 tomcat-run 或 tomcat-run-xxx）
        local base_name
        base_name=$(basename "$CATALINA_BASE")
        if [[ "$base_name" != "tomcat-run" && "$base_name" != tomcat-run-* ]]; then
            err "  路径校验失败: ${CATALINA_BASE}（期望 basename 为 tomcat-run 或 tomcat-run-*）"
            err "  拒绝删除以保护系统"
            exit 1
        fi
        run "rm -rf '${CATALINA_BASE}'"
        log "  已删除: ${CATALINA_BASE}"
    else
        log "  实例目录不存在: ${CATALINA_BASE}"
    fi
}

# --------------------- 4. 清理共享资源（CATALINA_HOME / JDK）---------------------
remove_shared_resources() {
    log "[4/5] 评估共享资源（CATALINA_HOME / JDK）..."

    # 排除当前实例（dry-run 时 CATALINA_BASE 还没真删，必须显式排除避免误判）
    local instance_count
    instance_count=$(list_instances "$CATALINA_BASE")
    log "  其他剩余实例目录数: ${instance_count}"

    if [ "$instance_count" -gt 0 ]; then
        log "  仍有其他实例存在，保留 CATALINA_HOME 与 JDK"
        return 0
    fi

    if [ "$PURGE" = "true" ]; then
        if [ -d "$CATALINA_HOME" ]; then
            run "rm -rf '${CATALINA_HOME}'"
            log "  已删除 CATALINA_HOME: ${CATALINA_HOME}"
        fi
        if [ -d "$JAVA_HOME" ]; then
            run "rm -rf '${JAVA_HOME}'"
            log "  已删除 JAVA_HOME: ${JAVA_HOME}"
        fi
    else
        warn "  这是最后一个实例，但未指定 --purge，保留共享资源:"
        warn "    - ${CATALINA_HOME}"
        warn "    - ${JAVA_HOME}"
        warn "  如需彻底清理，请追加 --purge 重新执行"
    fi
}

# --------------------- 5. 清理用户/组 / INSTALL_BASE（仅 --purge）---------------------
remove_user_and_base() {
    log "[5/5] 清理用户与安装根目录（仅 --purge）..."

    if [ "$PURGE" != "true" ]; then
        log "  未指定 --purge，跳过"
        return 0
    fi

    # 再次校验：purge 模式必须没有其他剩余实例（排除当前实例）
    local instance_count
    instance_count=$(list_instances "$CATALINA_BASE")
    if [ "$instance_count" -gt 0 ]; then
        err "  仍有 ${instance_count} 个其他实例存在，拒绝 --purge 清理 INSTALL_BASE"
        err "  请先卸载其他实例:"
        find "$INSTALL_BASE" -maxdepth 1 -type d -name 'tomcat-run*' 2>/dev/null \
            | grep -v -F "$CATALINA_BASE" \
            | xargs -n1 basename 2>/dev/null \
            | sed 's/^/    - /' >&2
        exit 1
    fi

    # 删除 INSTALL_BASE
    if [ -d "$INSTALL_BASE" ]; then
        run "rm -rf '${INSTALL_BASE}'"
        log "  已删除 INSTALL_BASE: ${INSTALL_BASE}"
    fi

    # 删除用户与组
    if id "$HOP_USER" >/dev/null 2>&1; then
        # userdel 不带 -r 避免误删 home 下的用户数据，仅删用户
        # 如需删除 home，提示用户手动清理
        run "userdel '${HOP_USER}' 2>/dev/null || true"
        log "  已删除用户: ${HOP_USER}"
        if [ -d "/home/${HOP_USER}" ]; then
            warn "  用户家目录保留: /home/${HOP_USER}（如需删除请手动 rm -rf）"
        fi
    else
        log "  用户不存在: ${HOP_USER}"
    fi
    if getent group "$HOP_GROUP" >/dev/null 2>&1; then
        run "groupdel '${HOP_GROUP}' 2>/dev/null || true"
        log "  已删除用户组: ${HOP_GROUP}"
    else
        log "  用户组不存在: ${HOP_GROUP}"
    fi
}

# --------------------- 主流程 ---------------------
main() {
    log "Qi Hop 卸载（实例: ${INSTANCE_NAME:-default}, 服务: ${SERVICE_NAME}）"
    print_plan
    echo

    # 确认提示（--yes 或 --dry-run 跳过）
    if [ "$YES" = "false" ] && [ "$DRY_RUN" = "false" ]; then
        warn "即将执行删除操作，此动作不可逆！"
        if [ "$PURGE" = "true" ]; then
            warn "  --purge 模式：将彻底删除 CATALINA_HOME、JDK、用户、INSTALL_BASE"
        fi
        read -r -p "确认卸载？输入 'yes' 继续，其他取消: " ans
        if [ "$ans" != "yes" ]; then
            err "用户取消"
            exit 2
        fi
    fi

    echo
    stop_systemd
    kill_processes
    remove_catalina_base
    remove_shared_resources
    remove_user_and_base

    echo
    log "卸载完成"
    if [ "$DRY_RUN" = "true" ]; then
        info "（以上为 DRY-RUN 预览，未实际执行；去掉 --dry-run 参数以真正执行）"
    fi
}

main "$@"
