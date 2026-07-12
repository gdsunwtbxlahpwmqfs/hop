#!/bin/bash
# =============================================================
# Qi Hop Web 离线自动化安装脚本
# 功能：一键完成 JDK、Tomcat、Hop Web 的安装与配置
# 用法：sudo bash install-hop-web.sh
# =============================================================

set -Eeuo pipefail

# --------------------- 颜色定义 ---------------------
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

log()  { echo -e "${GREEN}[$(date '+%H:%M:%S')]${NC} $*"; }
warn() { echo -e "${YELLOW}[$(date '+%H:%M:%S')] WARN:${NC} $*"; }
err()  { echo -e "${RED}[$(date '+%H:%M:%S')] ERROR:${NC} $*" >&2; }
info() { echo -e "${BLUE}[$(date '+%H:%M:%S')]${NC} $*"; }

# --------------------- 配置变量 ---------------------
# 安装基础目录
INSTALL_BASE="/opt/hop"
# 运行用户
HOP_USER="hop"
HOP_GROUP="hop"
# 脚本所在目录的父目录即为离线包根目录
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PKG_DIR="$(dirname "$SCRIPT_DIR")"

# JDK 与 Tomcat 安装路径
JAVA_HOME="${INSTALL_BASE}/jdk21"
CATALINA_HOME="${INSTALL_BASE}/tomcat"

# Tomcat HTTP 端口
TOMCAT_PORT="${TOMCAT_PORT:-8080}"

# --------------------- 前置检查 ---------------------
check_prerequisites() {
    log "执行前置检查..."

    # 检查 root 权限
    if [ "$EUID" -ne 0 ]; then
        err "请使用 root 用户或 sudo 执行此脚本"
        exit 1
    fi

    # 检查离线包文件
    local missing=0

    if ! ls "$PKG_DIR"/jdk/jdk-21*linux*.tar.gz >/dev/null 2>&1; then
        err "未找到 JDK 21 安装包: $PKG_DIR/jdk/"
        missing=1
    fi

    if ! ls "$PKG_DIR"/tomcat/apache-tomcat-10*.tar.gz >/dev/null 2>&1; then
        err "未找到 Tomcat 10 安装包: $PKG_DIR/tomcat/"
        missing=1
    fi

    if [ ! -f "$PKG_DIR/hop/hop.war" ]; then
        err "未找到 hop.war: $PKG_DIR/hop/hop.war"
        missing=1
    fi

    if ! ls "$PKG_DIR"/hop/hop-client-*.zip >/dev/null 2>&1; then
        err "未找到 Hop Client 包: $PKG_DIR/hop/"
        missing=1
    fi

    if ! ls "$PKG_DIR"/hop/hop-assemblies-plugins-*.zip >/dev/null 2>&1; then
        warn "未找到 Hop 插件包（可选）: $PKG_DIR/hop/"
    fi

    if [ "$missing" -eq 1 ]; then
        err "缺少必要安装文件，请检查离线安装包完整性"
        exit 1
    fi

    # 检查端口
    if ss -tlnp | grep -q ":${TOMCAT_PORT} "; then
        warn "端口 ${TOMCAT_PORT} 已被占用，可能需要修改 Tomcat 端口"
    fi

    log "前置检查通过"
}

# --------------------- 创建用户与目录 ---------------------
setup_user_and_dirs() {
    log "创建用户和目录..."

    # 创建用户组
    if ! getent group "$HOP_GROUP" >/dev/null; then
        groupadd -r "$HOP_GROUP"
    fi

    # 创建用户
    if ! id -u "$HOP_USER" >/dev/null 2>&1; then
        useradd -d "$INSTALL_BASE" -m -s /bin/bash -g "$HOP_GROUP" "$HOP_USER"
    fi

    # 创建目录结构
    mkdir -p "${INSTALL_BASE}"/{tomcat,plugins,jdbc-drivers,config,audit,logs,project,data}
    mkdir -p /tmp/rwt-resources

    log "用户和目录创建完成"
}

# --------------------- 安装 JDK ---------------------
install_jdk() {
    log "安装 JDK 21..."

    local jdk_pkg
    jdk_pkg=$(ls "$PKG_DIR"/jdk/jdk-21*linux*.tar.gz | head -1)

    tar -xzf "$jdk_pkg" -C "$INSTALL_BASE"

    # 重命名为标准目录
    local extracted_dir
    extracted_dir=$(find "$INSTALL_BASE" -maxdepth 1 -type d -name "jdk-21*" | head -1)
    if [ -n "$extracted_dir" ] && [ "$extracted_dir" != "$JAVA_HOME" ]; then
        rm -rf "$JAVA_HOME"
        mv "$extracted_dir" "$JAVA_HOME"
    fi

    # 验证
    if "$JAVA_HOME/bin/java" -version 2>&1 | grep -q "version"; then
        log "JDK 21 安装成功"
        "$JAVA_HOME/bin/java" -version
    else
        err "JDK 21 安装失败"
        exit 1
    fi
}

# --------------------- 安装 Tomcat ---------------------
install_tomcat() {
    log "安装 Tomcat 10..."

    local tomcat_pkg
    tomcat_pkg=$(ls "$PKG_DIR"/tomcat/apache-tomcat-10*.tar.gz | head -1)

    tar -xzf "$tomcat_pkg" -C "$INSTALL_BASE"

    # 重命名
    local extracted_dir
    extracted_dir=$(find "$INSTALL_BASE" -maxdepth 1 -type d -name "apache-tomcat-10*" | head -1)
    if [ -n "$extracted_dir" ] && [ "$extracted_dir" != "$CATALINA_HOME" ]; then
        rm -rf "$CATALINA_HOME"
        mv "$extracted_dir" "$CATALINA_HOME"
    fi

    # 清除默认 webapp
    rm -rf "${CATALINA_HOME}/webapps/"*

    # 创建 ROOT
    mkdir -p "${CATALINA_HOME}/webapps/ROOT"

    log "Tomcat 10 安装成功"
}

# --------------------- 部署 Hop Web ---------------------
deploy_hop_web() {
    log "部署 Hop Web 应用..."

    local WEBAPP_DIR="${CATALINA_HOME}/webapps/ROOT"

    # 1. 解压 hop.war
    info "解压 hop.war..."
    unzip -q "$PKG_DIR/hop/hop.war" -d "$WEBAPP_DIR"

    # 2. 从 Client 包提取核心库
    info "提取核心库 (hop-core, hop-engine)..."
    local client_zip
    client_zip=$(ls "$PKG_DIR"/hop/hop-client-*.zip | head -1)
    local work_dir="/tmp/hop-client-extract-$$"
    rm -rf "$work_dir"
    mkdir -p "$work_dir"
    unzip -q "$client_zip" -d "$work_dir"

    # 3. 复制核心库到 WEB-INF/lib
    info "复制核心库到 WEB-INF/lib..."
    cp "$work_dir"/hop/lib/core/*.jar "$WEBAPP_DIR/WEB-INF/lib/"
    cp "$work_dir"/hop/lib/beam/*.jar "$WEBAPP_DIR/WEB-INF/lib/"

    # 4. 删除冲突的 RCP fragment
    info "移除冲突的 hop-ui-rcp fragment..."
    rm -f "$WEBAPP_DIR/WEB-INF/lib"/hop-ui-rcp*

    # 5. 修正 SWT 库
    info "修正 SWT 库（移除 macOS cocoa，保留 Linux GTK）..."
    rm -f "$WEBAPP_DIR/WEB-INF/lib"/org.eclipse.swt.cocoa.*
    if ! ls "$WEBAPP_DIR/WEB-INF/lib"/org.eclipse.swt.gtk.linux.* >/dev/null 2>&1; then
        cp "$work_dir"/hop/lib/core/org.eclipse.swt.gtk.linux.*.jar \
           "$WEBAPP_DIR/WEB-INF/lib/" 2>/dev/null || warn "Linux GTK SWT 库未找到"
    fi

    # 6. 复制 Hop 配置
    info "复制 Hop 配置文件..."
    cp -r "$work_dir"/hop/config/* "${INSTALL_BASE}/config/" 2>/dev/null || true

    # 7. 复制 Hop CLI 脚本
    info "复制 Hop CLI 脚本..."
    cp "$work_dir"/hop/*.sh "$WEBAPP_DIR/" 2>/dev/null || true
    chmod +x "$WEBAPP_DIR"/*.sh 2>/dev/null || true

    # 8. 修正 CLI classpath
    info "修正 CLI 脚本 classpath..."
    for script in hop-run.sh hop-conf.sh hop-search.sh hop-encrypt.sh hop-import.sh; do
        if [ -f "$WEBAPP_DIR/$script" ]; then
            sed -i 's&lib/core/\*&../../lib/*:WEB-INF/lib/*:lib/core/*&g' "$WEBAPP_DIR/$script"
        fi
    done

    # 9. 修正 hop-config.json 路径
    if [ -f "${INSTALL_BASE}/config/hop-config.json" ]; then
        sed -i "s|config/projects|${INSTALL_BASE}/config/projects|g" \
            "${INSTALL_BASE}/config/hop-config.json"
    fi

    # 10. 部署插件
    local plugins_zip
    plugins_zip=$(ls "$PKG_DIR"/hop/hop-assemblies-plugins-*.zip 2>/dev/null | head -1)
    if [ -n "$plugins_zip" ]; then
        info "部署 Hop 插件..."
        local pwork="/tmp/hop-plugins-extract-$$"
        mkdir -p "$pwork/tmp"
        unzip -q "$plugins_zip" -d "$pwork/tmp"
        mv "$pwork/tmp/plugins/"* "${INSTALL_BASE}/plugins/" 2>/dev/null || true
        rm -rf "$pwork"
    else
        warn "跳过插件部署（未找到插件包）"
    fi

    # 11. 部署 JDBC 驱动
    if ls "$PKG_DIR"/jdbc/*.jar >/dev/null 2>&1; then
        info "部署 JDBC 驱动..."
        cp "$PKG_DIR"/jdbc/*.jar "${INSTALL_BASE}/jdbc-drivers/"
    fi

    # 12. 清理临时文件
    rm -rf "$work_dir"

    log "Hop Web 应用部署完成"
}

# --------------------- 配置 setenv.sh ---------------------
configure_setenv() {
    log "配置 Tomcat setenv.sh..."

    cat > "${CATALINA_HOME}/bin/setenv.sh" << SETENVEOF
#!/bin/bash
# Hop Web Tomcat 环境变量配置（自动生成）

export JAVA_HOME="${JAVA_HOME}"
export JRE_HOME="${JAVA_HOME}"

# Hop 配置
export HOP_CONFIG_FOLDER="${INSTALL_BASE}/config"
export HOP_AUDIT_FOLDER="${INSTALL_BASE}/audit"
export HOP_LOG_LEVEL="Basic"
export HOP_PASSWORD_ENCODER_PLUGIN="Hop"
export HOP_PLUGIN_BASE_FOLDERS="${INSTALL_BASE}/plugins"
export HOP_SHARED_JDBC_FOLDERS="${INSTALL_BASE}/jdbc-drivers"
export HOP_GUI_ZOOM_FACTOR="1.0"
export HOP_AES_ENCODER_KEY=""
export HOP_PROJECT_CONFIG_FILE_NAME="project-config.json"
export HOP_ENVIRONMENT_NAME="environment1"

# CATALINA_OPTS
CATALINA_OPTS=""
CATALINA_OPTS="\${CATALINA_OPTS} -Xms2g -Xmx2g"
CATALINA_OPTS="\${CATALINA_OPTS} -XX:+UseG1GC"
CATALINA_OPTS="\${CATALINA_OPTS} -XX:MaxGCPauseMillis=200"
CATALINA_OPTS="\${CATALINA_OPTS} -XX:+HeapDumpOnOutOfMemoryError"
CATALINA_OPTS="\${CATALINA_OPTS} -XX:HeapDumpPath=${INSTALL_BASE}/logs/"
CATALINA_OPTS="\${CATALINA_OPTS} -Djava.security.egd=file:/dev/./urandom"
CATALINA_OPTS="\${CATALINA_OPTS} -Dorg.eclipse.rap.rwt.resourceLocation=/tmp/rwt-resources"
CATALINA_OPTS="\${CATALINA_OPTS} -Duser.timezone=UTC -Dfile.encoding=UTF-8"
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_CONFIG_FOLDER=\${HOP_CONFIG_FOLDER}"
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_AUDIT_FOLDER=\${HOP_AUDIT_FOLDER}"
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_LOG_LEVEL=\${HOP_LOG_LEVEL}"
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_PASSWORD_ENCODER_PLUGIN=\${HOP_PASSWORD_ENCODER_PLUGIN}"
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_PLUGIN_BASE_FOLDERS=\${HOP_PLUGIN_BASE_FOLDERS}"
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_SHARED_JDBC_FOLDERS=\${HOP_SHARED_JDBC_FOLDERS}"
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_GUI_ZOOM_FACTOR=\${HOP_GUI_ZOOM_FACTOR}"
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_AES_ENCODER_KEY=\${HOP_AES_ENCODER_KEY}"

# Java 模块开放
CATALINA_OPTS="\${CATALINA_OPTS} --add-opens=java.base/java.lang=ALL-UNNAMED"
CATALINA_OPTS="\${CATALINA_OPTS} --add-opens=java.base/java.net=ALL-UNNAMED"
CATALINA_OPTS="\${CATALINA_OPTS} --add-opens=java.base/java.io=ALL-UNNAMED"
CATALINA_OPTS="\${CATALINA_OPTS} --add-opens=java.base/java.util=ALL-UNNAMED"
CATALINA_OPTS="\${CATALINA_OPTS} --add-opens=java.base/java.nio=ALL-UNNAMED"
CATALINA_OPTS="\${CATALINA_OPTS} --add-opens=java.base/sun.nio.ch=ALL-UNNAMED"

export CATALINA_OPTS
export CATALINA_OUT="${INSTALL_BASE}/logs/catalina.out"
SETENVEOF

    chmod +x "${CATALINA_HOME}/bin/setenv.sh"
    log "setenv.sh 配置完成"
}

# --------------------- 配置 systemd 服务 ---------------------
setup_systemd() {
    log "配置 systemd 服务..."

    cat > /etc/systemd/system/hop-web.service << SVCEOF
[Unit]
Description=Qi Hop Web Service
Documentation=https://hop.apache.org/
After=network.target

[Service]
Type=forking
User=${HOP_USER}
Group=${HOP_GROUP}
Environment="JAVA_HOME=${JAVA_HOME}"
Environment="JRE_HOME=${JAVA_HOME}"
Environment="CATALINA_PID=${CATALINA_HOME}/temp/tomcat.pid"
Environment="CATALINA_HOME=${CATALINA_HOME}"
Environment="CATALINA_BASE=${CATALINA_HOME}"
Environment="CATALINA_OUT=${INSTALL_BASE}/logs/catalina.out"

ExecStart=${CATALINA_HOME}/bin/startup.sh
ExecStop=${CATALINA_HOME}/bin/shutdown.sh

RestartSec=10
Restart=on-failure
LimitNOFILE=65536
LimitNPROC=65536
WorkingDirectory=${CATALINA_HOME}

[Install]
WantedBy=multi-user.target
SVCEOF

    systemctl daemon-reload
    systemctl enable hop-web

    log "systemd 服务配置完成（已设置开机自启）"
}

# --------------------- 设置权限 ---------------------
set_permissions() {
    log "设置文件权限..."

    chown -R "${HOP_USER}:${HOP_GROUP}" "${INSTALL_BASE}"
    chown -R "${HOP_USER}:${HOP_GROUP}" /tmp/rwt-resources
    chmod 755 "${INSTALL_BASE}"
    chmod +x "${CATALINA_HOME}/bin/"*.sh

    log "权限设置完成"
}

# --------------------- 启动验证 ---------------------
start_and_verify() {
    log "启动 Hop Web 服务..."

    systemctl start hop-web

    info "等待服务启动（最多 60 秒）..."
    local count=0
    while [ $count -lt 60 ]; do
        if curl -s -o /dev/null -w "%{http_code}" "http://localhost:${TOMCAT_PORT}/hop/status/" 2>/dev/null | grep -q "200\|401"; then
            break
        fi
        sleep 2
        count=$((count + 2))
        echo -n "."
    done
    echo ""

    # 最终验证
    if ss -tlnp | grep -q ":${TOMCAT_PORT} "; then
        log "============================================"
        log "  Hop Web 安装并启动成功！"
        log "============================================"
        info "Web UI:     http://<服务器IP>:${TOMCAT_PORT}/ui"
        info "Web UI(暗色): http://<服务器IP>:${TOMCAT_PORT}/ui-dark"
        info "Server API: http://<服务器IP>:${TOMCAT_PORT}/hop/"
        info "状态检查:   http://<服务器IP>:${TOMCAT_PORT}/hop/status/"
        info ""
        info "日志文件:   ${INSTALL_BASE}/logs/catalina.out"
        info "服务管理:   systemctl {start|stop|restart|status} hop-web"
        log "============================================"
    else
        err "服务启动可能失败，请检查日志: ${INSTALL_BASE}/logs/catalina.out"
        warn "常见原因：内存不足、核心库缺失、端口冲突"
        exit 1
    fi
}

# --------------------- 主流程 ---------------------
main() {
    echo ""
    log "============================================"
    log "  Qi Hop Web 离线安装脚本"
    log "  安装目录: ${INSTALL_BASE}"
    log "  Tomcat端口: ${TOMCAT_PORT}"
    log "============================================"
    echo ""

    check_prerequisites
    setup_user_and_dirs
    install_jdk
    install_tomcat
    deploy_hop_web
    configure_setenv
    setup_systemd
    set_permissions
    start_and_verify
}

main "$@"
