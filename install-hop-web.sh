#!/bin/bash
# =============================================================
# Qi Hop Web 离线/在线自动化安装脚本
# 功能：一键完成 JDK、Tomcat、Hop Web 的安装与配置
#       JDK/Tomcat 支持离线拷贝或在线下载（自动识别）
# 用法：sudo bash install-hop-web.sh
#
# 可选环境变量：
#   INSTALL_MODE   安装模式：auto（默认，离线优先）、offline（仅离线）、online（仅在线）
#   JDK_VERSION    在线下载的 JDK 主版本（默认 21）
#   TOMCAT_VERSION 在线下载的 Tomcat 版本（默认 10.1.28）
#   TOMCAT_PORT    Tomcat HTTP 端口（默认 8080）
#   DOWNLOAD_CACHE 在线下载缓存目录（默认 /tmp/hop-install-download）
#
#   Hop 项目/环境（对齐官方 Docker 约定，默认空=不创建/注册）：
#   HOP_PROJECT_FOLDER                    项目目录（留空则使用 hop-config.json 中已注册的项目）
#   HOP_PROJECT_NAME                      项目名称
#   HOP_ENVIRONMENT_NAME                  环境名称
#   HOP_ENVIRONMENT_CONFIG_FILE_NAME_PATHS 环境配置文件路径
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

# ============================================================
# Hop 项目与环境配置（对齐官方 Docker 约定，可通过环境变量覆盖）
# 默认为空（空=不创建/注册项目，与 Docker 语义一致）
# Hop 运行时通过 hop-config.json 中注册的 projectHome（${HOP_CONFIG_FOLDER}/projects/xxx）自动推导 PROJECT_HOME
# ============================================================
HOP_PROJECT_FOLDER="${HOP_PROJECT_FOLDER:-}"
HOP_PROJECT_NAME="${HOP_PROJECT_NAME:-}"
HOP_ENVIRONMENT_NAME="${HOP_ENVIRONMENT_NAME:-}"
HOP_ENVIRONMENT_CONFIG_FILE_NAME_PATHS="${HOP_ENVIRONMENT_CONFIG_FILE_NAME_PATHS:-}"

# 脚本所在目录的父目录即为离线包根目录
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PKG_DIR="$(dirname "$SCRIPT_DIR")"

# JDK 与 Tomcat 安装路径
JAVA_HOME="${INSTALL_BASE}/jdk21"
CATALINA_HOME="${INSTALL_BASE}/tomcat"

# Tomcat HTTP 端口
TOMCAT_PORT="${TOMCAT_PORT:-8080}"

# ============================================================
# 安装模式：auto（自动检测，离线优先）、offline（仅离线）、online（仅在线）
# ============================================================
INSTALL_MODE="${INSTALL_MODE:-auto}"

# 在线下载版本与 URL
JDK_VERSION="${JDK_VERSION:-21}"
TOMCAT_VERSION="${TOMCAT_VERSION:-10.1.28}"
TOMCAT_DOWNLOAD_URL="https://archive.apache.org/dist/tomcat/tomcat-10/v${TOMCAT_VERSION}/bin/apache-tomcat-${TOMCAT_VERSION}.tar.gz"

# 检测 CPU 架构（用于 JDK 下载）
detect_arch() {
    case "$(uname -m)" in
        x86_64|amd64) echo "x64" ;;
        aarch64|arm64) echo "aarch64" ;;
        *)
            err "不支持的 CPU 架构: $(uname -m)"
            exit 1
            ;;
    esac
}

JDK_ARCH="$(detect_arch)"
# Adoptium (Eclipse Temurin) API —— 自动获取最新 GA 版本
JDK_DOWNLOAD_URL="https://api.adoptium.net/v3/binary/latest/${JDK_VERSION}/ga/linux/${JDK_ARCH}/jdk/hotspot/normal/eclipse"

# 下载缓存目录
DOWNLOAD_CACHE="${DOWNLOAD_CACHE:-/tmp/hop-install-download}"

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

    # JDK：offline 模式必须存在；online 模式不需要；auto 模式可选
    local jdk_pkg=""
    jdk_pkg=$(ls "$PKG_DIR"/jdk/jdk-21*linux*.tar.gz 2>/dev/null | head -1 || true)
    if [ -n "$jdk_pkg" ]; then
        log "检测到 JDK 离线包: $(basename "$jdk_pkg")"
    else
        case "${INSTALL_MODE}" in
            offline)
                err "offline 模式但未找到 JDK 21 安装包: $PKG_DIR/jdk/"
                missing=1
                ;;
            online)
                info "online 模式：JDK 将从在线下载 (Temurin ${JDK_VERSION} ${JDK_ARCH})"
                ;;
            auto)
                warn "未找到 JDK 离线包，将使用在线下载 (Temurin ${JDK_VERSION} ${JDK_ARCH})"
                ;;
        esac
    fi

    # Tomcat：同上
    local tomcat_pkg=""
    tomcat_pkg=$(ls "$PKG_DIR"/tomcat/apache-tomcat-10*.tar.gz 2>/dev/null | head -1 || true)
    if [ -n "$tomcat_pkg" ]; then
        log "检测到 Tomcat 离线包: $(basename "$tomcat_pkg")"
    else
        case "${INSTALL_MODE}" in
            offline)
                err "offline 模式但未找到 Tomcat 10 安装包: $PKG_DIR/tomcat/"
                missing=1
                ;;
            online)
                info "online 模式：Tomcat 将从在线下载 (${TOMCAT_VERSION})"
                ;;
            auto)
                warn "未找到 Tomcat 离线包，将使用在线下载 (${TOMCAT_VERSION})"
                ;;
        esac
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
    mkdir -p "${INSTALL_BASE}"/{tomcat,plugins,jdbc-drivers,config,audit,logs,project,data,temp,rwt-resources}
    mkdir -p "${INSTALL_BASE}/rwt-resources/xterm"

    log "用户和目录创建完成"
}

# --------------------- 安装 JDK ---------------------
install_jdk() {
    log "安装 JDK 21..."

    local jdk_pkg=""
    # 1. 离线包优先（auto/offline 模式）
    if [ "${INSTALL_MODE}" != "online" ]; then
        jdk_pkg=$(ls "$PKG_DIR"/jdk/jdk-21*linux*.tar.gz 2>/dev/null | head -1 || true)
    fi

    # 2. 在线下载（online 模式，或 auto 模式未找到离线包）
    if [ -z "$jdk_pkg" ]; then
        if [ "${INSTALL_MODE}" = "offline" ]; then
            err "offline 模式但未找到 JDK 离线包"
            exit 1
        fi
        info "在线下载 JDK (Temurin ${JDK_VERSION} ${JDK_ARCH})..."
        mkdir -p "$DOWNLOAD_CACHE"
        jdk_pkg="${DOWNLOAD_CACHE}/jdk-${JDK_VERSION}-${JDK_ARCH}.tar.gz"
        # Adoptium API 会 302 重定向到实际二进制文件
        curl -fSL -o "$jdk_pkg" -L "$JDK_DOWNLOAD_URL" || {
            err "JDK 下载失败: $JDK_DOWNLOAD_URL"
            exit 1
        }
        log "JDK 下载完成: $(basename "$jdk_pkg")"
    else
        info "使用 JDK 离线包: $(basename "$jdk_pkg")"
    fi

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

    local tomcat_pkg=""
    # 1. 离线包优先（auto/offline 模式）
    if [ "${INSTALL_MODE}" != "online" ]; then
        tomcat_pkg=$(ls "$PKG_DIR"/tomcat/apache-tomcat-10*.tar.gz 2>/dev/null | head -1 || true)
    fi

    # 2. 在线下载（online 模式，或 auto 模式未找到离线包）
    if [ -z "$tomcat_pkg" ]; then
        if [ "${INSTALL_MODE}" = "offline" ]; then
            err "offline 模式但未找到 Tomcat 离线包"
            exit 1
        fi
        info "在线下载 Tomcat (${TOMCAT_VERSION})..."
        mkdir -p "$DOWNLOAD_CACHE"
        tomcat_pkg="${DOWNLOAD_CACHE}/apache-tomcat-${TOMCAT_VERSION}.tar.gz"
        curl -fSL -o "$tomcat_pkg" "$TOMCAT_DOWNLOAD_URL" || {
            err "Tomcat 下载失败: $TOMCAT_DOWNLOAD_URL"
            exit 1
        }
        log "Tomcat 下载完成: $(basename "$tomcat_pkg")"
    else
        info "使用 Tomcat 离线包: $(basename "$tomcat_pkg")"
    fi

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

    # 12. 复制 RAP/xterm 资源
    info "复制 RAP/xterm 资源..."
    if [ -d "$WEBAPP_DIR/xterm" ]; then
        cp "$WEBAPP_DIR/xterm/"* "${INSTALL_BASE}/rwt-resources/xterm/" 2>/dev/null || true
        echo "    已复制 xterm 资源"
    else
        warn "webapp 中未找到 xterm 资源"
    fi

    # 13. 清理临时文件
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

# ============================================================
# Hop 配置变量
# ============================================================
export HOP_CONFIG_FOLDER="${INSTALL_BASE}/config"
export HOP_AUDIT_FOLDER="${INSTALL_BASE}/audit"
export HOP_TEMP_FOLDER="${INSTALL_BASE}/temp"
export HOP_LOG_LEVEL="${HOP_LOG_LEVEL:-Basic}"
export HOP_PASSWORD_ENCODER_PLUGIN="${HOP_PASSWORD_ENCODER_PLUGIN:-Hop}"
export HOP_PLUGIN_BASE_FOLDERS="${INSTALL_BASE}/plugins"
export HOP_SHARED_JDBC_FOLDERS="${INSTALL_BASE}/jdbc-drivers"
# AES 加密器密钥（对齐 Docker，默认空）
export HOP_AES_ENCODER_KEY="${HOP_AES_ENCODER_KEY:-}"
# Web UI 缩放因子（对齐 Docker，默认 1.0）
export HOP_GUI_ZOOM_FACTOR="${HOP_GUI_ZOOM_FACTOR:-1.0}"

# 项目与环境变量（对齐 Docker 语义：空=不创建/注册项目）
# Hop 运行时通过 hop-config.json 中注册的 projectHome 自动推导 PROJECT_HOME
export HOP_PROJECT_CONFIG_FILE_NAME="project-config.json"
if [ -n "\${HOP_PROJECT_FOLDER}" ]; then
    export HOP_PROJECT_FOLDER="\${HOP_PROJECT_FOLDER}"
    export HOP_PROJECT_NAME="\${HOP_PROJECT_NAME}"
fi
if [ -n "\${HOP_ENVIRONMENT_NAME}" ]; then
    export HOP_ENVIRONMENT_NAME="\${HOP_ENVIRONMENT_NAME}"
fi
if [ -n "\${HOP_ENVIRONMENT_CONFIG_FILE_NAME_PATHS}" ]; then
    export HOP_ENVIRONMENT_CONFIG_FILE_NAME_PATHS="\${HOP_ENVIRONMENT_CONFIG_FILE_NAME_PATHS}"
fi

# ============================================================
# CATALINA_OPTS — JVM 参数
# ============================================================
CATALINA_OPTS=""
CATALINA_OPTS="\${CATALINA_OPTS} -Xms2g -Xmx4g"
CATALINA_OPTS="\${CATALINA_OPTS} -XX:+UseZGC -XX:+ZGenerational"
CATALINA_OPTS="\${CATALINA_OPTS} -XX:MaxGCPauseMillis=100"
CATALINA_OPTS="\${CATALINA_OPTS} -XX:+AlwaysPreTouch"
CATALINA_OPTS="\${CATALINA_OPTS} -XX:+HeapDumpOnOutOfMemoryError"
CATALINA_OPTS="\${CATALINA_OPTS} -XX:HeapDumpPath=${INSTALL_BASE}/logs/"
CATALINA_OPTS="\${CATALINA_OPTS} -Djava.security.egd=file:/dev/./urandom"
CATALINA_OPTS="\${CATALINA_OPTS} -Djdk.attach.allowAttachSelf=true"

# ============================================================
# CATALINA_OPTS — Hop 系统属性
# ============================================================
CATALINA_OPTS="\${CATALINA_OPTS} -Duser.timezone=Asia/Shanghai -Dfile.encoding=UTF-8"
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_CONFIG_FOLDER=\${HOP_CONFIG_FOLDER}"
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_AUDIT_FOLDER=\${HOP_AUDIT_FOLDER}"
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_TEMP_FOLDER=\${HOP_TEMP_FOLDER}"
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_LOG_LEVEL=\${HOP_LOG_LEVEL}"
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_PASSWORD_ENCODER_PLUGIN=\${HOP_PASSWORD_ENCODER_PLUGIN}"
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_PLUGIN_BASE_FOLDERS=\${HOP_PLUGIN_BASE_FOLDERS}"
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_SHARED_JDBC_FOLDERS=\${HOP_SHARED_JDBC_FOLDERS}"
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_AES_ENCODER_KEY=\${HOP_AES_ENCODER_KEY}"
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_GUI_ZOOM_FACTOR=\${HOP_GUI_ZOOM_FACTOR}"

# RAP 资源
CATALINA_OPTS="\${CATALINA_OPTS} -Dorg.eclipse.rap.rwt.resourceLocation=${INSTALL_BASE}/rwt-resources"
CATALINA_OPTS="\${CATALINA_OPTS} -Dswt.use.gtk3=false"

# 日志配置
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_LOG_FILE_MAX_SIZE_MB=500"
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_LOG_FILE_ROTATION_COUNT=10"
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_LOG_BUFFER_LINES=5000"

# 安全配置
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_HIDE_DATABASE_PASSWORDS_IN_LOGS=Y"
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_DISABLE_PLAIN_TEXT_CREDENTIALS=Y"
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_MASK_SENSITIVE_OUTPUT=Y"
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_DISABLE_SENSITIVE_DATA_LOGGING=Y"

# 性能配置
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_ROWSET_DEFAULT_SIZE=10000"
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_DEFAULT_SORT_SIZE=500000"
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_MEMORY_FREE_THRESHOLD_PERCENT=15"
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_AUTO_CLEAN_TEMP_SORT_FILES=Y"
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_CLEANUP_TEMP_FILES_ON_EXIT=Y"
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_MAX_CONCURRENT_BACKGROUND_TASKS=4"
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_USE_VIRTUAL_THREADS=Y"
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_METRIC_FLUSH_INTERVAL_MS=30000"
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_PREVIEW_BATCH_SIZE=100"

# ============================================================
# CATALINA_OPTS — Java 模块开放（Java 21 需要）
# ============================================================
CATALINA_OPTS="\${CATALINA_OPTS} --add-opens=java.xml/com.sun.org.apache.xalan.internal.xsltc.trax=ALL-UNNAMED"
CATALINA_OPTS="\${CATALINA_OPTS} --add-opens=java.xml/jdk.xml.internal=ALL-UNNAMED"
CATALINA_OPTS="\${CATALINA_OPTS} --add-opens=java.base/java.lang=ALL-UNNAMED"
CATALINA_OPTS="\${CATALINA_OPTS} --add-opens=java.base/java.lang.invoke=ALL-UNNAMED"
CATALINA_OPTS="\${CATALINA_OPTS} --add-opens=java.base/java.lang.reflect=ALL-UNNAMED"
CATALINA_OPTS="\${CATALINA_OPTS} --add-opens=java.base/java.io=ALL-UNNAMED"
CATALINA_OPTS="\${CATALINA_OPTS} --add-opens=java.base/java.net=ALL-UNNAMED"
CATALINA_OPTS="\${CATALINA_OPTS} --add-opens=java.base/java.nio=ALL-UNNAMED"
CATALINA_OPTS="\${CATALINA_OPTS} --add-opens=java.base/java.util=ALL-UNNAMED"
CATALINA_OPTS="\${CATALINA_OPTS} --add-opens=java.base/java.util.concurrent=ALL-UNNAMED"
CATALINA_OPTS="\${CATALINA_OPTS} --add-opens=java.base/java.util.concurrent.atomic=ALL-UNNAMED"
CATALINA_OPTS="\${CATALINA_OPTS} --add-opens=java.base/sun.nio.ch=ALL-UNNAMED"
CATALINA_OPTS="\${CATALINA_OPTS} --add-opens=java.base/sun.nio.cs=ALL-UNNAMED"
CATALINA_OPTS="\${CATALINA_OPTS} --add-opens=java.base/sun.security.action=ALL-UNNAMED"
CATALINA_OPTS="\${CATALINA_OPTS} --add-opens=java.base/sun.util.calendar=ALL-UNNAMED"
CATALINA_OPTS="\${CATALINA_OPTS} --add-opens=java.security.jgss/sun.security.krb5=ALL-UNNAMED"
CATALINA_OPTS="\${CATALINA_OPTS} --add-exports=java.base/sun.nio.ch=ALL-UNNAMED"

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
${HOP_PROJECT_FOLDER:+Environment="HOP_PROJECT_FOLDER=${HOP_PROJECT_FOLDER}"}
${HOP_PROJECT_NAME:+Environment="HOP_PROJECT_NAME=${HOP_PROJECT_NAME}"}
${HOP_ENVIRONMENT_NAME:+Environment="HOP_ENVIRONMENT_NAME=${HOP_ENVIRONMENT_NAME}"}
${HOP_ENVIRONMENT_CONFIG_FILE_NAME_PATHS:+Environment="HOP_ENVIRONMENT_CONFIG_FILE_NAME_PATHS=${HOP_ENVIRONMENT_CONFIG_FILE_NAME_PATHS}"}

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
    log "  Qi Hop Web 离线/在线安装脚本"
    log "  安装目录:   ${INSTALL_BASE}"
    log "  安装模式:   ${INSTALL_MODE}"
    log "  JDK 架构:   ${JDK_ARCH}"
    log "  Tomcat端口: ${TOMCAT_PORT}"
    [ -n "${HOP_PROJECT_FOLDER}" ] && log "  项目目录:   ${HOP_PROJECT_FOLDER}"
    [ -n "${HOP_PROJECT_NAME}" ] && log "  项目名称:   ${HOP_PROJECT_NAME}"
    [ -n "${HOP_ENVIRONMENT_NAME}" ] && log "  环境名称:   ${HOP_ENVIRONMENT_NAME}"
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
