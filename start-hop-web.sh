#!/bin/bash
set -e

cd "$(dirname "$0")"

HOP_HOME=$(pwd)
TOMCAT_VERSION="10.1.28"
TOMCAT_URL="https://archive.apache.org/dist/tomcat/tomcat-10/v${TOMCAT_VERSION}/bin/apache-tomcat-${TOMCAT_VERSION}.tar.gz"
TOMCAT_DIR="${HOP_HOME}/tomcat-${TOMCAT_VERSION}"
CATALINA_HOME="${TOMCAT_DIR}"
CATALINA_BASE="${HOP_HOME}/tomcat-run"
WEBAPP_DIR="${CATALINA_BASE}/webapps"
LOG_DIR="${CATALINA_BASE}/logs"

FORCE_BUILD=false
MANUAL_OS=""
MANUAL_ARCH=""
RESTART_ONLY=false
SYNC_DOCS=false

show_help() {
    cat << HELPEOF
用法: $(basename "$0") [选项]

选项:
  --force-build, -fb       强制重新编译 Hop assemblies
  --os <OS>                手动指定目标平台: linux | mac | windows
                           (默认自动检测)
  --arch <ARCH>            手动指定 CPU 架构: x86_64 | aarch64 | arm64
                           (默认自动检测)
  --no-llm                 跳过 LiteLLM 代理与向量服务启动
  --restart, -r            仅重启 Tomcat（跳过部署），用于热更新 JAR 后快速生效
  --sync-docs              仅同步文档资源（url-mapping.json + md 文件），然后重启
  --help, -h               显示此帮助信息

示例:
  $(basename "$0")                                    # 自动检测平台，跳过编译
  $(basename "$0") --force-build                      # 自动检测平台，强制编译
  $(basename "$0") --force-build --os linux --arch aarch64  # 交叉编译 Linux ARM64
  $(basename "$0") --os linux --arch x86_64           # 指定 Linux x86_64 平台
  $(basename "$0") --restart                          # 重启 Tomcat（JAR 热更新场景）
  $(basename "$0") --sync-docs                        # 仅同步文档资源后重启（文档更新场景）
HELPEOF
    exit 0
}

while [ $# -gt 0 ]; do
    case "$1" in
        --force-build|-fb)
            FORCE_BUILD=true
            shift
            ;;
        --os)
            MANUAL_OS="$2"
            shift 2
            ;;
        --arch)
            MANUAL_ARCH="$2"
            shift 2
            ;;
        --no-llm)
            export HOP_DOCKER_ENABLED=false
            shift
            ;;
        --restart|-r)
            RESTART_ONLY=true
            shift
            ;;
        --sync-docs)
            SYNC_DOCS=true
            shift
            ;;
        --help|-h)
            show_help
            ;;
        *)
            echo "ERROR: Unknown option: $1"
            echo "Run '$(basename "$0") --help' for usage."
            exit 1
            ;;
    esac
done

# ============================================================
# OS/Arch Detection & Configuration
# ============================================================
detect_os_arch() {
    # 默认值：自动检测
    OS_NAME=$(uname -s)
    OS_ARCH=$(uname -m)

    # 手动覆盖 OS
    if [ -n "${MANUAL_OS}" ]; then
        case "${MANUAL_OS}" in
            linux)   OS_NAME="Linux" ;;
            mac|macos|darwin) OS_NAME="Darwin" ;;
            windows|win) OS_NAME="MINGW64_NT" ;;
            *)
                echo "ERROR: Unsupported --os: ${MANUAL_OS} (supported: linux, mac, windows)"
                exit 1
                ;;
        esac
    fi

    # 手动覆盖 Arch
    if [ -n "${MANUAL_ARCH}" ]; then
        OS_ARCH="${MANUAL_ARCH}"
    fi

    # 根据 OS_FAMILY + ARCH 推导 SWT_ARTIFACT_ID
    case "${OS_NAME}" in
        Linux)
            OS_FAMILY="linux"
            case "${OS_ARCH}" in
                aarch64|arm64) SWT_ARTIFACT_ID="org.eclipse.swt.gtk.linux.aarch64" ;;
                *)             SWT_ARTIFACT_ID="org.eclipse.swt.gtk.linux.x86_64" ;;
            esac
            ;;
        Darwin)
            OS_FAMILY="mac"
            case "${OS_ARCH}" in
                aarch64|arm64) SWT_ARTIFACT_ID="org.eclipse.swt.cocoa.macosx.aarch64" ;;
                *)             SWT_ARTIFACT_ID="org.eclipse.swt.cocoa.macosx.x86_64" ;;
            esac
            ;;
        CYGWIN*|MINGW*|MSYS*)
            OS_FAMILY="windows"
            SWT_ARTIFACT_ID="org.eclipse.swt.win32.win32.x86_64"
            ;;
        *)
            echo "ERROR: Unsupported OS: ${OS_NAME}"
            exit 1
            ;;
    esac
    MAVEN_PROFILES="assemblies"
}

detect_os_arch

echo "============================================="
echo "  Hop Web Local Development Server"
echo "============================================="
if [ -n "${MANUAL_OS}" ] || [ -n "${MANUAL_ARCH}" ]; then
    echo "  OS:      ${OS_FAMILY} (手动指定)"
    echo "  Arch:    ${OS_ARCH} (手动指定)"
else
    echo "  OS:      ${OS_NAME}"
    echo "  Arch:    ${OS_ARCH}"
fi
echo "  SWT:     ${SWT_ARTIFACT_ID}"
echo "============================================="

# ============================================================
# 停止 Tomcat 的通用函数
# ============================================================
stop_tomcat() {
    echo "==> Stopping existing Tomcat instance..."
    if [ -f "${CATALINA_BASE}/work/catalina.pid" ] || pgrep -f "catalina" > /dev/null 2>&1; then
        "${CATALINA_HOME}/bin/catalina.sh" stop 10 > /dev/null 2>&1 || true
        if pgrep -f "catalina" > /dev/null 2>&1; then
            echo "    Force killing remaining Tomcat process..."
            pkill -9 -f "catalina" || true
            sleep 2
        fi
        echo "    Tomcat stopped"
    else
        echo "    No running Tomcat instance found"
    fi
}

# ============================================================
# --restart 快捷路径：跳过部署，仅重启 Tomcat（用于 JAR 热更新）
# ============================================================
if [ "${RESTART_ONLY}" = "true" ]; then
    echo ""
    echo "==> [Restart Mode] 跳过部署，直接重启 Tomcat..."
    stop_tomcat
    # 跳到启动部分（跳过 PHASE 1~3 和部署）
    goto_start_tomcat=true
fi

# ============================================================
# --sync-docs 快捷路径：仅同步文档资源，然后重启 Tomcat
# ============================================================
if [ "${SYNC_DOCS}" = "true" ]; then
    echo ""
    echo "==> [Sync-Docs Mode] 同步文档资源到已部署的 WAR..."

    DEPLOYED_MAPPING="${WEBAPP_DIR}/ROOT/WEB-INF/classes/org/apache/hop/rest/docs/url-mapping.json"
    SOURCE_MAPPING="${HOP_HOME}/docs/hop-assistant-manual/url-mapping.json"

    if [ ! -d "${WEBAPP_DIR}/ROOT" ]; then
        echo "ERROR: WAR 未部署，请先运行 $(basename "$0")（不带 --sync-docs）"
        exit 1
    fi

    stop_tomcat

    echo "    更新 url-mapping.json..."
    if [ -f "${SOURCE_MAPPING}" ]; then
        mkdir -p "$(dirname "${DEPLOYED_MAPPING}")"
        cp "${SOURCE_MAPPING}" "${DEPLOYED_MAPPING}"
        echo "    ✓ url-mapping.json 已更新"
    else
        echo "    Warning: ${SOURCE_MAPPING} 不存在"
    fi

    echo ""
    echo "    文档 MD 文件无需复制（通过 CWD 直接访问 ${HOP_HOME}/docs/hop-assistant-manual/）"

    echo ""
    echo "==> 文档同步完成，正在重启 Tomcat..."
    goto_start_tomcat=true
fi

if [ "${goto_start_tomcat:-false}" != "true" ]; then

# ============================================================
# PHASE 1: Assemblies Package Installation
# ============================================================
echo ""
echo "╔══════════════════════════════════════════════════════════╗"
echo "║                  PHASE 1: Assemblies Installation        ║"
echo "╚══════════════════════════════════════════════════════════╝"

if [ "${FORCE_BUILD}" = "true" ]; then
    echo ""
    echo "==> Building Hop assemblies..."
    ./mvnw clean install -Dfast-build -DskipTests \
      -Dswt.artifactId=${SWT_ARTIFACT_ID} \
      -Denv=${OS_FAMILY} \
      -P${MAVEN_PROFILES}
else
    echo ""
    echo "==> Skipping build (use --force-build or -fb to enable)"
fi

CLIENT_ZIP=$(ls assemblies/client/target/hop-client-*.zip 2>/dev/null | head -1)
if [ -n "$CLIENT_ZIP" ]; then
    echo "    ✓ Client assembly: $(basename "$CLIENT_ZIP")"
else
    echo "ERROR: Client assembly zip not found"
    exit 1
fi

PLUGINS_ZIP=$(ls assemblies/plugins/target/hop-assemblies-plugins-*-SNAPSHOT.zip 2>/dev/null | head -1)
if [ -n "$PLUGINS_ZIP" ]; then
    echo "    ✓ Plugins assembly: $(basename "$PLUGINS_ZIP")"
else
    echo "ERROR: Plugins assembly zip not found"
    exit 1
fi

STATIC_ZIP=$(ls assemblies/static/target/hop-assemblies-static-*-SNAPSHOT.zip 2>/dev/null | head -1)
if [ -n "$STATIC_ZIP" ]; then
    echo "    ✓ Static assembly: $(basename "$STATIC_ZIP")"
fi

# ============================================================
# PHASE 2: WAR Deployment & Tomcat Configuration
# ============================================================
echo ""
echo "╔══════════════════════════════════════════════════════════╗"
echo "║             PHASE 2: WAR Deployment & Tomcat Setup       ║"
echo "╚══════════════════════════════════════════════════════════╝"

if [ ! -d "${TOMCAT_DIR}" ]; then
    echo ""
    echo "==> Downloading Apache Tomcat ${TOMCAT_VERSION}..."
    mkdir -p "${TOMCAT_DIR}"
    curl -sL "${TOMCAT_URL}" | tar -xzf - --strip-components=1 -C "${TOMCAT_DIR}"
fi

mkdir -p "${CATALINA_BASE}/lib"
mkdir -p "${CATALINA_BASE}/logs"
mkdir -p "${CATALINA_BASE}/work"
mkdir -p "${CATALINA_BASE}/temp"
mkdir -p "${WEBAPP_DIR}"
mkdir -p "${CATALINA_BASE}/audit"
mkdir -p "${CATALINA_BASE}/config"

if [ ! -d "${CATALINA_BASE}/conf" ]; then
    echo ""
    echo "==> Copying Tomcat config files..."
    cp -r "${CATALINA_HOME}/conf" "${CATALINA_BASE}/conf"
fi

echo ""
echo "==> Deploying hop.war..."
rm -rf "${WEBAPP_DIR}/ROOT"
rm -rf "${WEBAPP_DIR}/hop"
unzip -q "assemblies/web/target/hop.war" -d "${WEBAPP_DIR}/ROOT"

echo ""
echo "==> Copying core/beam libraries..."
rm -rf "${CATALINA_HOME}/hop-lib-extracted"
unzip -q -o "$CLIENT_ZIP" "hop/lib/core/*" "hop/lib/beam/*" -d "${CATALINA_HOME}/hop-lib-extracted"
cp "${CATALINA_HOME}/hop-lib-extracted/hop/lib/core/"*.jar "${WEBAPP_DIR}/ROOT/WEB-INF/lib/" 2>/dev/null || true
cp "${CATALINA_HOME}/hop-lib-extracted/hop/lib/beam/"*.jar "${WEBAPP_DIR}/ROOT/WEB-INF/lib/" 2>/dev/null || true
rm -rf "${CATALINA_HOME}/hop-lib-extracted"

echo ""
echo "==> Extracting plugins..."
rm -rf "${WEBAPP_DIR}/ROOT/WEB-INF/plugins"
mkdir -p "${WEBAPP_DIR}/ROOT/WEB-INF/plugins"
unzip -q "$PLUGINS_ZIP" -d "${WEBAPP_DIR}/ROOT/WEB-INF/plugins"
mv "${WEBAPP_DIR}/ROOT/WEB-INF/plugins/plugins/"* "${WEBAPP_DIR}/ROOT/WEB-INF/plugins/" 2>/dev/null || true
rm -rf "${WEBAPP_DIR}/ROOT/WEB-INF/plugins/plugins"

echo ""
echo "==> Removing conflicting jars..."
rm -f "${WEBAPP_DIR}/ROOT/WEB-INF/lib/hop-ui-rcp"*
rm -f "${WEBAPP_DIR}/ROOT/WEB-INF/lib/org.eclipse.swt."*

unzip -j -o "assemblies/web/target/hop.war" "WEB-INF/lib/hop-ui-*" -d "${WEBAPP_DIR}/ROOT/WEB-INF/lib/" >/dev/null 2>&1
rm -f "${WEBAPP_DIR}/ROOT/WEB-INF/lib/hop-ui-rcp"*

echo ""
echo "==> Setting up RAP resources..."
mkdir -p "${CATALINA_BASE}/rwt-resources/xterm"
if [ -d "${WEBAPP_DIR}/ROOT/xterm" ]; then
    cp "${WEBAPP_DIR}/ROOT/xterm/"* "${CATALINA_BASE}/rwt-resources/xterm/"
    echo "    Copied xterm resources"
else
    echo "    Warning: xterm resources not found in webapp"
fi

# ============================================================
# PHASE 3: External Resources Copying
# ============================================================
echo ""
echo "╔══════════════════════════════════════════════════════════╗"
echo "║              PHASE 3: External Resources Copying         ║"
echo "╚══════════════════════════════════════════════════════════╝"

echo ""
echo "==> Copying configuration files..."
HOP_CONFIG_SOURCE="${HOP_HOME}/assemblies/static/src/main/resources/config/hop-config.json"
if [ -f "${HOP_CONFIG_SOURCE}" ]; then
    cp "${HOP_CONFIG_SOURCE}" "${CATALINA_BASE}/config/"
    echo "    Copied hop-config.json"
else
    echo "    Warning: hop-config.json not found at ${HOP_CONFIG_SOURCE}"
fi

if [ -f "${HOP_HOME}/resources/disabledGuiElements.xml" ]; then
    cp "${HOP_HOME}/resources/disabledGuiElements.xml" "${CATALINA_BASE}/config/"
    echo "    Copied disabledGuiElements.xml"
else
    echo "    Warning: disabledGuiElements.xml not found"
fi

echo ""
echo "==> Copying projects..."
mkdir -p "${CATALINA_BASE}/config/projects"
for PROJECT_NAME in default samples; do
    PROJECT_SOURCE="${HOP_HOME}/assemblies/static/src/main/resources/config/projects/${PROJECT_NAME}"
    PROJECT_TARGET="${CATALINA_BASE}/config/projects/${PROJECT_NAME}"
    if [ -d "${PROJECT_SOURCE}" ]; then
        cp -r "${PROJECT_SOURCE}" "${CATALINA_BASE}/config/projects/"
        echo "    Copied ${PROJECT_NAME} project to ${PROJECT_TARGET}"
    else
        echo "    Warning: ${PROJECT_NAME} project not found at ${PROJECT_SOURCE}"
    fi
done

# Stop running Tomcat instance (if any) so the new projects take effect
echo ""
stop_tomcat

echo ""
echo "==> Copying JDBC drivers..."
mkdir -p "${WEBAPP_DIR}/ROOT/WEB-INF/jdbc-drivers"
if [ -d "${HOP_HOME}/resources/jdbc-drivers" ]; then
    cp -r "${HOP_HOME}/resources/jdbc-drivers/"* "${WEBAPP_DIR}/ROOT/WEB-INF/jdbc-drivers/"
    echo "    Copied JDBC drivers"
else
    echo "    Warning: jdbc-drivers not found in resources/"
fi

echo ""
echo "==> Syncing documentation resources..."
DEPLOYED_MAPPING="${WEBAPP_DIR}/ROOT/WEB-INF/classes/org/apache/hop/rest/docs/url-mapping.json"
SOURCE_MAPPING="${HOP_HOME}/docs/hop-assistant-manual/url-mapping.json"
if [ -f "${SOURCE_MAPPING}" ]; then
    mkdir -p "$(dirname "${DEPLOYED_MAPPING}")"
    cp "${SOURCE_MAPPING}" "${DEPLOYED_MAPPING}"
    echo "    ✓ url-mapping.json synced to deployed WAR"
else
    echo "    Warning: url-mapping.json not found at ${SOURCE_MAPPING}"
fi
# Docs MD files are accessed via CWD (HOP_HOME), no copy needed
echo "    Docs accessible at: ${HOP_HOME}/docs/hop-assistant-manual/"

fi  # end of if [ "${goto_start_tomcat}" != "true" ]

# ============================================================
# CATALINA_OPTS — JVM & Hop system properties
# ============================================================

# Hop 环境变量（对齐官方 Docker 约定，可通过外部环境变量覆盖）
export HOP_AUDIT_FOLDER="${HOP_AUDIT_FOLDER:-${CATALINA_BASE}/audit}"
export HOP_CONFIG_FOLDER="${HOP_CONFIG_FOLDER:-${CATALINA_BASE}/config}"
export HOP_TEMP_FOLDER="${HOP_TEMP_FOLDER:-${CATALINA_BASE}/temp}"
export HOP_LOG_LEVEL="${HOP_LOG_LEVEL:-Basic}"
export HOP_PASSWORD_ENCODER_PLUGIN="${HOP_PASSWORD_ENCODER_PLUGIN:-Hop}"
export HOP_PLUGIN_BASE_FOLDERS="${HOP_PLUGIN_BASE_FOLDERS:-${WEBAPP_DIR}/ROOT/WEB-INF/plugins}"
export HOP_SHARED_JDBC_FOLDERS="${HOP_SHARED_JDBC_FOLDERS:-${WEBAPP_DIR}/ROOT/WEB-INF/jdbc-drivers}"
export HOP_AES_ENCODER_KEY="${HOP_AES_ENCODER_KEY:-}"
export HOP_GUI_ZOOM_FACTOR="${HOP_GUI_ZOOM_FACTOR:-1.0}"

# 项目与环境变量：默认为空（对齐 Docker 语义：空=不创建/注册项目）
# 这些变量用于控制项目注册逻辑，不是 Hop 运行时 JVM 参数
# Hop 运行时通过 hop-config.json 中注册的 projectHome（${HOP_CONFIG_FOLDER}/projects/xxx）自动推导 PROJECT_HOME
export HOP_PROJECT_FOLDER="${HOP_PROJECT_FOLDER:-}"
export HOP_PROJECT_NAME="${HOP_PROJECT_NAME:-}"
export HOP_PROJECT_CONFIG_FILE_NAME="${HOP_PROJECT_CONFIG_FILE_NAME:-project-config.json}"
export HOP_ENVIRONMENT_NAME="${HOP_ENVIRONMENT_NAME:-}"
export HOP_ENVIRONMENT_CONFIG_FILE_NAME_PATHS="${HOP_ENVIRONMENT_CONFIG_FILE_NAME_PATHS:-}"

CATALINA_OPTS="-Xmx4096m"
CATALINA_OPTS="${CATALINA_OPTS} -Xms1024m"
CATALINA_OPTS="${CATALINA_OPTS} -XX:+UseZGC -XX:+ZGenerational"
CATALINA_OPTS="${CATALINA_OPTS} -XX:MaxGCPauseMillis=100"
CATALINA_OPTS="${CATALINA_OPTS} -XX:+AlwaysPreTouch"
CATALINA_OPTS="${CATALINA_OPTS} -Djdk.attach.allowAttachSelf=true"

CATALINA_OPTS="${CATALINA_OPTS} -Duser.timezone=Asia/Shanghai -Dfile.encoding=UTF-8"
CATALINA_OPTS="${CATALINA_OPTS} -DHOP_AUDIT_FOLDER=${HOP_AUDIT_FOLDER}"
CATALINA_OPTS="${CATALINA_OPTS} -DHOP_CONFIG_FOLDER=${HOP_CONFIG_FOLDER}"
CATALINA_OPTS="${CATALINA_OPTS} -DHOP_TEMP_FOLDER=${HOP_TEMP_FOLDER}"
CATALINA_OPTS="${CATALINA_OPTS} -DHOP_PLUGIN_BASE_FOLDERS=${HOP_PLUGIN_BASE_FOLDERS}"
CATALINA_OPTS="${CATALINA_OPTS} -DHOP_SHARED_JDBC_FOLDERS=${HOP_SHARED_JDBC_FOLDERS}"
CATALINA_OPTS="${CATALINA_OPTS} -DHOP_AES_ENCODER_KEY=${HOP_AES_ENCODER_KEY}"
CATALINA_OPTS="${CATALINA_OPTS} -DHOP_GUI_ZOOM_FACTOR=${HOP_GUI_ZOOM_FACTOR}"
CATALINA_OPTS="${CATALINA_OPTS} -Dhop.docs.base=${HOP_HOME}/docs/hop-assistant-manual"
CATALINA_OPTS="${CATALINA_OPTS} -Dorg.eclipse.rap.rwt.resourceLocation=${CATALINA_BASE}/rwt-resources"
CATALINA_OPTS="${CATALINA_OPTS} -Dswt.use.gtk3=false"

if [ "${OS_FAMILY}" = "mac" ]; then
    CATALINA_OPTS="${CATALINA_OPTS} -Dapple.awt.UIElement=true"
    CATALINA_OPTS="${CATALINA_OPTS} -Dapple.awt.quitStrategy=CLOSE_ALL_WINDOWS"
    CATALINA_OPTS="${CATALINA_OPTS} -Dswt.enable.ime=true"
fi

CATALINA_OPTS="${CATALINA_OPTS} -DHOP_PASSWORD_ENCODER_PLUGIN=${HOP_PASSWORD_ENCODER_PLUGIN}"
CATALINA_OPTS="${CATALINA_OPTS} -DHOP_HIDE_DATABASE_PASSWORDS_IN_LOGS=Y"
CATALINA_OPTS="${CATALINA_OPTS} -DHOP_DISABLE_PLAIN_TEXT_CREDENTIALS=Y"
CATALINA_OPTS="${CATALINA_OPTS} -DHOP_MASK_SENSITIVE_OUTPUT=Y"
CATALINA_OPTS="${CATALINA_OPTS} -DHOP_DISABLE_SENSITIVE_DATA_LOGGING=Y"

CATALINA_OPTS="${CATALINA_OPTS} -DHOP_LOG_LEVEL=${HOP_LOG_LEVEL}"
CATALINA_OPTS="${CATALINA_OPTS} -DHOP_LOG_FILE_MAX_SIZE_MB=500"
CATALINA_OPTS="${CATALINA_OPTS} -DHOP_LOG_FILE_ROTATION_COUNT=10"
CATALINA_OPTS="${CATALINA_OPTS} -DHOP_LOG_BUFFER_LINES=5000"

CATALINA_OPTS="${CATALINA_OPTS} -DHOP_ROWSET_DEFAULT_SIZE=10000"
CATALINA_OPTS="${CATALINA_OPTS} -DHOP_DEFAULT_SORT_SIZE=500000"
CATALINA_OPTS="${CATALINA_OPTS} -DHOP_MEMORY_FREE_THRESHOLD_PERCENT=15"
CATALINA_OPTS="${CATALINA_OPTS} -DHOP_AUTO_CLEAN_TEMP_SORT_FILES=Y"
CATALINA_OPTS="${CATALINA_OPTS} -DHOP_CLEANUP_TEMP_FILES_ON_EXIT=Y"
CATALINA_OPTS="${CATALINA_OPTS} -DHOP_MAX_CONCURRENT_BACKGROUND_TASKS=4"
CATALINA_OPTS="${CATALINA_OPTS} -DHOP_USE_VIRTUAL_THREADS=Y"
CATALINA_OPTS="${CATALINA_OPTS} -DHOP_METRIC_FLUSH_INTERVAL_MS=30000"
CATALINA_OPTS="${CATALINA_OPTS} -DHOP_PREVIEW_BATCH_SIZE=100"

CATALINA_OPTS="${CATALINA_OPTS} --add-opens=java.xml/com.sun.org.apache.xalan.internal.xsltc.trax=ALL-UNNAMED"
CATALINA_OPTS="${CATALINA_OPTS} --add-opens=java.xml/jdk.xml.internal=ALL-UNNAMED"
CATALINA_OPTS="${CATALINA_OPTS} --add-opens=java.base/java.lang=ALL-UNNAMED"
CATALINA_OPTS="${CATALINA_OPTS} --add-opens=java.base/java.lang.invoke=ALL-UNNAMED"
CATALINA_OPTS="${CATALINA_OPTS} --add-opens=java.base/java.lang.reflect=ALL-UNNAMED"
CATALINA_OPTS="${CATALINA_OPTS} --add-opens=java.base/java.io=ALL-UNNAMED"
CATALINA_OPTS="${CATALINA_OPTS} --add-opens=java.base/java.net=ALL-UNNAMED"
CATALINA_OPTS="${CATALINA_OPTS} --add-opens=java.base/java.nio=ALL-UNNAMED"
CATALINA_OPTS="${CATALINA_OPTS} --add-opens=java.base/java.util=ALL-UNNAMED"
CATALINA_OPTS="${CATALINA_OPTS} --add-opens=java.base/java.util.concurrent=ALL-UNNAMED"
CATALINA_OPTS="${CATALINA_OPTS} --add-opens=java.base/java.util.concurrent.atomic=ALL-UNNAMED"
CATALINA_OPTS="${CATALINA_OPTS} --add-opens=java.base/sun.nio.ch=ALL-UNNAMED"
CATALINA_OPTS="${CATALINA_OPTS} --add-opens=java.base/sun.nio.cs=ALL-UNNAMED"
CATALINA_OPTS="${CATALINA_OPTS} --add-opens=java.base/sun.security.action=ALL-UNNAMED"
CATALINA_OPTS="${CATALINA_OPTS} --add-opens=java.base/sun.util.calendar=ALL-UNNAMED"
CATALINA_OPTS="${CATALINA_OPTS} --add-opens=java.security.jgss/sun.security.krb5=ALL-UNNAMED"
CATALINA_OPTS="${CATALINA_OPTS} --add-exports=java.base/sun.nio.ch=ALL-UNNAMED"

export CATALINA_HOME
export CATALINA_BASE
export CATALINA_OPTS

# ============================================================
# Optional Docker/LiteLLM Integration
# ============================================================
if [ "${HOP_DOCKER_ENABLED:-true}" = "true" ]; then
    echo ""
    echo "检查 LiteLLM 代理..."
    if command -v docker &>/dev/null && docker ps &>/dev/null; then
        if ! docker ps | grep -q "hop-litellm-dev"; then
            echo "启动 LiteLLM 代理..."
            cd "${HOP_HOME}"
            docker compose -f docker-compose.dev.yml up -d litellm
            sleep 3
            echo "LiteLLM 代理已启动"
        else
            echo "LiteLLM 代理已在运行"
        fi
    else
        echo "警告: Docker 未运行，跳过 LiteLLM 代理启动"
    fi

    echo ""
    echo "设置 LLM 环境变量..."
    export HOP_LLM_ENABLED=true
    export HOP_LLM_API_URL=http://localhost:4000/v1
    export HOP_LLM_API_KEY=sk-hop-litellm-dev
    export HOP_LLM_MODEL=qwen-plus

    echo "  HOP_LLM_ENABLED: ${HOP_LLM_ENABLED}"
    echo "  HOP_LLM_API_URL: ${HOP_LLM_API_URL}"
    echo "  HOP_LLM_MODEL: ${HOP_LLM_MODEL}"
fi

echo ""
echo "==> Starting Hop Web Server..."
echo "    Web UI:  http://localhost:8080/ui"
echo "    REST:    http://localhost:8080/api/v1/"
echo "    Docs:    http://localhost:8080/api/v1/docs/stats"
echo "    Logs:    ${LOG_DIR}/catalina.out"
echo ""

"${CATALINA_HOME}/bin/catalina.sh" run