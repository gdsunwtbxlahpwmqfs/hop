#!/bin/bash
# =============================================================
# Qi Hop 离线部署脚本(环境依赖对齐 docker/web.Dockerfile + dev-scripts/start-web-dev.sh)
# 总结：主部署入口，一键安装 Hop Web 到 /opt/qi（JDK 21 + Tomcat 10 + WAR + systemd 服务，默认端口 8080）
# 功能：前置检查 -> 创建用户/目录 -> 安装 JDK/Tomcat -> 部署 Hop Web -> 生成配置
#
# 目录布局(CATALINA_HOME / CATALINA_BASE 分离，对齐开发模式 tomcat/ + tomcat-run/)：
#   ${INSTALL_BASE}/
#   ├── jdk21/                   # JDK 软件
#   ├── tomcat/                  # CATALINA_HOME(只读 Tomcat 软件：bin/lib/conf 模板)
#   └── tomcat-run/              # CATALINA_BASE(运行实例，可写)
#       ├── bin/setenv.sh        # 生成的环境变量
#       ├── conf/                # 首次安装从 tomcat/conf 复制
#       ├── webapps/ROOT/        # 解压的 hop.war
#       ├── audit/  config/  plugins/  jdbc-drivers/  # Hop 数据目录
#       ├── rwt-resources/       # RAP 资源缓存(运行时由 RWT 从 war classpath 写入)
#       ├── logs/  temp/  work/
#
# 用法：
#   sudo bash hop-deploy.sh                          # 默认部署(端口 8080，实例 qi-hop-001)
#   sudo bash hop-deploy.sh --pkg /tmp/hop-offline   # 指定离线包解压目录
#   sudo bash hop-deploy.sh --port 9090              # 自定义端口
#   sudo bash hop-deploy.sh --base /opt/qi           # 自定义安装根目录
#   sudo bash hop-deploy.sh --heap 8g                # 自定义堆大小(默认 fixed 模式，物理机部署)
#   sudo bash hop-deploy.sh --heap-mode aggressive   # 使用 AggressiveHeap(容器环境)
#   sudo bash hop-deploy.sh --uid 1000 --gid 1000    # 对齐 Dockerfile ARG HOP_UID/GID
#   sudo bash hop-deploy.sh --instance qi-hop-002    # 多实例：默认 qi-hop-001，此例创建第二个实例
#   sudo bash hop-deploy.sh --log-level Detailed     # 日志级别
#   sudo bash hop-deploy.sh --checksum               # 开启 SHA256 校验(默认关闭)
#   sudo bash hop-deploy.sh --skip-checksum          # 关闭 SHA256 校验(默认)
#
# 多实例部署(同机运行多个 Hop Web)：
#   必改参数：--instance(唯一标识) + --port(避免端口冲突)
#   示例：
#     sudo bash hop-deploy.sh --instance qi-hop-001 --port 8080    # 第一个实例
#     sudo bash hop-deploy.sh --instance qi-hop-002 --port 9090    # 第二个实例(端口必须不同)
# =============================================================

set -Eeuo pipefail

# --------------------- 颜色与日志 ---------------------
RED='\033[0;31m'; GREEN='\033[0;32m'; YELLOW='\033[1;33m'; BLUE='\033[0;34m'; NC='\033[0m'
log()  { echo -e "${GREEN}[$(date '+%H:%M:%S')]${NC} $*"; }
warn() { echo -e "${YELLOW}[$(date '+%H:%M:%S')] WARN:${NC} $*"; }
err()  { echo -e "${RED}[$(date '+%H:%M:%S')] ERROR:${NC} $*" >&2; }
info() { echo -e "${BLUE}[$(date '+%H:%M:%S')]${NC} $*"; }

trap 'err "部署过程中断(行: $LINENO)"; exit 1' ERR

# --------------------- 默认配置(对齐 docker/web.Dockerfile)---------------------
# 共享变量统一 ${VAR:-default}，允许上游/环境变量覆盖（对齐 hop-start/verify/uninstall）
INSTALL_BASE="${INSTALL_BASE:-/opt/qi}"
HOP_USER="${HOP_USER:-qi}"
HOP_GROUP="${HOP_GROUP:-qi}"
HOP_UID="${HOP_UID:-1001}"        # 对齐 Dockerfile ARG HOP_UID
HOP_GID="${HOP_GID:-1001}"        # 对齐 Dockerfile ARG HOP_GID
HOP_USER_HOME="${HOP_USER_HOME:-/home/qi}"   # 对齐 Dockerfile useradd -d /home/qi
TOMCAT_PORT="${TOMCAT_PORT:-8080}"
SERVICE_NAME="${SERVICE_NAME:-qi-hop}"
LOG_LEVEL="${LOG_LEVEL:-Basic}"    # 对齐 ENV HOP_LOG_LEVEL（运行时导出为 HOP_LOG_LEVEL）

# JVM 堆内存策略：aggressive(对齐 Dockerfile -XX:+AggressiveHeap)| fixed(固定大小)
HEAP_MODE="${HEAP_MODE:-fixed}"
HEAP_SIZE="${HEAP_SIZE:-8g}"

# SHA256 校验：默认关闭(false=开启, true=关闭)，可通过 --checksum 开启或 --skip-checksum 关闭
SKIP_CHECKSUM="${SKIP_CHECKSUM:-true}"

# 多实例名：默认 qi-hop-001(tomcat-run-qi-hop-001/)，支持同机多 Hop Web
INSTANCE_NAME="${INSTANCE_NAME:-qi-hop-001}"

# Hop 项目/环境变量(对齐 Dockerfile ENV，默认空=不创建/注册)
HOP_PROJECT_FOLDER="${HOP_PROJECT_FOLDER:-}"
HOP_PROJECT_NAME="${HOP_PROJECT_NAME:-}"
HOP_PROJECT_CONFIG_FILE_NAME="${HOP_PROJECT_CONFIG_FILE_NAME:-project-config.json}"
HOP_ENVIRONMENT_NAME="${HOP_ENVIRONMENT_NAME:-}"
HOP_ENVIRONMENT_CONFIG_FILE_NAME_PATHS="${HOP_ENVIRONMENT_CONFIG_FILE_NAME_PATHS:-}"

# 离线包根目录：默认脚本所在目录的父目录
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PKG_DIR="$(dirname "$SCRIPT_DIR")"

# --------------------- 参数解析 ---------------------
while [[ $# -gt 0 ]]; do
    case "$1" in
        --pkg)   PKG_DIR="$2"; shift 2 ;;
        --base)  INSTALL_BASE="$2"; shift 2 ;;
        --port)  TOMCAT_PORT="$2"; shift 2 ;;
        --heap)  HEAP_SIZE="$2"; shift 2 ;;
        --heap-mode) HEAP_MODE="$2"; shift 2 ;;
        --log-level) LOG_LEVEL="$2"; shift 2 ;;
        --service) SERVICE_NAME="$2"; shift 2 ;;
        --uid)   HOP_UID="$2"; shift 2 ;;
        --gid)   HOP_GID="$2"; shift 2 ;;
        --instance) INSTANCE_NAME="$2"; shift 2 ;;
        --checksum) SKIP_CHECKSUM="false"; shift ;;
        --skip-checksum) SKIP_CHECKSUM="true"; shift ;;
        -h|--help) sed -n '2,35p' "$0"; exit 0 ;;
        *) err "未知参数: $1"; exit 1 ;;
    esac
done

# CATALINA_HOME(Tomcat 软件，只读) / CATALINA_BASE(运行实例，可写)
JAVA_HOME="${INSTALL_BASE}/jdk21"
CATALINA_HOME="${INSTALL_BASE}/tomcat"
if [ -n "$INSTANCE_NAME" ]; then
    CATALINA_BASE="${INSTALL_BASE}/tomcat-run-${INSTANCE_NAME}"
    [ "$SERVICE_NAME" = "qi-hop" ] && SERVICE_NAME="qi-hop-${INSTANCE_NAME}"
else
    CATALINA_BASE="${INSTALL_BASE}/tomcat-run"
fi
WEBAPP_DIR="${CATALINA_BASE}/webapps/ROOT"

# Hop 数据目录全部位于 CATALINA_BASE 下(对齐 dev-scripts/start-web-dev.sh)
HOP_AUDIT_FOLDER="${CATALINA_BASE}/audit"
HOP_CONFIG_FOLDER="${CATALINA_BASE}/config"
HOP_PLUGIN_BASE_FOLDERS="${CATALINA_BASE}/plugins"
HOP_SHARED_JDBC_FOLDERS="${CATALINA_BASE}/jdbc-drivers"
HOP_REST_CONFIG_FOLDER="${CATALINA_BASE}/config"
RWT_RESOURCE_LOCATION="${CATALINA_BASE}/rwt-resources"
HOP_DOCS_FOLDER="${CATALINA_BASE}/docs"

# --------------------- 前置检查 ---------------------
check_prerequisites() {
    log "执行前置检查..."

    # root 权限
    if [ "$EUID" -ne 0 ]; then
        err "请使用 root 用户或 sudo 执行"
        exit 1
    fi

    # 命令依赖
    local cmds=("tar" "unzip" "ss" "curl")
    for c in "${cmds[@]}"; do
        command -v "$c" >/dev/null 2>&1 || { err "缺少命令: $c"; exit 1; }
    done

    # 离线包完整性
    local missing=0
    [ -f "$PKG_DIR/MANIFEST.txt" ] || warn "未找到 MANIFEST.txt (非标准离线包)"

    local jdk_pkg tomcat_pkg
    jdk_pkg=$(ls "$PKG_DIR"/jdk/jdk-21*linux*.tar.gz 2>/dev/null | head -1 || true)
    tomcat_pkg=$(ls "$PKG_DIR"/tomcat/apache-tomcat-10*.tar.gz 2>/dev/null | head -1 || true)

    [ -z "$jdk_pkg" ] && { err "未找到 JDK 21 离线包: $PKG_DIR/jdk/"; missing=1; }
    [ -z "$tomcat_pkg" ] && { err "未找到 Tomcat 10 离线包: $PKG_DIR/tomcat/"; missing=1; }

    if [ ! -f "$PKG_DIR/hop/hop.war" ]; then
        err "未找到 hop.war: $PKG_DIR/hop/hop.war"
        missing=1
    fi
    if ! ls "$PKG_DIR"/hop/hop-client-*.zip >/dev/null 2>&1; then
        err "未找到 Hop Client 包"
        missing=1
    fi

    [ "$missing" -eq 1 ] && { err "离线包不完整"; exit 1; }

    # SHA256 校验(若存在校验文件且未跳过)
    if [ "$SKIP_CHECKSUM" != "true" ] && [ -f "$PKG_DIR/SHA256SUMS" ]; then
        log "执行 SHA256 校验..."
        ( cd "$PKG_DIR" && sha256sum -c SHA256SUMS --quiet 2>/dev/null ) || warn "部分文件校验失败，请确认离线包完整性"
    elif [ "$SKIP_CHECKSUM" = "true" ]; then
        info "跳过 SHA256 校验(SKIP_CHECKSUM=true)"
    fi

    # 端口检查
    if ss -tlnp 2>/dev/null | awk '{print $4}' | grep -Eq ":${TOMCAT_PORT}\$"; then
        err "端口 ${TOMCAT_PORT} 已被占用，请释放后重试"
        err "  查看占用进程: ss -tlnp | grep ':${TOMCAT_PORT} '"
        exit 1
    fi

    log "前置检查通过"
}

# --------------------- 创建用户与目录 ---------------------
setup_user_and_dirs() {
    log "创建用户和目录..."

    # 对齐 Dockerfile: groupadd -r qi -g ${HOP_GID}
    getent group "$HOP_GROUP" >/dev/null 2>&1 || groupadd -r "$HOP_GROUP" -g "$HOP_GID"
    # 对齐 Dockerfile: useradd -d /home/qi -u ${HOP_UID} -m -s /bin/bash -g qi qi
    if ! id -u "$HOP_USER" >/dev/null 2>&1; then
        useradd -d "$HOP_USER_HOME" -u "$HOP_UID" -m -s /bin/bash -g "$HOP_GROUP" -G root "$HOP_USER"
    else
        usermod -aG root "$HOP_USER"
    fi

    # CATALINA_BASE 运行实例目录(对齐 dev-scripts/start-web-dev.sh 的 tomcat-run/ 结构)
    # 软件目录 CATALINA_HOME(tomcat/)由 install_tomcat 创建
    mkdir -p "${CATALINA_BASE}"/{bin,webapps,logs,temp,work}
    mkdir -p "${CATALINA_BASE}"/{audit,config,plugins,jdbc-drivers,rwt-resources}

    # 对齐 Dockerfile: mkdir -p "$CATALINA_HOME"/lib/swt/linux/x86_64
    # SWT native 库放在 CATALINA_HOME/lib(Tomcat 共享 lib)
    mkdir -p "${CATALINA_HOME}/lib/swt/linux/x86_64"

    log "实例目录: ${CATALINA_BASE}"
    log "用户和目录创建完成"
}

# --------------------- 安装 JDK ---------------------
install_jdk() {
    log "安装 JDK 21..."
    local jdk_pkg
    jdk_pkg=$(ls "$PKG_DIR"/jdk/jdk-21*linux*.tar.gz 2>/dev/null | head -1)

    tar -xzf "$jdk_pkg" -C "$INSTALL_BASE"

    local extracted_dir
    extracted_dir=$(find "$INSTALL_BASE" -maxdepth 1 -type d -name "jdk-21*" | head -1)
    if [ -n "$extracted_dir" ] && [ "$extracted_dir" != "$JAVA_HOME" ]; then
        rm -rf "$JAVA_HOME"
        mv "$extracted_dir" "$JAVA_HOME"
    fi

    if "$JAVA_HOME/bin/java" -version 2>&1 | grep -q "version"; then
        log "JDK 21 安装成功"
        "$JAVA_HOME/bin/java" -version
    else
        err "JDK 21 安装失败"; exit 1
    fi
}

# --------------------- 安装 Tomcat ---------------------
install_tomcat() {
    log "安装 Tomcat 10..."
    local tomcat_pkg
    tomcat_pkg=$(ls "$PKG_DIR"/tomcat/apache-tomcat-10*.tar.gz 2>/dev/null | head -1)

    # 解压到 CATALINA_HOME(Tomcat 软件目录，只读)
    tar -xzf "$tomcat_pkg" -C "$INSTALL_BASE"

    local extracted_dir
    extracted_dir=$(find "$INSTALL_BASE" -maxdepth 1 -type d -name "apache-tomcat-10*" | head -1)
    if [ -n "$extracted_dir" ] && [ "$extracted_dir" != "$CATALINA_HOME" ]; then
        rm -rf "$CATALINA_HOME"
        mv "$extracted_dir" "$CATALINA_HOME"
    fi

    # 清理 CATALINA_HOME 的默认 webapp(实例 webapps 在 CATALINA_BASE)
    rm -rf "${CATALINA_HOME}/webapps/"*

    # 首次安装：从 CATALINA_HOME/conf 复制模板到 CATALINA_BASE/conf
    # 多实例模式：每个实例独立 server.xml(端口不同)
    if [ ! -d "${CATALINA_BASE}/conf" ]; then
        cp -r "${CATALINA_HOME}/conf" "${CATALINA_BASE}/conf"
        log "已初始化实例配置: ${CATALINA_BASE}/conf"
    else
        warn "实例配置已存在，保留: ${CATALINA_BASE}/conf"
    fi

    # 修改 CATALINA_BASE/conf/server.xml 端口
    # - HTTP 端口：始终使用 ${TOMCAT_PORT}
    # - shutdown/AJP 端口：多实例时需要错开，避免同机冲突
    sed -i "s/port=\"8080\"/port=\"${TOMCAT_PORT}\"/" "${CATALINA_BASE}/conf/server.xml"

    if [ -n "$INSTANCE_NAME" ]; then
        # 多实例：根据 --port 推算错开 shutdown/AJP 端口(仅当 server.xml 仍是默认值时)
        local shutdown_port=$((TOMCAT_PORT - 75))   # 8080 -> 8005
        local ajp_port=$((TOMCAT_PORT + 1))         # 8080 -> 8081(避开 HTTP 端口)
        sed -i "s/port=\"8005\"/port=\"${shutdown_port}\"/" "${CATALINA_BASE}/conf/server.xml"
        sed -i "s/port=\"8009\"/port=\"${ajp_port}\"/" "${CATALINA_BASE}/conf/server.xml"
        log "多实例端口分配: HTTP=${TOMCAT_PORT}, shutdown=${shutdown_port}, AJP=${ajp_port}"
    fi

    log "Tomcat 10 安装完成(CATALINA_HOME=${CATALINA_HOME}, CATALINA_BASE=${CATALINA_BASE}, 端口=${TOMCAT_PORT})"
}

# --------------------- 部署 Hop 应用 ---------------------
deploy_hop() {
    log "部署 Hop Web..."

    # 1. 解压 WAR 到 ROOT(先创建目录，避免 cd 失败导致后续命令在错误目录执行)
    mkdir -p "$WEBAPP_DIR"
    unzip -q "$PKG_DIR/hop/hop.war" -d "$WEBAPP_DIR"

    # 2. 从 Client 包补充核心库(hop.war 不含 hop-core/hop-engine)
    local client_zip
    client_zip=$(ls "$PKG_DIR"/hop/hop-client-*.zip 2>/dev/null | head -1 || true)
    if [ -z "$client_zip" ]; then
        err "未找到 hop-client-*.zip: $PKG_DIR/hop/"
        exit 1
    fi
    local work_dir="/tmp/hop-client-extract-$$"
    rm -rf "$work_dir"; mkdir -p "$work_dir"
    unzip -q "$client_zip" -d "$work_dir"

    cp "$work_dir"/hop/lib/core/*.jar "$WEBAPP_DIR/WEB-INF/lib/" 2>/dev/null || true
    cp "$work_dir"/hop/lib/beam/*.jar "$WEBAPP_DIR/WEB-INF/lib/" 2>/dev/null || true

    # 3. 清理 RAP 模式下的冲突 jar
    #    hop-client-*.zip 的 lib/core/ 是为桌面 RCP 准备的，包含 RAP Web 模式下冲突或冗余的 jar。
    #    策略对齐 start-hop-web.sh:312-314(纯 RAP 策略 B)+ 问题集锦.md 的根因分析。
    #    冲突 jar 的来源：rcp/src/assembly/assembly.xml 用 <scope>runtime</scope> 把
    #    hop-ui-rcp 及其 runtime 依赖(tm4e.core/jface.text)打包到 lib/core/。
    clean_rap_conflicts() {
        local lib_dir="$WEBAPP_DIR/WEB-INF/lib"
        local removed=0
        # 模式 | 清理原因
        # hop-ui-rcp*: 与 hop-ui-rap 重复提供 TextSizeUtilFacadeImpl，且引用 RAP 缺失的 SWT 方法
        # org.eclipse.tm4e.core*: RCP 专用代码编辑器核心(hop-ui-rcp runtime 依赖，rap/ui/core 均不使用)
        # org.eclipse.jface.text*: RCP 专用 JFace 文本(hop-ui-rcp runtime 依赖，rap/ui/core 均不使用)
        # org.eclipse.swt.*: 所有桌面 SWT 实现，RAP 用 org.eclipse.rap.rwt 作为唯一 SWT 实现；
        #   桌面 SWT 会导致 Invalid thread access 或 native 库加载失败
        local patterns=(
            'hop-ui-rcp*'
            'org.eclipse.tm4e.core*'
            'org.eclipse.jface.text*'
            'org.eclipse.swt.cocoa*'
            'org.eclipse.swt.gtk.linux*'
            'org.eclipse.swt.win32*'
        )
        local pattern matches m
        for pattern in "${patterns[@]}"; do
            matches=$(ls "$lib_dir"/$pattern 2>/dev/null || true)
            if [ -n "$matches" ]; then
                for m in $matches; do
                    warn "  清理冲突 jar: $(basename "$m")"
                done
                rm -f "$lib_dir"/$pattern
                removed=1
            fi
        done
        if [ "$removed" = "1" ]; then
            log "RAP 冲突 jar 清理完成"
        else
            log "无冲突 jar 需要清理(war 可能已经是 RAP 干净版本)"
        fi
    }
    clean_rap_conflicts

    # 4. 复制配置(对齐 dev-scripts/start-web-dev.sh: config/ 下放 hop-config.json + projects/)
    cp -r "$work_dir"/hop/config/* "${HOP_CONFIG_FOLDER}/" 2>/dev/null || true

    # 5. 部署插件
    if ls "$PKG_DIR"/hop/hop-assemblies-plugins-*.zip >/dev/null 2>&1; then
        local plugins_zip
        plugins_zip=$(ls "$PKG_DIR"/hop/hop-assemblies-plugins-*.zip | head -1)
        local pwork="/tmp/hop-plugins-extract-$$"
        rm -rf "$pwork"; mkdir -p "$pwork/tmp"
        unzip -q "$plugins_zip" -d "$pwork/tmp"
        mv "$pwork/tmp/plugins/"* "${HOP_PLUGIN_BASE_FOLDERS}/" 2>/dev/null || true
        rm -rf "$pwork"
    fi

    # 6. JDBC 驱动
    cp "$PKG_DIR"/jdbc-drivers/*.jar "${HOP_SHARED_JDBC_FOLDERS}/" 2>/dev/null || warn "未发现 JDBC 驱动(可选)"

    # 6.1 GUI 配置：disabledGuiElements.xml(隐藏 Git / Search 等辅助透视图)
    #     对齐 start-hop-web.sh:347，运行时由 GuiRegistry 从 HOP_CONFIG_FOLDER 读取
    if [ -f "$PKG_DIR/config/disabledGuiElements.xml" ]; then
        cp "$PKG_DIR/config/disabledGuiElements.xml" "${HOP_CONFIG_FOLDER}/"
        log "已部署 GUI 配置: ${HOP_CONFIG_FOLDER}/disabledGuiElements.xml"
    else
        warn "未发现 config/disabledGuiElements.xml(可选，辅助透视图将保持显示)"
    fi

    # 6.2 RWT 资源缓存目录：仅创建空目录并赋权
    #     xterm/monaco 等静态资源已在 war 的 WEB-INF/classes 内，
    #     HopWeb.registerXtermResources() 通过 RWT addResource() 注册，
    #     RWT 启动时按需写入 resourceLocation，无需预拷贝。
    mkdir -p "$RWT_RESOURCE_LOCATION"
    chmod 755 "$RWT_RESOURCE_LOCATION"

    # 6.3 离线部署说明书部署(hop-assistant-manual 文档已通过 Maven 打包进 WAR classpath，无需单独部署)
    # HOP_DOCS_FOLDER 已在派生路径区定义，此处仅创建目录
    mkdir -p "$HOP_DOCS_FOLDER"
    if [ -f "$PKG_DIR/docs/离线部署说明书.md" ]; then
        cp "$PKG_DIR/docs/离线部署说明书.md" "$HOP_DOCS_FOLDER/"
        log "已部署离线部署说明书: ${HOP_DOCS_FOLDER}/离线部署说明书.md"
    fi

    # 7. CLI 脚本与 classpath 修正(对齐 Dockerfile 第 85-90 行)
    cp "$work_dir"/hop/*.sh "$WEBAPP_DIR/" 2>/dev/null || true
    chmod +x "$WEBAPP_DIR"/*.sh 2>/dev/null || true
    # 对齐 Dockerfile: sed -i 's&lib/core/*&../../lib/*:WEB-INF/lib/*:lib/core/*&g'
    for s in hop-run.sh hop-conf.sh hop-search.sh hop-encrypt.sh hop-import.sh; do
        [ -f "$WEBAPP_DIR/$s" ] && sed -i 's&lib/core/\*&../../lib/*:WEB-INF/lib/*:lib/core/*&g' "$WEBAPP_DIR/$s"
    done

    # 8. 修正 hop-config.json 路径(对齐 Dockerfile 第 80 行)
    # Dockerfile: sed -i 's/config\/projects/${HOP_CONFIG_FOLDER}\/projects/g'
    # 主机部署模式：直接替换为绝对路径(更直观)
    if [ -f "${HOP_CONFIG_FOLDER}/hop-config.json" ]; then
        sed -i "s|config/projects|${HOP_CONFIG_FOLDER}/projects|g" "${HOP_CONFIG_FOLDER}/hop-config.json"
    fi

    # 9. SWT native 库目录(对齐 Dockerfile 第 82 行，CATALINA_HOME/lib/swt/linux/x86_64)
    #    纯 RAP 模式下桌面 SWT 已清理，此目录保留为兼容性占位(hop-core 部分代码会探测)
    mkdir -p "${CATALINA_HOME}/lib/swt/linux/x86_64"

    rm -rf "$work_dir"
    log "Hop Web 部署完成"
}

# --------------------- 生成 setenv.sh(对齐 docker/web.Dockerfile ENV)---------------------
generate_setenv() {
    log "生成 setenv.sh..."

    # 计算 JVM 堆参数(根据 HEAP_MODE)
    local jvm_heap_opts=""
    if [ "$HEAP_MODE" = "aggressive" ]; then
        # 对齐 Dockerfile: HOP_OPTIONS="-XX:+AggressiveHeap"
        jvm_heap_opts="-XX:+AggressiveHeap"
    else
        jvm_heap_opts="-Xms${HEAP_SIZE} -Xmx${HEAP_SIZE} -XX:+UseG1GC -XX:MaxGCPauseMillis=200"
    fi

    cat > "${CATALINA_BASE}/bin/setenv.sh" <<ENVEOF
#!/bin/bash
# =============================================================
# Qi Hop Tomcat 环境变量配置(实例: ${INSTANCE_NAME:-default})
# 生成时间: $(date '+%Y-%m-%d %H:%M:%S')
# 对齐参考: docker/web.Dockerfile + dev-scripts/start-web-dev.sh
# =============================================================

# ----- Java 运行时 -----
export JAVA_HOME="${JAVA_HOME}"
export JRE_HOME="${JAVA_HOME}"
export CATALINA_HOME="${CATALINA_HOME}"
export CATALINA_BASE="${CATALINA_BASE}"
export CATALINA_OUT="${CATALINA_BASE}/logs/catalina.out"

# ----- Hop 核心环境变量(数据目录全部位于 CATALINA_BASE 下)-----
export HOP_CONFIG_FOLDER="${HOP_CONFIG_FOLDER}"
export HOP_AUDIT_FOLDER="${HOP_AUDIT_FOLDER}"
export HOP_LOG_LEVEL="${LOG_LEVEL}"
export HOP_PASSWORD_ENCODER_PLUGIN="Hop"
export HOP_PLUGIN_BASE_FOLDERS="${HOP_PLUGIN_BASE_FOLDERS}"
export HOP_SHARED_JDBC_FOLDERS="${HOP_SHARED_JDBC_FOLDERS}"
export HOP_GUI_ZOOM_FACTOR="1.0"
export HOP_AES_ENCODER_KEY="\${HOP_AES_ENCODER_KEY:-}"
export HOP_REST_CONFIG_FOLDER="${HOP_REST_CONFIG_FOLDER}"

# ----- Hop 项目/环境变量(对齐 Dockerfile ENV)-----
export HOP_PROJECT_FOLDER="\${HOP_PROJECT_FOLDER:-${HOP_PROJECT_FOLDER}}"
export HOP_PROJECT_NAME="\${HOP_PROJECT_NAME:-${HOP_PROJECT_NAME}}"
export HOP_PROJECT_CONFIG_FILE_NAME="${HOP_PROJECT_CONFIG_FILE_NAME}"
export HOP_ENVIRONMENT_NAME="\${HOP_ENVIRONMENT_NAME:-${HOP_ENVIRONMENT_NAME}}"
export HOP_ENVIRONMENT_CONFIG_FILE_NAME_PATHS="\${HOP_ENVIRONMENT_CONFIG_FILE_NAME_PATHS:-${HOP_ENVIRONMENT_CONFIG_FILE_NAME_PATHS}}"

# ----- CATALINA_OPTS(对齐 Dockerfile ENV CATALINA_OPTS)-----
# JVM 堆与 GC(HEAP_MODE=${HEAP_MODE})
CATALINA_OPTS="${jvm_heap_opts}"

# OOM 自动 dump
CATALINA_OPTS="\${CATALINA_OPTS} -XX:+HeapDumpOnOutOfMemoryError"
CATALINA_OPTS="\${CATALINA_OPTS} -XX:HeapDumpPath=${CATALINA_BASE}/logs/"

# Hop 变量传递为 JVM 系统属性(对齐 Dockerfile CATALINA_OPTS)
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_AES_ENCODER_KEY=\\"\${HOP_AES_ENCODER_KEY}\\""
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_AUDIT_FOLDER=\\"\${HOP_AUDIT_FOLDER}\\""
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_CONFIG_FOLDER=\\"\${HOP_CONFIG_FOLDER}\\""
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_LOG_LEVEL=\\"\${HOP_LOG_LEVEL}\\""
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_PASSWORD_ENCODER_PLUGIN=\\"\${HOP_PASSWORD_ENCODER_PLUGIN}\\""
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_PLUGIN_BASE_FOLDERS=\\"\${HOP_PLUGIN_BASE_FOLDERS}\\""
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_SHARED_JDBC_FOLDERS=\\"\${HOP_SHARED_JDBC_FOLDERS}\\""
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_GUI_ZOOM_FACTOR=\\"\${HOP_GUI_ZOOM_FACTOR}\\""
CATALINA_OPTS="\${CATALINA_OPTS} -DHOP_REST_CONFIG_FOLDER=\\"\${HOP_REST_CONFIG_FOLDER}\\""

# RAP 资源缓存目录(对齐 Dockerfile -Dorg.eclipse.rap.rwt.resourceLocation)
# RWT 启动时从 war classpath 自填充 xterm/monaco 到此目录
CATALINA_OPTS="\${CATALINA_OPTS} -Dorg.eclipse.rap.rwt.resourceLocation=${RWT_RESOURCE_LOCATION}"

# 时区与编码(中国时区 CST)
CATALINA_OPTS="\${CATALINA_OPTS} -Duser.timezone=Asia/Shanghai -Dfile.encoding=UTF-8 -Duser.language=zh -Duser.country=CN"
# 解决 Tomcat 启动慢(/dev/random 熵不足)
CATALINA_OPTS="\${CATALINA_OPTS} -Djava.security.egd=file:/dev/./urandom"

# Java 模块开放(Java 9+ 模块系统兼容性，对齐 .mvn/jvm.config)
CATALINA_OPTS="\${CATALINA_OPTS} --add-opens=java.base/java.lang=ALL-UNNAMED"
CATALINA_OPTS="\${CATALINA_OPTS} --add-opens=java.base/java.net=ALL-UNNAMED"
CATALINA_OPTS="\${CATALINA_OPTS} --add-opens=java.base/java.io=ALL-UNNAMED"
CATALINA_OPTS="\${CATALINA_OPTS} --add-opens=java.base/java.util=ALL-UNNAMED"
CATALINA_OPTS="\${CATALINA_OPTS} --add-opens=java.base/java.nio=ALL-UNNAMED"
CATALINA_OPTS="\${CATALINA_OPTS} --add-opens=java.base/sun.nio.ch=ALL-UNNAMED"
CATALINA_OPTS="\${CATALINA_OPTS} --add-opens=java.xml/com.sun.org.apache.xerces.internal.parsers=ALL-UNNAMED"

export CATALINA_OPTS
ENVEOF

    chmod +x "${CATALINA_BASE}/bin/setenv.sh"
    log "setenv.sh 生成完成: ${CATALINA_BASE}/bin/setenv.sh(HEAP_MODE=${HEAP_MODE})"
}

# --------------------- 注册 systemd 服务 ---------------------
setup_systemd() {
    log "注册 systemd 服务: ${SERVICE_NAME}"
    cat > "/etc/systemd/system/${SERVICE_NAME}.service" <<EOF
[Unit]
Description=Qi Hop Web Service (instance: ${INSTANCE_NAME:-default})
Documentation=https://hop.apache.org/
After=network.target

[Service]
Type=forking
PIDFile=${CATALINA_BASE}/temp/tomcat.pid
User=${HOP_USER}
Group=${HOP_GROUP}
Environment="JAVA_HOME=${JAVA_HOME}"
Environment="JRE_HOME=${JAVA_HOME}"
Environment="CATALINA_PID=${CATALINA_BASE}/temp/tomcat.pid"
Environment="CATALINA_HOME=${CATALINA_HOME}"
Environment="CATALINA_BASE=${CATALINA_BASE}"
Environment="CATALINA_OUT=${CATALINA_BASE}/logs/catalina.out"
ExecStart=${CATALINA_HOME}/bin/startup.sh
ExecStop=${CATALINA_HOME}/bin/shutdown.sh
RestartSec=10
Restart=on-failure
LimitNOFILE=65536
LimitNPROC=65536
WorkingDirectory=${CATALINA_BASE}

[Install]
WantedBy=multi-user.target
EOF

    # 设置权限：实例目录归 qi 用户；CATALINA_HOME 软件目录保持可读
    chown -R "${HOP_USER}:${HOP_GROUP}" "${CATALINA_BASE}"
    chown -R "${HOP_USER}:${HOP_GROUP}" "${CATALINA_HOME}/lib/swt"
    systemctl daemon-reload
    systemctl enable "${SERVICE_NAME}"
    log "systemd 服务注册完成(已设置开机自启)"
}

# --------------------- 部署摘要 ---------------------
print_summary() {
    echo
    info "=================== 部署摘要(tomcat + tomcat-run 分离)==================="
    info " 安装根目录   : ${INSTALL_BASE}"
    info " 实例名       : ${INSTANCE_NAME:-default}"
    info " 运行用户     : ${HOP_USER} (UID=${HOP_UID}, GID=${HOP_GID}, home=${HOP_USER_HOME})"
    info " JDK          : ${JAVA_HOME}"
    info " CATALINA_HOME: ${CATALINA_HOME}(只读 Tomcat 软件)"
    info " CATALINA_BASE: ${CATALINA_BASE}(运行实例)"
    info " Web 应用     : ${WEBAPP_DIR}"
    info " 服务端口     : ${TOMCAT_PORT}"
    info " 服务名称     : ${SERVICE_NAME}"
    info " JVM 堆策略   : ${HEAP_MODE} $([ "$HEAP_MODE" = "fixed" ] && echo "(${HEAP_SIZE})" || echo "(AggressiveHeap)")"
    info " 日志级别     : ${LOG_LEVEL}"
    info " 配置目录     : ${HOP_CONFIG_FOLDER}"
    info " 审计目录     : ${HOP_AUDIT_FOLDER}"
    info " 插件目录     : ${HOP_PLUGIN_BASE_FOLDERS}"
    info " JDBC 目录    : ${HOP_SHARED_JDBC_FOLDERS}"
    info " RWT 缓存     : ${RWT_RESOURCE_LOCATION}(RWT 运行时自填充)"
    info " SWT 目录     : ${CATALINA_HOME}/lib/swt/linux/x86_64"
    info " 文档目录     : ${HOP_DOCS_FOLDER}(含 hop-assistant-manual 插件帮助文档)"
    info " setenv.sh    : ${CATALINA_BASE}/bin/setenv.sh"
    info "======================================================"
    echo
    info "下一步："
    info "  1) 启动服务: sudo bash ${SCRIPT_DIR}/hop-start.sh start ${SERVICE_NAME}"
    info "  2) 验证部署: sudo bash ${SCRIPT_DIR}/hop-verify.sh"
    info "  3) 浏览器访问: http://<服务器IP>:${TOMCAT_PORT}/ui"
    echo
    info "多实例部署示例(同机第二个 Hop Web，端口 9090)："
    info "  sudo bash $0 --instance qi-hop-002 --port 9090"
}

# --------------------- 主流程 ---------------------
main() {
    log "Qi Hop 离线部署(实例: ${INSTANCE_NAME:-default}, 端口: ${TOMCAT_PORT})"
    check_prerequisites
    setup_user_and_dirs
    install_jdk
    install_tomcat
    deploy_hop
    generate_setenv
    setup_systemd
    print_summary
}

main "$@"
