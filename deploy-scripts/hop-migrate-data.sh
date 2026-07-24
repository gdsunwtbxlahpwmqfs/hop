#!/bin/bash
# =============================================================
# Qi Hop 数据目录迁移脚本
# 总结：将 Hop 数据目录（config/audit/plugins/jdbc-drivers/rwt-resources/docs）迁移到外挂盘
# 原理：使用符号链接方案，无需修改 setenv.sh 或 hop-config.json
# 用法：
#   sudo bash hop-migrate-data.sh --target /mnt/data/hop-data    # 指定目标数据目录
#   sudo bash hop-migrate-data.sh --target /mnt/data/hop-data --instance qi-hop-001    # 指定实例
#   sudo bash hop-migrate-data.sh --target /mnt/data/hop-data --base /opt/qi    # 指定安装根目录
#   sudo bash hop-migrate-data.sh --dry-run    # 模拟执行，不实际迁移
#
# 迁移流程：
#   1. 前置检查（root权限、服务状态、目标目录可写）
#   2. 停止服务
#   3. 创建目标目录结构
#   4. rsync 同步数据（保留权限、支持断点续传）
#   5. 创建符号链接（替换原目录）
#   6. 设置权限
#   7. 启动服务
#   8. 验证服务状态
# =============================================================

set -Eeuo pipefail

# --------------------- 颜色与日志 ---------------------
RED='\033[0;31m'; GREEN='\033[0;32m'; YELLOW='\033[1;33m'; BLUE='\033[0;34m'; NC='\033[0m'
log()  { echo -e "${GREEN}[$(date '+%H:%M:%S')]${NC} $*"; }
warn() { echo -e "${YELLOW}[$(date '+%H:%M:%S')] WARN:${NC} $*"; }
err()  { echo -e "${RED}[$(date '+%H:%M:%S')] ERROR:${NC} $*" >&2; }
info() { echo -e "${BLUE}[$(date '+%H:%M:%S')]${NC} $*"; }

trap 'err "迁移过程中断(行: $LINENO)"; exit 1' ERR

# --------------------- 默认配置（对齐 hop-deploy.sh）---------------------
INSTALL_BASE="${INSTALL_BASE:-/opt/qi}"
HOP_USER="${HOP_USER:-qi}"
HOP_GROUP="${HOP_GROUP:-qi}"
INSTANCE_NAME="${INSTANCE_NAME:-qi-hop-001}"
TARGET_DATA_DIR=""
DRY_RUN=false

# --------------------- 参数解析 ---------------------
while [[ $# -gt 0 ]]; do
    case "$1" in
        --target)  TARGET_DATA_DIR="$2"; shift 2 ;;
        --instance) INSTANCE_NAME="$2"; shift 2 ;;
        --base)    INSTALL_BASE="$2"; shift 2 ;;
        --dry-run) DRY_RUN=true; shift ;;
        -h|--help)
            sed -n '2,22p' "$0"
            exit 0 ;;
        *) err "未知参数: $1"; exit 1 ;;
    esac
done

# CATALINA_BASE（运行实例目录）
if [ -n "$INSTANCE_NAME" ]; then
    CATALINA_BASE="${INSTALL_BASE}/tomcat-run-${INSTANCE_NAME}"
    SERVICE_NAME="qi-hop-${INSTANCE_NAME}"
else
    CATALINA_BASE="${INSTALL_BASE}/tomcat-run"
    SERVICE_NAME="qi-hop"
fi

# 需要迁移的数据目录列表（对齐 hop-deploy.sh 第 115-121 行）
DATA_DIRS=(
    "audit"
    "config"
    "plugins"
    "jdbc-drivers"
    "rwt-resources"
    "docs"
)

# --------------------- 前置检查 ---------------------
check_prerequisites() {
    log "执行前置检查..."

    # root 权限
    if [ "$EUID" -ne 0 ]; then
        err "请使用 root 用户或 sudo 执行"
        exit 1
    fi

    # 目标目录必须指定
    if [ -z "$TARGET_DATA_DIR" ]; then
        err "请使用 --target 指定目标数据目录"
        err "示例: sudo bash $0 --target /mnt/data/hop-data"
        exit 1
    fi

    # 目标目录不能是 CATALINA_BASE 下的子目录
    if [[ "$(realpath "$TARGET_DATA_DIR")" == "$(realpath "$CATALINA_BASE")"/* ]]; then
        err "目标目录不能在 CATALINA_BASE 下"
        exit 1
    fi

    # CATALINA_BASE 必须存在
    if [ ! -d "$CATALINA_BASE" ]; then
        err "CATALINA_BASE 不存在: $CATALINA_BASE"
        err "请确认实例名和安装根目录正确"
        exit 1
    fi

    # 检查服务是否存在
    if ! systemctl list-unit-files 2>/dev/null | grep -q "^${SERVICE_NAME}\.service"; then
        warn "systemd 服务 ${SERVICE_NAME}.service 不存在，将跳过 systemd 操作"
    fi

    # 检查目标目录所在磁盘是否已挂载且可写
    local target_parent=$(dirname "$TARGET_DATA_DIR")
    if [ ! -d "$target_parent" ]; then
        err "目标目录父级不存在: $target_parent"
        exit 1
    fi

    if [ ! -w "$target_parent" ]; then
        err "目标目录父级不可写: $target_parent"
        exit 1
    fi

    # 检查 rsync 命令
    if ! command -v rsync >/dev/null 2>&1; then
        err "缺少必要命令: rsync"
        exit 1
    fi

    log "前置检查通过"
}

# --------------------- 显示迁移预览 ---------------------
show_preview() {
    echo
    info "=================== 迁移预览 ===================="
    info " 安装根目录   : ${INSTALL_BASE}"
    info " 实例名       : ${INSTANCE_NAME}"
    info " CATALINA_BASE: ${CATALINA_BASE}"
    info " 目标数据目录 : ${TARGET_DATA_DIR}"
    info " 服务名称     : ${SERVICE_NAME}"
    info " 迁移目录列表 : ${DATA_DIRS[*]}"
    info " 模拟执行     : ${DRY_RUN}"
    info "=================================================="
    echo

    # 提示用户确认
    read -p "确认执行迁移? (y/N): " confirm
    if [[ ! "$confirm" =~ ^[Yy]$ ]]; then
        log "用户取消迁移"
        exit 0
    fi
}

# --------------------- 停止服务 ---------------------
stop_service() {
    log "停止服务: ${SERVICE_NAME}"
    if systemctl list-unit-files 2>/dev/null | grep -q "^${SERVICE_NAME}\.service"; then
        if systemctl is-active "$SERVICE_NAME" >/dev/null 2>&1; then
            if [ "$DRY_RUN" = "false" ]; then
                systemctl stop "$SERVICE_NAME"
                # 等待服务完全停止
                local count=0
                while systemctl is-active "$SERVICE_NAME" >/dev/null 2>&1 && [ "$count" -lt 30 ]; do
                    sleep 1; count=$((count + 1))
                done
                if systemctl is-active "$SERVICE_NAME" >/dev/null 2>&1; then
                    warn "服务停止超时，强制终止"
                    pkill -9 -f "catalina.base=${CATALINA_BASE}" 2>/dev/null || true
                fi
            else
                log "[模拟] 跳过实际停止操作"
            fi
            log "服务已停止"
        else
            warn "服务未运行，无需停止"
        fi
    else
        warn "systemd 服务不存在，跳过停止操作"
    fi
}

# --------------------- 创建目标目录 ---------------------
create_target_dirs() {
    log "创建目标目录结构..."
    if [ "$DRY_RUN" = "false" ]; then
        mkdir -p "$TARGET_DATA_DIR"
        for dir in "${DATA_DIRS[@]}"; do
            mkdir -p "${TARGET_DATA_DIR}/${dir}"
            log "  创建: ${TARGET_DATA_DIR}/${dir}"
        done
    else
        log "[模拟] 创建目录: ${TARGET_DATA_DIR}/{${DATA_DIRS[*]}}"
    fi
}

# --------------------- 同步数据 ---------------------
sync_data() {
    log "同步数据到目标目录..."
    for dir in "${DATA_DIRS[@]}"; do
        local src="${CATALINA_BASE}/${dir}"
        local dst="${TARGET_DATA_DIR}/${dir}"

        if [ ! -d "$src" ]; then
            warn "源目录不存在，跳过: $src"
            continue
        fi

        if [ "$DRY_RUN" = "false" ]; then
            log "  同步: ${src} → ${dst}"
            rsync -av --delete "$src/" "$dst/"
        else
            log "[模拟] 同步: ${src} → ${dst}"
        fi
    done
}

# --------------------- 创建符号链接 ---------------------
create_symlinks() {
    log "创建符号链接..."
    cd "$CATALINA_BASE"
    for dir in "${DATA_DIRS[@]}"; do
        local src="${CATALINA_BASE}/${dir}"
        local dst="${TARGET_DATA_DIR}/${dir}"

        if [ ! -d "$dst" ]; then
            warn "目标目录不存在，跳过: $dst"
            continue
        fi

        if [ "$DRY_RUN" = "false" ]; then
            # 备份原目录（如果存在且不是符号链接）
            if [ -d "$src" ] && [ ! -L "$src" ]; then
                mv "$src" "${src}.bak"
                log "  备份原目录: ${src}.bak"
            fi

            # 创建符号链接
            ln -s "$dst" "$src"
            log "  创建链接: ${src} → ${dst}"
        else
            log "[模拟] 创建链接: ${src} → ${dst}"
        fi
    done
}

# --------------------- 设置权限 ---------------------
set_permissions() {
    log "设置权限..."
    if [ "$DRY_RUN" = "false" ]; then
        # 设置目标数据目录权限
        chown -R "${HOP_USER}:${HOP_GROUP}" "$TARGET_DATA_DIR"
        log "  目标目录权限: ${TARGET_DATA_DIR} → ${HOP_USER}:${HOP_GROUP}"

        # 设置符号链接权限
        for dir in "${DATA_DIRS[@]}"; do
            local link="${CATALINA_BASE}/${dir}"
            if [ -L "$link" ]; then
                chown "${HOP_USER}:${HOP_GROUP}" "$link"
            fi
        done
        log "  符号链接权限已设置"
    else
        log "[模拟] 设置权限: ${TARGET_DATA_DIR} → ${HOP_USER}:${HOP_GROUP}"
    fi
}

# --------------------- 启动服务 ---------------------
start_service() {
    log "启动服务: ${SERVICE_NAME}"
    if systemctl list-unit-files 2>/dev/null | grep -q "^${SERVICE_NAME}\.service"; then
        if [ "$DRY_RUN" = "false" ]; then
            systemctl start "$SERVICE_NAME"

            # 等待服务就绪（最多60秒）
            local count=0
            log "等待服务启动..."
            while [ "$count" -lt 60 ]; do
                if systemctl is-active "$SERVICE_NAME" >/dev/null 2>&1; then
                    log "服务启动成功"
                    return 0
                fi
                sleep 2; count=$((count + 2))
                printf "."
            done
            echo
            err "服务启动超时"
            return 1
        else
            log "[模拟] 跳过实际启动操作"
        fi
    else
        warn "systemd 服务不存在，跳过启动操作"
    fi
}

# --------------------- 验证服务状态 ---------------------
verify_service() {
    log "验证服务状态..."
    if [ "$DRY_RUN" = "false" ]; then
        if systemctl is-active "$SERVICE_NAME" >/dev/null 2>&1; then
            log "服务状态: ${GREEN}RUNNING${NC}"
        else
            err "服务状态: ${RED}STOPPED${NC}"
            return 1
        fi

        # 验证符号链接
        echo
        info "符号链接验证:"
        local all_ok=true
        for dir in "${DATA_DIRS[@]}"; do
            local link="${CATALINA_BASE}/${dir}"
            if [ -L "$link" ]; then
                local target=$(readlink "$link")
                log "  ${GREEN}✓${NC} ${link} → ${target}"
            else
                err "  ${RED}✗${NC} ${link} 不是符号链接"
                all_ok=false
            fi
        done

        if [ "$all_ok" = "true" ]; then
            return 0
        else
            return 1
        fi
    else
        log "[模拟] 跳过验证"
        return 0
    fi
}

# --------------------- 显示迁移总结 ---------------------
print_summary() {
    echo
    info "=================== 迁移总结 ===================="
    if [ "$DRY_RUN" = "false" ]; then
        info " 迁移状态     : ${GREEN}已完成${NC}"
        info " 源目录       : ${CATALINA_BASE}"
        info " 目标数据目录 : ${TARGET_DATA_DIR}"
        info " 迁移目录     : ${DATA_DIRS[*]}"
        info " 服务名称     : ${SERVICE_NAME}"
        info " 服务状态     : $(systemctl is-active "$SERVICE_NAME" 2>/dev/null || echo "未知")"
        info ""
        info "注意事项:"
        info "  1. 原目录已备份为 *.bak，确认迁移成功后可删除"
        info "  2. 请确保 ${TARGET_DATA_DIR} 所在磁盘已配置开机自动挂载"
        info "  3. 在 /etc/fstab 中添加挂载配置以防止开机后挂载丢失"
        info "  4. 数据目录通过符号链接透明访问，无需修改任何配置文件"
    else
        info " 迁移状态     : ${YELLOW}模拟执行${NC}"
        info " 未执行任何实际操作"
    fi
    info "=================================================="
}

# --------------------- 主流程 ---------------------
main() {
    log "Qi Hop 数据目录迁移（实例: ${INSTANCE_NAME}）"

    # 1. 前置检查
    check_prerequisites

    # 2. 显示预览并确认
    show_preview

    # 3. 停止服务
    stop_service

    # 4. 创建目标目录
    create_target_dirs

    # 5. 同步数据
    sync_data

    # 6. 创建符号链接
    create_symlinks

    # 7. 设置权限
    set_permissions

    # 8. 启动服务
    start_service

    # 9. 验证服务状态
    if verify_service; then
        print_summary
        log "迁移成功！"
        exit 0
    else
        err "迁移失败，请检查日志和符号链接"
        exit 1
    fi
}

main "$@"