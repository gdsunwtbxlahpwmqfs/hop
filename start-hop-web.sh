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

echo "============================================="
echo "  Hop Web Local Development Server"
echo "============================================="

if [ ! -f "assemblies/web/target/hop.war" ]; then
    echo ""
    echo "==> Building Hop Web assembly (macOS platform)..."
    ./mvnw clean install -Dfast-build -DskipTests \
      -Dswt.artifactId=org.eclipse.swt.cocoa.macosx.x86_64 \
      -Denv=mac \
      -P'!swt-linux,assemblies'
    if [ ! -f "assemblies/web/target/hop.war" ]; then
        echo "ERROR: Failed to build hop.war"
        exit 1
    fi
fi

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

if [ ! -d "${CATALINA_BASE}/conf" ]; then
    echo "==> Copying Tomcat config files..."
    cp -r "${CATALINA_HOME}/conf" "${CATALINA_BASE}/conf"
fi

rm -rf "${WEBAPP_DIR}/ROOT"
rm -rf "${WEBAPP_DIR}/hop"

echo ""
echo "==> Deploying hop.war..."
unzip -q "assemblies/web/target/hop.war" -d "${WEBAPP_DIR}/ROOT"

echo ""
echo "==> Copying core/beam libraries..."
CLIENT_ZIP=$(ls assemblies/client/target/hop-client-*.zip 2>/dev/null | head -1)
if [ -n "$CLIENT_ZIP" ]; then
    echo "    Extracting from client assembly..."
    rm -rf "${CATALINA_HOME}/hop-lib-extracted"
    unzip -q -o "$CLIENT_ZIP" "hop/lib/core/*" "hop/lib/beam/*" -d "${CATALINA_HOME}/hop-lib-extracted"
    cp "${CATALINA_HOME}/hop-lib-extracted/hop/lib/core/"*.jar "${WEBAPP_DIR}/ROOT/WEB-INF/lib/" 2>/dev/null || true
    cp "${CATALINA_HOME}/hop-lib-extracted/hop/lib/beam/"*.jar "${WEBAPP_DIR}/ROOT/WEB-INF/lib/" 2>/dev/null || true
    rm -rf "${CATALINA_HOME}/hop-lib-extracted"
else
    echo "    Warning: client assembly zip not found"
fi

echo ""
echo "==> Extracting plugins..."
PLUGINS_ZIP=$(ls assemblies/plugins/target/hop-assemblies-plugins-*-SNAPSHOT.zip 2>/dev/null | head -1)
if [ -n "$PLUGINS_ZIP" ]; then
    echo "    Extracting plugins..."
    rm -rf "${WEBAPP_DIR}/ROOT/WEB-INF/plugins"
    mkdir -p "${WEBAPP_DIR}/ROOT/WEB-INF/plugins"
    unzip -q "$PLUGINS_ZIP" -d "${WEBAPP_DIR}/ROOT/WEB-INF/plugins"
    mv "${WEBAPP_DIR}/ROOT/WEB-INF/plugins/plugins/"* "${WEBAPP_DIR}/ROOT/WEB-INF/plugins/" 2>/dev/null || true
    rm -rf "${WEBAPP_DIR}/ROOT/WEB-INF/plugins/plugins"
else
    echo "    Warning: plugins zip not found"
fi

# Remove conflicting jars:
# - hop-ui-rcp: conflicts with hop-ui-rap (both provide TextSizeUtilFacadeImpl,
#   and RCP references SWT methods absent in RAP).
# - org.eclipse.swt.*: desktop SWT conflicts with RAP's built-in SWT implementation.
#   RAP provides its own org.eclipse.swt.widgets.Display via org.eclipse.rap.rwt;
#   the desktop SWT jar causes "Invalid thread access" errors at Display creation.
rm -f "${WEBAPP_DIR}/ROOT/WEB-INF/lib/hop-ui-rcp"*
rm -f "${WEBAPP_DIR}/ROOT/WEB-INF/lib/org.eclipse.swt."*

# Re-extract hop-ui jars from the war, because the client assembly zip above may
# have overwritten them with an older cached version (the client zip is built from
# .m2 and may lag behind the freshly built war).
unzip -j -o "assemblies/web/target/hop.war" "WEB-INF/lib/hop-ui-*" -d "${WEBAPP_DIR}/ROOT/WEB-INF/lib/" >/dev/null 2>&1
# Remove the RCP jar that was just re-extracted (keep only hop-ui and hop-ui-rap)
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

CATALINA_OPTS="-Xmx2048m"
CATALINA_OPTS="${CATALINA_OPTS} -Duser.timezone=Asia/Shanghai"
CATALINA_OPTS="${CATALINA_OPTS} -DHOP_LOG_LEVEL=Debug"
CATALINA_OPTS="${CATALINA_OPTS} -DHOP_AUDIT_FOLDER=${CATALINA_BASE}/audit"
CATALINA_OPTS="${CATALINA_OPTS} -DHOP_CONFIG_FOLDER=${CATALINA_BASE}/config"
CATALINA_OPTS="${CATALINA_OPTS} -DHOP_PLUGIN_BASE_FOLDERS=${WEBAPP_DIR}/ROOT/WEB-INF/plugins"
CATALINA_OPTS="${CATALINA_OPTS} -DHOP_PASSWORD_ENCODER_PLUGIN=Hop"
CATALINA_OPTS="${CATALINA_OPTS} -DHOP_SHARED_JDBC_FOLDERS=${WEBAPP_DIR}/ROOT/WEB-INF/lib"
CATALINA_OPTS="${CATALINA_OPTS} -Dorg.eclipse.rap.rwt.resourceLocation=${CATALINA_BASE}/rwt-resources"
CATALINA_OPTS="${CATALINA_OPTS} -Dswt.use.gtk3=false"

export CATALINA_HOME
export CATALINA_BASE
export CATALINA_OPTS

mkdir -p "${CATALINA_BASE}/audit"
mkdir -p "${CATALINA_BASE}/config"

echo ""
echo "==> Copying configuration files..."
if [ -f "${HOP_HOME}/resources/disabledGuiElements.xml" ]; then
    cp "${HOP_HOME}/resources/disabledGuiElements.xml" "${CATALINA_BASE}/config/"
    echo "    Copied disabledGuiElements.xml"
else
    echo "    Warning: disabledGuiElements.xml not found"
fi

echo ""
echo "检查 LiteLLM 代理..."
if ! docker ps | grep -q "hop-litellm-dev"; then
    echo "启动 LiteLLM 代理..."
    cd "${HOP_HOME}"
    docker compose -f docker-compose.dev.yml up -d litellm
    sleep 3
    echo "LiteLLM 代理已启动"
else
    echo "LiteLLM 代理已在运行"
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

echo ""
echo "==> Starting Hop Web Server..."
echo "    Web UI:  http://localhost:8080/ui"
echo "    REST:    http://localhost:8080/hop/"
echo "    Logs:    ${LOG_DIR}/catalina.out"
echo ""

"${CATALINA_HOME}/bin/catalina.sh" run
