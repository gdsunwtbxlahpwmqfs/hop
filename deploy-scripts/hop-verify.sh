#!/bin/bash
# =============================================================
# Qi Hop 部署验证脚本
# 功能：环境/功能/性能/日志/插件/安全 多维度验证
# 用法：
#   ./hop-verify.sh                    # 全量验证
#   ./hop-verify.sh --section env      # 仅环境检查
#   ./hop-verify.sh --section func     # 仅功能验证
#   ./hop-verify.sh --section perf     # 仅性能测试
#   ./hop-verify.sh --section log      # 仅日志检查
#   ./hop-verify.sh --section plugin   # 仅插件加载
#   ./hop-verify.sh --section security # 仅安全检查
# =============================================================

set -Euo pipefail

# --------------------- 颜色与日志 ---------------------
RED='\033[0;31m'; GREEN='\033[0;32m'; YELLOW='\033[1;33m'; BLUE='\033[0;34m'; NC='\033[0m'
log()  { echo -e "${GREEN}[$(date '+%H:%M:%S')]${NC} $*"; }
warn() { echo -e "${YELLOW}[$(date '+%H:%M:%S')] WARN:${NC} $*"; }
err()  { echo -e "${RED}[$(date '+%H:%M:%S')] ERROR:${NC} $*" >&2; }
info() { echo -e "${BLUE}[$(date '+%H:%M:%S')]${NC} $*"; }

# 计数器
PASS=0; FAIL=0; WARN=0

pass() { echo -e "  ${GREEN}✓ PASS${NC}  $1"; PASS=$((PASS+1)); }
fail() { echo -e "  ${RED}✗ FAIL${NC}  $1"; FAIL=$((FAIL+1)); }
warn_() { echo -e "  ${YELLOW}! WARN${NC}  $1"; WARN=$((WARN+1)); }

# --------------------- 默认配置 ---------------------
INSTALL_BASE="${INSTALL_BASE:-/opt/hop}"
INSTANCE_NAME="${INSTANCE_NAME:-}"
TOMCAT_PORT="${TOMCAT_PORT:-8080}"
HEALTH_CHECK_PATH="${HEALTH_CHECK_PATH:-/hop/status/}"
SECTION="all"

while [[ $# -gt 0 ]]; do
    case "$1" in
        --section) SECTION="$2"; shift 2 ;;
        --base) INSTALL_BASE="$2"; shift 2 ;;
        --port) TOMCAT_PORT="$2"; shift 2 ;;
        --instance) INSTANCE_NAME="$2"; shift 2 ;;
        -h|--help) sed -n '2,13p' "$0"; exit 0 ;;
        *) err "未知参数: $1"; exit 1 ;;
    esac
done

# CATALINA_HOME（Tomcat 软件）/ CATALINA_BASE（运行实例）分离，对齐 hop-deploy.sh
JAVA_HOME="${INSTALL_BASE}/jdk21"
CATALINA_HOME="${INSTALL_BASE}/tomcat"
if [ -n "$INSTANCE_NAME" ]; then
    CATALINA_BASE="${INSTALL_BASE}/tomcat-run-${INSTANCE_NAME}"
else
    CATALINA_BASE="${INSTALL_BASE}/tomcat-run"
fi
WEBAPP_DIR="${CATALINA_BASE}/webapps/ROOT"
LOG_FILE="${CATALINA_BASE}/logs/catalina.out"
PLUGINS_DIR="${CATALINA_BASE}/plugins"
JDBC_DIR="${CATALINA_BASE}/jdbc-drivers"
SETENV_FILE="${CATALINA_BASE}/bin/setenv.sh"
CONF_DIR="${CATALINA_BASE}/conf"

# --------------------- 1. 环境检查 ---------------------
verify_env() {
    echo
    info "=================== 1. 环境检查 ==================="

    # OS
    if [ -f /etc/os-release ]; then
        . /etc/os-release
        info "操作系统: ${PRETTY_NAME:-unknown}"
        case "${ID:-}" in
            centos|rhel|ubuntu|debian|rocky|almalinux|fedora) pass "受支持的 Linux 发行版" ;;
            *) warn_ "未在官方支持列表的发行版（可能仍可用）" ;;
        esac
    else
        warn_ "无法读取 /etc/os-release"
    fi

    # 架构
    local arch
    arch=$(uname -m)
    case "$arch" in
        x86_64)     pass "CPU 架构: x86_64" ;;
        aarch64)    pass "CPU 架构: aarch64" ;;
        *) fail "不支持的 CPU 架构: $arch" ;;
    esac

    # 内存
    local mem_total_mb
    mem_total_mb=$(awk '/MemTotal/ {printf "%d", $2/1024}' /proc/meminfo 2>/dev/null || echo 0)
    if [ "$mem_total_mb" -ge 4096 ]; then
        pass "物理内存: ${mem_total_mb} MB (≥ 4096)"
    else
        warn_ "物理内存较低: ${mem_total_mb} MB（建议 ≥ 4096 MB）"
    fi

    # 磁盘
    local avail_gb
    avail_gb=$(df -BG "${INSTALL_BASE}" 2>/dev/null | awk 'NR==2 {gsub("G","",$4); print $4}')
    if [ -n "${avail_gb:-}" ] && [ "$avail_gb" -ge 5 ]; then
        pass "可用磁盘: ${avail_gb} GB (≥ 5)"
    else
        warn_ "可用磁盘较低（建议 ≥ 5GB）"
    fi

    # JDK
    if "$JAVA_HOME/bin/java" -version 2>&1 | grep -q "version \"21"; then
        pass "JDK 21 已正确安装"
        "$JAVA_HOME/bin/java" -version 2>&1 | sed 's/^/    /'
    else
        fail "JDK 21 未正确安装或路径错误: $JAVA_HOME"
    fi

    # Tomcat
    if [ -x "${CATALINA_HOME}/bin/catalina.sh" ]; then
        pass "Tomcat 安装目录有效: ${CATALINA_HOME}"
    else
        fail "Tomcat 未正确安装: ${CATALINA_HOME}"
    fi

    # 关键命令
    for c in tar unzip curl ss; do
        command -v "$c" >/dev/null 2>&1 && pass "命令可用: $c" || fail "缺少命令: $c"
    done
}

# --------------------- 2. 功能验证 ---------------------
verify_func() {
    echo
    info "=================== 2. 功能验证 ==================="
    local base_url="http://localhost:${TOMCAT_PORT}"

    # 进程（多实例用 catalina.base 精确匹配）
    if pgrep -f "catalina.base=${CATALINA_BASE}" >/dev/null 2>&1; then
        pass "Tomcat 进程正在运行"
    else
        fail "Tomcat 进程未运行"; return
    fi

    # 端口
    if ss -tlnp 2>/dev/null | grep -q ":${TOMCAT_PORT} "; then
        pass "端口 ${TOMCAT_PORT} 正在监听"
    else
        fail "端口 ${TOMCAT_PORT} 未监听"; return
    fi

    # Hop Server 状态接口
    local resp
    if resp=$(curl -fsS --max-time 10 "${base_url}${HEALTH_CHECK_PATH}" 2>/dev/null); then
        pass "Hop Server status 接口可访问"
        echo "$resp" | head -c 200 | sed 's/^/    /'
        echo
    else
        fail "Hop Server status 接口不可达"
    fi

    # Web UI（HEAD 请求）
    local code
    code=$(curl -s -o /dev/null -w "%{http_code}" --max-time 10 "${base_url}/ui" 2>/dev/null || echo "000")
    case "$code" in
        200|301|302) pass "Web UI 可访问 (HTTP ${code})" ;;
        401)         pass "Web UI 启用了认证 (HTTP 401)" ;;
        *)           fail "Web UI 异常 (HTTP ${code})" ;;
    esac

    # REST API 根路径
    code=$(curl -s -o /dev/null -w "%{http_code}" --max-time 10 "${base_url}/hop/" 2>/dev/null || echo "000")
    case "$code" in
        200|301|302|405) pass "REST API /hop/ 可访问 (HTTP ${code})" ;;
        *)               warn_ "REST API /hop/ 返回 ${code}" ;;
    esac

    # Docs 接口（如果部署）
    code=$(curl -s -o /dev/null -w "%{http_code}" --max-time 10 "${base_url}/api/v1/docs/stats" 2>/dev/null || echo "000")
    case "$code" in
        200) pass "Docs 服务可用 (/api/v1/docs/stats)" ;;
        404) warn_ "Docs 服务未部署（可选）" ;;
        *)   warn_ "Docs 服务返回 ${code}" ;;
    esac
}

# --------------------- 3. 性能测试 ---------------------
verify_perf() {
    echo
    info "=================== 3. 性能测试 ==================="
    local base_url="http://localhost:${TOMCAT_PORT}"

    # 响应时间（10 次平均）
    local total=0 count=10 i ts te elapsed
    for i in $(seq 1 $count); do
        ts=$(date +%s%3N 2>/dev/null || python3 -c 'import time;print(int(time.time()*1000))')
        curl -fsS -o /dev/null --max-time 5 "${base_url}${HEALTH_CHECK_PATH}" 2>/dev/null || {
            fail "请求失败（第 ${i} 次）"; return
        }
        te=$(date +%s%3N 2>/dev/null || python3 -c 'import time;print(int(time.time()*1000))')
        elapsed=$((te - ts))
        total=$((total + elapsed))
    done
    local avg=$((total / count))
    if [ "$avg" -lt 200 ]; then
        pass "平均响应时间: ${avg}ms (< 200ms 优)"
    elif [ "$avg" -lt 500 ]; then
        warn_ "平均响应时间: ${avg}ms (< 500ms 良)"
    else
        fail "平均响应时间: ${avg}ms (≥ 500ms 差)"
    fi

    # 并发测试（20 并发，各 5 次）
    if command -v ab >/dev/null 2>&1; then
        ab -n 100 -c 20 -q "${base_url}${HEALTH_CHECK_PATH}" 2>/dev/null | \
            awk '/Requests per second/ {printf "    并发吞吐: %s req/s\n", $4}
                 /Time per request/ && /mean/ {printf "    平均 RT  : %s ms (并发)\n", $4}' || true
        pass "并发测试完成（ab）"
    else
        warn_ "未安装 ab（apache2-utils），跳过并发测试"
    fi

    # JVM 内存占用（用 catalina.base 精确匹配实例）
    local pid
    pid=$(pgrep -f "catalina.base=${CATALINA_BASE}" | head -1 || true)
    if [ -n "$pid" ]; then
        local rss_kb
        rss_kb=$(awk '/VmRSS/ {print $2}' /proc/$pid/status 2>/dev/null || echo 0)
        if [ "$rss_kb" -gt 0 ]; then
            local rss_mb=$((rss_kb / 1024))
            info "JVM RSS 内存: ${rss_mb} MB"
            if [ "$rss_mb" -lt 8192 ]; then
                pass "JVM 内存占用合理 (≤ 8 GB)"
            else
                warn_ "JVM 内存占用较高 (> 8 GB)，请检查堆配置"
            fi
        fi
    fi

    # 文件描述符
    if [ -n "${pid:-}" ] && [ -d "/proc/$pid/fd" ]; then
        local fd_count
        fd_count=$(ls "/proc/$pid/fd" 2>/dev/null | wc -l | tr -d ' ')
        info "文件描述符使用: ${fd_count}"
        [ "$fd_count" -lt 1000 ] && pass "文件描述符使用正常" || warn_ "文件描述符较高 (${fd_count})"
    fi
}

# --------------------- 4. 日志检查 ---------------------
verify_log() {
    echo
    info "=================== 4. 日志检查 ==================="

    if [ ! -f "$LOG_FILE" ]; then
        fail "日志文件不存在: $LOG_FILE"; return
    fi

    local size_mb
    size_mb=$(du -m "$LOG_FILE" 2>/dev/null | cut -f1)
    info "日志文件大小: ${size_mb} MB"

    # 关键启动标志
    if grep -q "Server startup in" "$LOG_FILE" 2>/dev/null; then
        pass "检测到 Tomcat 启动成功标志"
    else
        fail "未检测到 Tomcat 启动成功标志"
    fi

    if grep -q "Projects enabled" "$LOG_FILE" 2>/dev/null; then
        pass "Hop 项目系统已初始化"
    else
        warn_ "未检测到 Hop 项目初始化日志"
    fi

    # 错误统计
    local err_count
    err_count=$(grep -c -i -E "ERROR|Exception" "$LOG_FILE" 2>/dev/null || echo 0)
    if [ "$err_count" -lt 10 ]; then
        pass "错误日志数量正常 (${err_count})"
    elif [ "$err_count" -lt 100 ]; then
        warn_ "存在一定数量的错误日志 (${err_count})"
    else
        fail "错误日志过多 (${err_count})"
    fi

    # 关键异常模式
    if grep -q "OutOfMemoryError" "$LOG_FILE" 2>/dev/null; then
        fail "检测到 OutOfMemoryError（请增大 -Xmx）"
    else
        pass "无 OutOfMemoryError"
    fi

    if grep -q "NoClassDefFoundError" "$LOG_FILE" 2>/dev/null; then
        fail "检测到 NoClassDefFoundError（核心库可能缺失）"
    else
        pass "无 NoClassDefFoundError"
    fi

    # 最近 5 条 WARN/ERROR
    info "最近 5 条 WARN/ERROR 日志："
    grep -i -E "ERROR|Exception|WARN" "$LOG_FILE" 2>/dev/null | tail -5 | sed 's/^/    /' || true
}

# --------------------- 5. 插件加载检查 ---------------------
verify_plugin() {
    echo
    info "=================== 5. 插件加载检查 ==================="
    local plugins_dir="$PLUGINS_DIR"
    local jdbc_dir="$JDBC_DIR"

    if [ ! -d "$plugins_dir" ]; then
        fail "插件目录不存在: $plugins_dir"; return
    fi

    local plugin_count
    plugin_count=$(find "$plugins_dir" -maxdepth 2 -name "*.zip" 2>/dev/null | wc -l | tr -d ' ')
    info "插件包数量: ${plugin_count}"
    if [ "$plugin_count" -ge 100 ]; then
        pass "插件数量充足 (≥ 100)"
    elif [ "$plugin_count" -ge 50 ]; then
        warn_ "插件数量一般 (${plugin_count})"
    else
        fail "插件数量过少 (${plugin_count})"
    fi

    # 关键插件类别
    for cat in transforms actions databases; do
        local cnt
        cnt=$(find "${plugins_dir}/${cat}" -maxdepth 1 -type d 2>/dev/null | wc -l | tr -d ' ')
        if [ "$cnt" -gt 1 ]; then
            pass "${cat} 插件类别存在 ($((cnt - 1)) 个)"
        else
            warn_ "${cat} 插件类别缺失或为空"
        fi
    done

    # JDBC 驱动
    if [ -d "$jdbc_dir" ]; then
        local jdbc_count
        jdbc_count=$(find "$jdbc_dir" -name "*.jar" 2>/dev/null | wc -l | tr -d ' ')
        info "JDBC 驱动数量: ${jdbc_count}"
        [ "$jdbc_count" -ge 1 ] && pass "JDBC 驱动已部署" || warn_ "未发现 JDBC 驱动"
    fi

    # 日志中的插件加载记录
    if [ -f "$LOG_FILE" ]; then
        local loaded
        loaded=$(grep -c -i "plugin" "$LOG_FILE" 2>/dev/null || echo 0)
        info "日志中插件相关记录: ${loaded} 条"
        [ "$loaded" -gt 0 ] && pass "插件已加载" || warn_ "未检测到插件加载记录"
    fi
}

# --------------------- 6. 安全检查 ---------------------
verify_security() {
    echo
    info "=================== 6. 安全检查 ==================="

    # 运行用户（用 catalina.base 精确匹配实例）
    local pid
    pid=$(pgrep -f "catalina.base=${CATALINA_BASE}" | head -1 || true)
    if [ -n "$pid" ]; then
        local run_user
        run_user=$(ps -o user= -p "$pid" 2>/dev/null | tr -d ' ')
        if [ "$run_user" = "root" ]; then
            fail "Tomcat 以 root 用户运行（存在安全风险）"
        else
            pass "Tomcat 以非 root 用户运行 (${run_user})"
        fi
    fi

    # 防火墙
    if command -v firewall-cmd >/dev/null 2>&1; then
        if firewall-cmd --list-ports 2>/dev/null | grep -q "${TOMCAT_PORT}"; then
            pass "防火墙已放行端口 ${TOMCAT_PORT} (firewalld)"
        else
            warn_ "防火墙未放行端口 ${TOMCAT_PORT}"
        fi
    fi

    # 关键文件权限（setenv.sh 在 CATALINA_BASE/bin/）
    if [ -f "$SETENV_FILE" ]; then
        local perm
        perm=$(stat -c "%a" "$SETENV_FILE" 2>/dev/null || stat -f "%Lp" "$SETENV_FILE" 2>/dev/null)
        if [ "$perm" = "750" ] || [ "$perm" = "755" ] || [ "$perm" = "700" ]; then
            pass "setenv.sh 权限合理 (${perm})"
        else
            warn_ "setenv.sh 权限: ${perm}（建议 750）"
        fi
    else
        warn_ "setenv.sh 不存在: ${SETENV_FILE}"
    fi

    # 默认 webapp 是否清理（webapps 在 CATALINA_BASE 下）
    local default_apps
    default_apps=$(find "${CATALINA_BASE}/webapps" -maxdepth 1 -type d ! -name webapps ! -name ROOT 2>/dev/null | wc -l | tr -d ' ')
    if [ "$default_apps" -eq 0 ]; then
        pass "Tomcat 默认 webapp 已清理"
    else
        warn_ "Tomcat 仍存在 ${default_apps} 个默认 webapp（建议清理）"
    fi

    # 认证配置（conf 在 CATALINA_BASE 下，可选）
    if [ -f "${CONF_DIR}/tomcat-users.xml" ]; then
        pass "已配置 Tomcat 用户认证"
    else
        warn_ "未配置 Tomcat 用户认证（生产环境建议启用）"
    fi
}

# --------------------- 汇总 ---------------------
print_summary() {
    echo
    info "=================== 验证汇总 ==================="
    echo -e "  ${GREEN}通过 (PASS)${NC} : ${PASS}"
    echo -e "  ${YELLOW}警告 (WARN)${NC} : ${WARN}"
    echo -e "  ${RED}失败 (FAIL)${NC} : ${FAIL}"
    info "================================================"
    echo
    if [ "$FAIL" -gt 0 ]; then
        err "验证未通过：存在 ${FAIL} 项失败，请排查后重新验证"
        exit 1
    elif [ "$WARN" -gt 0 ]; then
        warn "验证通过（存在 ${WARN} 项警告，建议关注）"
        exit 0
    else
        log "全部验证项通过"
        exit 0
    fi
}

# --------------------- 主入口 ---------------------
main() {
    info "Qi Hop 部署验证（安装目录: ${INSTALL_BASE}）"
    case "$SECTION" in
        all)
            verify_env
            verify_func
            verify_perf
            verify_log
            verify_plugin
            verify_security
            ;;
        env)      verify_env ;;
        func)     verify_func ;;
        perf)     verify_perf ;;
        log)      verify_log ;;
        plugin)   verify_plugin ;;
        security) verify_security ;;
        *) err "未知 section: $SECTION"; exit 1 ;;
    esac
    print_summary
}

main "$@"
