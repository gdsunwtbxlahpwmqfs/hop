#!/bin/bash
# =============================================================
# Qi Hop 系统环境优化脚本（适用于 OpenEuler 22.03 LTS）
# 功能：优化系统环境，为 Hop Web 和 LLM 助手部署做准备
# 用法：
#   sudo ./hop-system-prepare.sh
#   sudo ./hop-system-prepare.sh --skip-docker    # 跳过 Docker 配置
#   sudo ./hop-system-prepare.sh --skip-firewall  # 跳过防火墙/SELinux配置
# =============================================================

set -Eeuo pipefail

RED='\033[0;31m'; GREEN='\033[0;32m'; YELLOW='\033[1;33m'; BLUE='\033[0;34m'; NC='\033[0m'
log()  { echo -e "${GREEN}[$(date '+%H:%M:%S')]${NC} $*"; }
warn() { echo -e "${YELLOW}[$(date '+%H:%M:%S')] WARN:${NC} $*"; }
err()  { echo -e "${RED}[$(date '+%H:%M:%S')] ERROR:${NC} $*" >&2; }
info() { echo -e "${BLUE}[$(date '+%H:%M:%S')]${NC} $*"; }

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

SKIP_DOCKER=false
SKIP_FIREWALL=false

while [[ $# -gt 0 ]]; do
    case "$1" in
        --skip-docker)    SKIP_DOCKER=true; shift ;;
        --skip-firewall)  SKIP_FIREWALL=true; shift ;;
        -h|--help)
            sed -n '2,13p' "$0"
            exit 0 ;;
        *) err "未知参数: $1"; exit 1 ;;
    esac
done

check_root() {
    if [[ "$(id -u)" != "0" ]]; then
        err "此脚本需要 root 权限，请使用 sudo 运行"
        exit 1
    fi
}

update_system() {
    log "1. 更新系统软件包..."
    dnf update -y
    log "系统更新完成"
}

configure_firewall() {
    if [ "$SKIP_FIREWALL" = true ]; then
        warn "跳过防火墙配置"
        return
    fi

    log "2. 关闭防火墙..."
    systemctl stop firewalld 2>/dev/null || true
    systemctl disable firewalld 2>/dev/null || true
    log "防火墙已关闭"

    log "3. 关闭 SELinux..."
    setenforce 0 2>/dev/null || true
    if [ -f "/etc/selinux/config" ]; then
        sed -i 's/^SELINUX=enforcing/SELINUX=disabled/' /etc/selinux/config
        sed -i 's/^SELINUX=permissive/SELINUX=disabled/' /etc/selinux/config
    fi
    log "SELinux 已关闭"
}

configure_swap() {
    log "4. 关闭 swap..."
    swapoff -a 2>/dev/null || true
    if [ -f "/etc/fstab" ]; then
        sed -i '/swap/s/^/#/' /etc/fstab
    fi
    log "swap 已关闭"
}

configure_kernel_modules() {
    if [ "$SKIP_DOCKER" = true ]; then
        warn "跳过内核模块配置"
        return
    fi

    log "5. 加载容器所需内核模块..."
    modprobe overlay 2>/dev/null || true
    modprobe br_netfilter 2>/dev/null || true
    log "内核模块加载完成"
}

configure_kernel_params() {
    if [ "$SKIP_DOCKER" = true ]; then
        warn "跳过内核参数配置"
        return
    fi

    log "6. 配置内核参数..."
    cat > /etc/sysctl.d/docker.conf << 'EOF'
net.bridge.bridge-nf-call-iptables = 1
net.bridge.bridge-nf-call-ip6tables = 1
net.ipv4.ip_forward = 1
EOF
    sysctl --system 2>/dev/null || true
    log "内核参数配置完成"
}

configure_docker() {
    if [ "$SKIP_DOCKER" = true ]; then
        warn "跳过 Docker 配置"
        return
    fi

    log "7. 配置 Docker..."
    mkdir -p /etc/docker
    cat > /etc/docker/daemon.json << 'EOF'
{
  "exec-opts": ["native.cgroupdriver=systemd"],
  "log-driver": "json-file",
  "log-opts": {
    "max-size": "100m",
    "max-file": "3"
  },
  "storage-driver": "overlay2"
}
EOF
    log "Docker 配置完成"
}

configure_java_env() {
    log "8. 配置 Java 环境变量..."
    echo 'export JAVA_HOME=/opt/hop/jdk21' >> /etc/profile
    echo 'export PATH=$JAVA_HOME/bin:$PATH' >> /etc/profile
    log "Java 环境变量已配置到 /etc/profile"

    log "9. 配置当前会话 Java 环境..."
    export JAVA_HOME=/opt/hop/jdk21
    export PATH=$JAVA_HOME/bin:$PATH
    log "当前会话 Java 环境已生效"
}

configure_user() {
    log "10. 配置用户权限..."
    local hop_user="hop"
    if id "$hop_user" &>/dev/null; then
        usermod -aG root "$hop_user"
        log "用户 $hop_user 已添加到 root 组"
    else
        warn "用户 $hop_user 不存在，跳过组配置"
    fi
}

main() {
    check_root

    log "========== Qi Hop 系统环境优化（OpenEuler 22.03 LTS）=========="
    log "开始优化系统环境..."

    update_system
    configure_firewall
    configure_swap
    configure_kernel_modules
    configure_kernel_params
    configure_docker
    configure_java_env
    configure_user

    log "========== 系统环境优化完成 =========="
    info ""
    info "重要提示:"
    info "  1. 如需立即生效环境变量，请执行: source /etc/profile"
    info "  2. 如需安装 Docker，请执行: dnf install docker -y && systemctl enable --now docker"
    info "  3. 建议重启系统以确保所有配置生效"
    info ""
}

main "$@"