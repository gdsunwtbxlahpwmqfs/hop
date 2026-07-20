#!/bin/bash
# =============================================================
# Qi Hop 服务启动/停止/状态管理脚本
# 用法：
#   ./hop-start.sh start       # 启动
#   ./hop-start.sh stop        # 停止
#   ./hop-start.sh restart     # 重启
#   ./hop-start.sh status      # 查看状态
#   ./hop-start.sh foreground  # 前台运行（便于调试）
#   ./hop-start.sh log         # 查看实时日志
#   ./hop-start.sh health      # 健康检查（探活 REST API）
#
# 多实例支持：
#   INSTANCE_NAME=bi ./hop-start.sh status
#   或通过 --instance bi 指定（对齐 hop-deploy.sh --instance）
# =============================================================

set -Euo pipefail

# --------------------- 颜色与日志 ---------------------
RED='\033[0;31m'; GREEN='\033[0;32m'; YELLOW='\033[1;33m'; BLUE='\033[0;34m'; NC='\033[0m'
log()  { echo -e "${GREEN}[$(date '+%H:%M:%S')]${NC} $*"; }
warn() { echo -e "${YELLOW}[$(date '+%H:%M:%S')] WARN:${NC} $*"; }
err()  { echo -e "${RED}[$(date '+%H:%M:%S')] ERROR:${NC} $*" >&2; }
info() { echo -e "${BLUE}[$(date '+%H:%M:%S')]${NC} $*"; }

# --------------------- 默认配置（先设默认值，再被参数覆盖）---------------------
# 共享变量一律 ${VAR:-default}，允许 hop-deploy.sh 写入的环境变量透传覆盖
INSTALL_BASE="${INSTALL_BASE:-/opt/qi}"
HOP_USER="${HOP_USER:-qi}"
TOMCAT_PORT="${TOMCAT_PORT:-8080}"
INSTANCE_NAME="${INSTANCE_NAME:-qi-hop-001}"
# SERVICE_NAME 默认留空，由下方派生为 qi-hop-${INSTANCE_NAME}（对齐 hop-deploy.sh 注册的服务名）
SERVICE_NAME="${SERVICE_NAME:-}"
HEALTH_CHECK_PATH="${HEALTH_CHECK_PATH:-/hop/status/}"
HEALTH_TIMEOUT="${HEALTH_TIMEOUT:-120}"   # 启动健康检查超时（秒）
HEALTH_INTERVAL="${HEALTH_INTERVAL:-3}"   # 轮询间隔

# --------------------- 参数解析（提取 --instance/--base/--port，剩余留给子命令）---------------------
CMD=""
while [[ $# -gt 0 ]]; do
    case "$1" in
        --instance) INSTANCE_NAME="$2"; shift 2 ;;
        --base)     INSTALL_BASE="$2"; shift 2 ;;
        --port)     TOMCAT_PORT="$2"; shift 2 ;;
        start|stop|restart|status|foreground|fg|log|logs|health)
            CMD="$1"; shift ;;
        -h|--help)
            sed -n '2,15p' "$0"
            echo
            echo "命令列表: start | stop | restart | status | foreground | log | health"
            exit 0 ;;
        *) err "未知参数: $1"; exit 1 ;;
    esac
done

# 多实例时 service 名带后缀（对齐 hop-deploy.sh 逻辑）
if [ -z "$SERVICE_NAME" ]; then
    if [ -n "$INSTANCE_NAME" ]; then
        SERVICE_NAME="qi-hop-${INSTANCE_NAME}"
    else
        SERVICE_NAME="qi-hop"
    fi
fi

# CATALINA_HOME（Tomcat 软件，只读）/ CATALINA_BASE（运行实例，可写）
# 对齐 hop-deploy.sh 的 tomcat + tomcat-run[-${INSTANCE_NAME}] 分离结构
CATALINA_HOME="${INSTALL_BASE}/tomcat"
if [ -n "$INSTANCE_NAME" ]; then
    CATALINA_BASE="${INSTALL_BASE}/tomcat-run-${INSTANCE_NAME}"
else
    CATALINA_BASE="${INSTALL_BASE}/tomcat-run"
fi
JAVA_HOME="${INSTALL_BASE}/jdk21"
LOG_FILE="${CATALINA_BASE}/logs/catalina.out"
PID_FILE="${CATALINA_BASE}/temp/tomcat.pid"

# WEBAPP_DIR 统一指向 webapps/ROOT（对齐 hop-deploy.sh / hop-verify.sh）
WEBAPP_DIR="${CATALINA_BASE}/webapps/ROOT"

# --------------------- Hop 环境变量（对齐 hop-deploy.sh：数据目录全部位于 CATALINA_BASE 下）---------------------
# 注意：插件/JDBC 目录必须用 CATALINA_BASE 下的真实部署路径，而非 WEB-INF 内部路径
export HOP_AUDIT_FOLDER="${HOP_AUDIT_FOLDER:-${CATALINA_BASE}/audit}"
export HOP_CONFIG_FOLDER="${HOP_CONFIG_FOLDER:-${CATALINA_BASE}/config}"
export HOP_TEMP_FOLDER="${HOP_TEMP_FOLDER:-${CATALINA_BASE}/temp}"
export HOP_LOG_LEVEL="${HOP_LOG_LEVEL:-Basic}"
export HOP_PASSWORD_ENCODER_PLUGIN="${HOP_PASSWORD_ENCODER_PLUGIN:-Hop}"
export HOP_PLUGIN_BASE_FOLDERS="${HOP_PLUGIN_BASE_FOLDERS:-${CATALINA_BASE}/plugins}"
export HOP_SHARED_JDBC_FOLDERS="${HOP_SHARED_JDBC_FOLDERS:-${CATALINA_BASE}/jdbc-drivers}"
export HOP_AES_ENCODER_KEY="${HOP_AES_ENCODER_KEY:-}"
export HOP_GUI_ZOOM_FACTOR="${HOP_GUI_ZOOM_FACTOR:-1.0}"
export HOP_REST_CONFIG_FOLDER="${HOP_REST_CONFIG_FOLDER:-${CATALINA_BASE}/config}"

# 项目与环境变量：默认为空（对齐 Docker 语义：空=不创建/注册项目）
export HOP_PROJECT_FOLDER="${HOP_PROJECT_FOLDER:-}"
export HOP_PROJECT_NAME="${HOP_PROJECT_NAME:-}"
export HOP_PROJECT_CONFIG_FILE_NAME="${HOP_PROJECT_CONFIG_FILE_NAME:-project-config.json}"
export HOP_ENVIRONMENT_NAME="${HOP_ENVIRONMENT_NAME:-}"
export HOP_ENVIRONMENT_CONFIG_FILE_NAME_PATHS="${HOP_ENVIRONMENT_CONFIG_FILE_NAME_PATHS:-}"

# 是否使用 systemd（root 权限且服务存在时优先）
USE_SYSTEMD=false
if [ "$(id -u)" -eq 0 ] && systemctl list-unit-files 2>/dev/null | grep -q "^${SERVICE_NAME}\.service"; then
    USE_SYSTEMD=true
fi

# --------------------- 环境变量构建 ---------------------
build_catalina_opts() {
    local opts=""

    opts="${opts} -Xmx4096m"
    opts="${opts} -Xms1024m"
    opts="${opts} -XX:+UseZGC -XX:+ZGenerational"
    opts="${opts} -XX:MaxGCPauseMillis=100"
    opts="${opts} -XX:+AlwaysPreTouch"
    opts="${opts} -Djdk.attach.allowAttachSelf=true"
    opts="${opts} -XX:+HeapDumpOnOutOfMemoryError"
    opts="${opts} -XX:HeapDumpPath=${CATALINA_BASE}/logs/"

    opts="${opts} -Duser.timezone=Asia/Shanghai -Dfile.encoding=UTF-8"
    opts="${opts} -DHOP_AUDIT_FOLDER=${HOP_AUDIT_FOLDER}"
    opts="${opts} -DHOP_CONFIG_FOLDER=${HOP_CONFIG_FOLDER}"
    opts="${opts} -DHOP_TEMP_FOLDER=${HOP_TEMP_FOLDER}"
    opts="${opts} -DHOP_PLUGIN_BASE_FOLDERS=${HOP_PLUGIN_BASE_FOLDERS}"
    opts="${opts} -DHOP_SHARED_JDBC_FOLDERS=${HOP_SHARED_JDBC_FOLDERS}"
    opts="${opts} -DHOP_AES_ENCODER_KEY=${HOP_AES_ENCODER_KEY}"
    opts="${opts} -DHOP_GUI_ZOOM_FACTOR=${HOP_GUI_ZOOM_FACTOR}"
    opts="${opts} -DHOP_REST_CONFIG_FOLDER=${HOP_REST_CONFIG_FOLDER}"
    opts="${opts} -Dorg.eclipse.rap.rwt.resourceLocation=${CATALINA_BASE}/rwt-resources"
    opts="${opts} -Dswt.use.gtk3=false"

    opts="${opts} -DHOP_PASSWORD_ENCODER_PLUGIN=${HOP_PASSWORD_ENCODER_PLUGIN}"
    opts="${opts} -DHOP_HIDE_DATABASE_PASSWORDS_IN_LOGS=Y"
    opts="${opts} -DHOP_DISABLE_PLAIN_TEXT_CREDENTIALS=Y"
    opts="${opts} -DHOP_MASK_SENSITIVE_OUTPUT=Y"
    opts="${opts} -DHOP_DISABLE_SENSITIVE_DATA_LOGGING=Y"

    opts="${opts} -DHOP_LOG_LEVEL=${HOP_LOG_LEVEL}"
    opts="${opts} -DHOP_LOG_FILE_MAX_SIZE_MB=500"
    opts="${opts} -DHOP_LOG_FILE_ROTATION_COUNT=10"
    opts="${opts} -DHOP_LOG_BUFFER_LINES=5000"

    opts="${opts} -DHOP_ROWSET_DEFAULT_SIZE=10000"
    opts="${opts} -DHOP_DEFAULT_SORT_SIZE=500000"
    opts="${opts} -DHOP_MEMORY_FREE_THRESHOLD_PERCENT=15"
    opts="${opts} -DHOP_AUTO_CLEAN_TEMP_SORT_FILES=Y"
    opts="${opts} -DHOP_CLEANUP_TEMP_FILES_ON_EXIT=Y"
    opts="${opts} -DHOP_MAX_CONCURRENT_BACKGROUND_TASKS=4"
    opts="${opts} -DHOP_USE_VIRTUAL_THREADS=Y"
    opts="${opts} -DHOP_METRIC_FLUSH_INTERVAL_MS=30000"
    opts="${opts} -DHOP_PREVIEW_BATCH_SIZE=100"

    opts="${opts} --add-opens=java.xml/com.sun.org.apache.xalan.internal.xsltc.trax=ALL-UNNAMED"
    opts="${opts} --add-opens=java.xml/jdk.xml.internal=ALL-UNNAMED"
    opts="${opts} --add-opens=java.base/java.lang=ALL-UNNAMED"
    opts="${opts} --add-opens=java.base/java.lang.invoke=ALL-UNNAMED"
    opts="${opts} --add-opens=java.base/java.lang.reflect=ALL-UNNAMED"
    opts="${opts} --add-opens=java.base/java.io=ALL-UNNAMED"
    opts="${opts} --add-opens=java.base/java.net=ALL-UNNAMED"
    opts="${opts} --add-opens=java.base/java.nio=ALL-UNNAMED"
    opts="${opts} --add-opens=java.base/java.util=ALL-UNNAMED"
    opts="${opts} --add-opens=java.base/java.util.concurrent=ALL-UNNAMED"
    opts="${opts} --add-opens=java.base/java.util.concurrent.atomic=ALL-UNNAMED"
    opts="${opts} --add-opens=java.base/sun.nio.ch=ALL-UNNAMED"
    opts="${opts} --add-opens=java.base/sun.nio.cs=ALL-UNNAMED"
    opts="${opts} --add-opens=java.base/sun.security.action=ALL-UNNAMED"
    opts="${opts} --add-opens=java.base/sun.util.calendar=ALL-UNNAMED"
    opts="${opts} --add-opens=java.security.jgss/sun.security.krb5=ALL-UNNAMED"
    opts="${opts} --add-exports=java.base/sun.nio.ch=ALL-UNNAMED"

    opts="${opts} -Djava.security.egd=file:/dev/./urandom"

    echo "${opts}"
}

start_litellm() {
    if [ "${HOP_DOCKER_ENABLED:-true}" != "true" ]; then
        return
    fi

    if ! command -v docker &>/dev/null; then
        warn "Docker 未安装，跳过 LiteLLM 启动"
        return
    fi

    if ! docker ps &>/dev/null; then
        warn "Docker 未运行，跳过 LiteLLM 启动"
        return
    fi

    if docker ps | grep -q "hop-litellm-dev"; then
        log "LiteLLM 代理已在运行"
        return
    fi

    log "启动 LiteLLM 代理..."
    local compose_file="${INSTALL_BASE}/docker-compose.dev.yml"
    if [ -f "$compose_file" ]; then
        docker compose -f "$compose_file" up -d litellm
        sleep 3
        log "LiteLLM 代理已启动"

        export HOP_LLM_ENABLED=true
        export HOP_LLM_API_URL=http://localhost:4000/v1
        export HOP_LLM_API_KEY=sk-hop-litellm-dev
        export HOP_LLM_MODEL=qwen-plus
        log "LLM 环境变量已设置: HOP_LLM_ENABLED=${HOP_LLM_ENABLED}, HOP_LLM_API_URL=${HOP_LLM_API_URL}, HOP_LLM_MODEL=${HOP_LLM_MODEL}"
    else
        warn "未找到 docker-compose.dev.yml，跳过 LiteLLM 启动"
    fi
}

# --------------------- 工具函数 ---------------------
running_pid() {
    # 优先使用 PID 文件，回退到 pgrep
    if [ -f "$PID_FILE" ]; then
        local p
        p=$(cat "$PID_FILE" 2>/dev/null || true)
        if [ -n "$p" ] && kill -0 "$p" 2>/dev/null; then
            echo "$p"; return 0
        fi
    fi
    # 多实例共享 CATALINA_HOME，必须用 catalina.base=<CATALINA_BASE> 区分
    local p
    p=$(pgrep -f "catalina.base=${CATALINA_BASE}" 2>/dev/null | head -1 || true)
    [ -n "$p" ] && { echo "$p"; return 0; }
    return 1
}

is_running() {
    running_pid >/dev/null 2>&1
}

check_port() {
    ss -tlnp 2>/dev/null | grep -q ":${TOMCAT_PORT} "
}

wait_for_health() {
    local elapsed=0
    log "等待服务就绪（最长 ${HEALTH_TIMEOUT}s）..."
    while [ "$elapsed" -lt "$HEALTH_TIMEOUT" ]; do
        if curl -fsS -o /dev/null "http://localhost:${TOMCAT_PORT}${HEALTH_CHECK_PATH}" 2>/dev/null; then
            log "服务已就绪（耗时 ${elapsed}s）"
            return 0
        fi
        sleep "$HEALTH_INTERVAL"
        elapsed=$((elapsed + HEALTH_INTERVAL))
        printf "."
    done
    echo
    warn "健康检查超时（${HEALTH_TIMEOUT}s 未响应）"
    return 1
}

# --------------------- 命令实现 ---------------------
do_start() {
    if is_running; then
        warn "服务已在运行（PID: $(running_pid)）"
        exit 0
    fi
    log "启动 Qi Hop 服务..."
    log "使用 systemd: ${USE_SYSTEMD}"

    start_litellm

    if [ "$USE_SYSTEMD" = "true" ]; then
        systemctl start "$SERVICE_NAME"
    else
        local catalina_opts=$(build_catalina_opts)
        if [ "$(id -u)" -eq 0 ]; then
            su - "$HOP_USER" -c "
                export JAVA_HOME='${JAVA_HOME}'
                export CATALINA_HOME='${CATALINA_HOME}'
                export CATALINA_BASE='${CATALINA_BASE}'
                export CATALINA_OPTS='${catalina_opts}'
                export HOP_AUDIT_FOLDER='${HOP_AUDIT_FOLDER}'
                export HOP_CONFIG_FOLDER='${HOP_CONFIG_FOLDER}'
                export HOP_TEMP_FOLDER='${HOP_TEMP_FOLDER}'
                export HOP_LOG_LEVEL='${HOP_LOG_LEVEL}'
                export HOP_PASSWORD_ENCODER_PLUGIN='${HOP_PASSWORD_ENCODER_PLUGIN}'
                export HOP_PLUGIN_BASE_FOLDERS='${HOP_PLUGIN_BASE_FOLDERS}'
                export HOP_SHARED_JDBC_FOLDERS='${HOP_SHARED_JDBC_FOLDERS}'
                export HOP_AES_ENCODER_KEY='${HOP_AES_ENCODER_KEY}'
                export HOP_GUI_ZOOM_FACTOR='${HOP_GUI_ZOOM_FACTOR}'
                export HOP_REST_CONFIG_FOLDER='${HOP_REST_CONFIG_FOLDER}'
                export HOP_LLM_ENABLED='${HOP_LLM_ENABLED:-false}'
                export HOP_LLM_API_URL='${HOP_LLM_API_URL:-}'
                export HOP_LLM_API_KEY='${HOP_LLM_API_KEY:-}'
                export HOP_LLM_MODEL='${HOP_LLM_MODEL:-}'
                '${CATALINA_HOME}/bin/startup.sh'
            "
        else
            export JAVA_HOME="$JAVA_HOME"
            export CATALINA_HOME="$CATALINA_HOME"
            export CATALINA_BASE="$CATALINA_BASE"
            export CATALINA_OPTS=$(build_catalina_opts)
            "${CATALINA_HOME}/bin/startup.sh"
        fi
    fi

    if wait_for_health; then
        log "服务启动成功"
        log "Web UI:  http://localhost:${TOMCAT_PORT}/ui"
        log "REST:    http://localhost:${TOMCAT_PORT}/api/v1/"
        log "Docs:    http://localhost:${TOMCAT_PORT}/api/v1/docs/stats"
        do_status
    else
        err "服务启动异常，请查看日志: tail -f $LOG_FILE"
        exit 1
    fi
}

do_stop() {
    if ! is_running; then
        warn "服务未运行"
        # 清理残留 PID 文件
        rm -f "$PID_FILE" 2>/dev/null || true
        exit 0
    fi
    local pid
    pid=$(running_pid)
    log "停止 Qi Hop 服务（PID: ${pid}）..."

    if [ "$USE_SYSTEMD" = "true" ]; then
        systemctl stop "$SERVICE_NAME"
    else
        # shutdown.sh 需要 CATALINA_BASE 定位实例
        if [ "$(id -u)" -eq 0 ]; then
            su - "$HOP_USER" -c "export JAVA_HOME='${JAVA_HOME}'; export CATALINA_HOME='${CATALINA_HOME}'; export CATALINA_BASE='${CATALINA_BASE}'; '${CATALINA_HOME}/bin/shutdown.sh'" 2>/dev/null || true
        else
            export JAVA_HOME="$JAVA_HOME"
            export CATALINA_HOME="$CATALINA_HOME"
            export CATALINA_BASE="$CATALINA_BASE"
            "${CATALINA_HOME}/bin/shutdown.sh" 2>/dev/null || true
        fi
    fi

    # 等待优雅退出
    local count=0
    while kill -0 "$pid" 2>/dev/null && [ "$count" -lt 30 ]; do
        sleep 1; count=$((count + 1))
    done

    # 仍未退出则强制
    if kill -0 "$pid" 2>/dev/null; then
        warn "优雅退出超时，强制终止 PID: ${pid}"
        kill -9 "$pid" 2>/dev/null || true
        # 同时清理该实例的所有子进程（用 catalina.base 精确匹配，避免误杀其他实例）
        pkill -9 -f "catalina.base=${CATALINA_BASE}" 2>/dev/null || true
    fi

    rm -f "$PID_FILE" 2>/dev/null || true
    log "服务已停止"
}

do_restart() {
    log "重启 Qi Hop 服务..."
    do_stop
    sleep 2
    do_start
}

do_status() {
    echo
    info "================== 服务状态 =================="
    if is_running; then
        local pid
        pid=$(running_pid)
        log " 进程状态   : ${GREEN}RUNNING${NC} (PID: ${pid})"
    else
        err " 进程状态   : STOPPED"
    fi

    if check_port; then
        log " 端口监听   : ${GREEN}${TOMCAT_PORT}${NC} ✓"
    else
        warn " 端口监听   : ${TOMCAT_PORT} 未监听"
    fi

    if curl -fsS -o /dev/null "http://localhost:${TOMCAT_PORT}${HEALTH_CHECK_PATH}" 2>/dev/null; then
        log " REST 接口  : ${GREEN}OK${NC}"
    else
        warn " REST 接口  : 不可达"
    fi

    info " 日志文件   : ${LOG_FILE}"
    info " PID 文件   : ${PID_FILE}"
    info " 安装目录   : ${INSTALL_BASE}"
    info " CATALINA_BASE: ${CATALINA_BASE}"
    info " 实例名     : ${INSTANCE_NAME:-default}"
    info "=============================================="
    echo
}

do_foreground() {
    log "前台运行（输出到终端，Ctrl+C 停止）..."
    start_litellm
    if [ "$(id -u)" -eq 0 ]; then
        su - "$HOP_USER" -c "
            export JAVA_HOME='${JAVA_HOME}'
            export CATALINA_HOME='${CATALINA_HOME}'
            export CATALINA_BASE='${CATALINA_BASE}'
            export CATALINA_OPTS='$(build_catalina_opts)'
            export HOP_AUDIT_FOLDER='${HOP_AUDIT_FOLDER}'
            export HOP_CONFIG_FOLDER='${HOP_CONFIG_FOLDER}'
            export HOP_TEMP_FOLDER='${HOP_TEMP_FOLDER}'
            export HOP_LOG_LEVEL='${HOP_LOG_LEVEL}'
            export HOP_PASSWORD_ENCODER_PLUGIN='${HOP_PASSWORD_ENCODER_PLUGIN}'
            export HOP_PLUGIN_BASE_FOLDERS='${HOP_PLUGIN_BASE_FOLDERS}'
            export HOP_SHARED_JDBC_FOLDERS='${HOP_SHARED_JDBC_FOLDERS}'
            export HOP_AES_ENCODER_KEY='${HOP_AES_ENCODER_KEY}'
            export HOP_GUI_ZOOM_FACTOR='${HOP_GUI_ZOOM_FACTOR}'
            export HOP_REST_CONFIG_FOLDER='${HOP_REST_CONFIG_FOLDER}'
            export HOP_LLM_ENABLED='${HOP_LLM_ENABLED:-false}'
            export HOP_LLM_API_URL='${HOP_LLM_API_URL:-}'
            export HOP_LLM_API_KEY='${HOP_LLM_API_KEY:-}'
            export HOP_LLM_MODEL='${HOP_LLM_MODEL:-}'
            '${CATALINA_HOME}/bin/catalina.sh' run
        "
    else
        export JAVA_HOME="$JAVA_HOME"
        export CATALINA_HOME="$CATALINA_HOME"
        export CATALINA_BASE="$CATALINA_BASE"
        export CATALINA_OPTS=$(build_catalina_opts)
        "${CATALINA_HOME}/bin/catalina.sh" run
    fi
}

do_log() {
    if [ ! -f "$LOG_FILE" ]; then
        warn "日志文件不存在: $LOG_FILE"
        exit 1
    fi
    log "实时日志（Ctrl+C 退出）..."
    tail -f "$LOG_FILE"
}

do_health() {
    local start_ts end_ts elapsed
    start_ts=$(date +%s%3N 2>/dev/null || date +%s)
    if curl -fsS "http://localhost:${TOMCAT_PORT}${HEALTH_CHECK_PATH}" >/dev/null 2>&1; then
        end_ts=$(date +%s%3N 2>/dev/null || date +%s)
        elapsed=$((end_ts - start_ts))
        log "健康检查: ${GREEN}PASS${NC} (响应耗时 ${elapsed}ms)"
        exit 0
    else
        err "健康检查: FAIL"
        exit 1
    fi
}

# --------------------- 主入口 ---------------------
case "${CMD:-}" in
    start)      do_start ;;
    stop)       do_stop ;;
    restart)    do_restart ;;
    status)     do_status ;;
    foreground) do_foreground ;;
    fg)         do_foreground ;;
    log)        do_log ;;
    logs)       do_log ;;
    health)     do_health ;;
    "")
        sed -n '2,16p' "$0"
        echo
        echo "命令列表: start | stop | restart | status | foreground | log | health"
        echo "示例: $0 start | $0 --instance bi status"
        ;;
    *) err "未知命令: ${CMD}"; exit 1 ;;
esac
