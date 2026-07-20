#!/bin/bash
# =============================================================
# Qi Hop 离线依赖包下载脚本
# 功能：下载 JDK、Tomcat 等离线部署所需的依赖包
# 用法：
#   ./hop-download-deps.sh                          # 默认下载 Linux 平台依赖
#   ./hop-download-deps.sh --platform linux         # 指定平台（linux/mac/win）
#   ./hop-download-deps.sh --output /tmp/downloads  # 指定输出目录
#   ./hop-download-deps.sh --list                   # 列出支持的版本
#   ./hop-download-deps.sh --verify                 # 仅校验已下载文件
# =============================================================

set -Eeuo pipefail

# --------------------- 颜色与日志 ---------------------
RED='\033[0;31m'; GREEN='\033[0;32m'; YELLOW='\033[1;33m'; BLUE='\033[0;34m'; NC='\033[0m'
log()  { echo -e "${GREEN}[$(date '+%H:%M:%S')]${NC} $*"; }
warn() { echo -e "${YELLOW}[$(date '+%H:%M:%S')] WARN:${NC} $*"; }
err()  { echo -e "${RED}[$(date '+%H:%M:%S')] ERROR:${NC} $*" >&2; }
info() { echo -e "${BLUE}[$(date '+%H:%M:%S')]${NC} $*"; }

# --------------------- 默认配置 ---------------------
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
OUTPUT_DIR="${SCRIPT_DIR}/downloads"
PLATFORM="linux"
JDK_VERSION="21.0.3"
TOMCAT_VERSION="10.1.24"
VERIFY_ONLY=false
LIST_ONLY=false

# --------------------- 参数解析 ---------------------
while [[ $# -gt 0 ]]; do
    case "$1" in
        --platform)     PLATFORM="$2"; shift 2 ;;
        --output)       OUTPUT_DIR="$2"; shift 2 ;;
        --verify)       VERIFY_ONLY=true; shift ;;
        --list)         LIST_ONLY=true; shift ;;
        -h|--help)
            sed -n '2,12p' "$0"
            exit 0 ;;
        *) err "未知参数: $1"; exit 1 ;;
    esac
done

# --------------------- 版本配置 ---------------------
get_jdk_url() {
    local platform="$1"
    case "$platform" in
        linux) echo "https://download.oracle.com/java/21/archive/jdk-21.0.3_linux-x64_bin.tar.gz" ;;
        mac)   echo "https://download.oracle.com/java/21/archive/jdk-21.0.3_macos-x64_bin.tar.gz" ;;
        win)   echo "https://download.oracle.com/java/21/archive/jdk-21.0.3_windows-x64_bin.zip" ;;
        *) return 1 ;;
    esac
}

get_jdk_sha256() {
    local platform="$1"
    case "$platform" in
        linux) echo "c245aeec0b82b3fc56c8696968b6b88220495f512b4353b3c42702db215fd595" ;;
        mac)   echo "f51a83c6328d1327aac38eb7345e0846fea8e859ef5bcf65f98a5793aa027253" ;;
        win)   echo "3f78795f92a1b40997226d1d611b28137334415dd4885047034f55e08b0c6c4f" ;;
        *) return 1 ;;
    esac
}

get_tomcat_url() {
    echo "https://archive.apache.org/dist/tomcat/tomcat-10/v10.1.24/bin/apache-tomcat-10.1.24.tar.gz"
}

get_tomcat_sha256() {
    echo "216db5c726a6857e2a698ba5f9406fa862d037733f98ab2338feb3fc511c3068"
}

# --------------------- 工具函数 ---------------------
check_cmd() {
    command -v "$1" >/dev/null 2>&1 || { err "缺少必要命令: $1"; return 1; }
}

# sha256 兼容层：Linux 用 sha256sum，macOS 用 shasum -a 256
SHA256_CMD=""
if command -v sha256sum >/dev/null 2>&1; then
    SHA256_CMD="sha256sum"
elif command -v shasum >/dev/null 2>&1; then
    SHA256_CMD="shasum -a 256"
else
    err "未找到 sha256sum 或 shasum，无法校验"
    exit 1
fi

# 计算单个文件的 sha256（只输出哈希值）
sha256_hash() {
    $SHA256_CMD "$1" 2>/dev/null | awk '{print $1}'
}

download_file() {
    local url="$1"
    local dest="$2"
    local expected_sha256="${3:-}"

    local filename
    filename=$(basename "$url")
    local output_path="${dest}/${filename}"

    if [ -f "$output_path" ]; then
        if [ -n "$expected_sha256" ]; then
            local actual_sha256
            actual_sha256=$(sha256_hash "$output_path")
            if [ "$actual_sha256" = "$expected_sha256" ]; then
                log "已存在且校验通过: ${filename}"
                return 0
            fi
            warn "已存在但校验失败，重新下载: ${filename}"
        else
            warn "已存在，跳过下载: ${filename}"
            return 0
        fi
    fi

    log "下载: ${filename}"
    if ! curl -L -f -o "$output_path" "$url" 2>/dev/null; then
        err "下载失败: ${url}"
        rm -f "$output_path"
        return 1
    fi

    if [ -n "$expected_sha256" ]; then
        local actual_sha256
        actual_sha256=$(sha256_hash "$output_path")
        if [ "$actual_sha256" != "$expected_sha256" ]; then
            err "SHA256 校验失败: ${filename}"
            err "  期望: ${expected_sha256}"
            err "  实际: ${actual_sha256}"
            rm -f "$output_path"
            return 1
        fi
        log "SHA256 校验通过: ${filename}"
    fi

    return 0
}

list_versions() {
    info "=================== 支持的版本列表 ==================="
    echo
    info "JDK 版本: ${JDK_VERSION}"
    info "支持平台: linux / mac / win"
    echo
    info "Tomcat 版本: ${TOMCAT_VERSION}"
    echo
    info "使用示例:"
    echo "  ./hop-download-deps.sh --platform linux"
    exit 0
}

verify_downloads() {
    log "校验已下载的依赖包..."
    local all_pass=0

    local jdk_url
    jdk_url=$(get_jdk_url "$PLATFORM") || {
        err "不支持的平台: ${PLATFORM}"; exit 1
    }
    local jdk_file="${OUTPUT_DIR}/jdk/$(basename "$jdk_url")"
    if [ -f "$jdk_file" ]; then
        local expected_sha256
        expected_sha256=$(get_jdk_sha256 "$PLATFORM")
        local actual_sha256
        actual_sha256=$(sha256_hash "$jdk_file")
        if [ "$actual_sha256" = "$expected_sha256" ]; then
            log "${GREEN}✓${NC} JDK ${JDK_VERSION} (${PLATFORM}): 校验通过"
        else
            err "JDK ${JDK_VERSION} (${PLATFORM}): 校验失败"
            all_pass=1
        fi
    else
        warn "JDK 文件不存在: ${jdk_file}"
    fi

    local tomcat_url
    tomcat_url=$(get_tomcat_url)
    local tomcat_file="${OUTPUT_DIR}/tomcat/$(basename "$tomcat_url")"
    if [ -f "$tomcat_file" ]; then
        local tomcat_expected_sha256
        tomcat_expected_sha256=$(get_tomcat_sha256)
        local tomcat_actual_sha256
        tomcat_actual_sha256=$(sha256_hash "$tomcat_file")
        if [ "$tomcat_actual_sha256" = "$tomcat_expected_sha256" ]; then
            log "${GREEN}✓${NC} Tomcat ${TOMCAT_VERSION}: 校验通过"
        else
            err "Tomcat ${TOMCAT_VERSION}: 校验失败"
            all_pass=1
        fi
    else
        warn "Tomcat 文件不存在: ${tomcat_file}"
    fi

    if [ "$all_pass" -eq 0 ]; then
        log "所有文件校验通过"
    else
        err "部分文件校验失败"
        exit 1
    fi
    exit 0
}

# --------------------- 前置检查 ---------------------
preflight() {
    log "前置检查..."
    check_cmd curl || exit 1
    # sha256 校验依赖已在工具函数区处理（sha256sum 或 shasum 二选一）

    case "$PLATFORM" in
        linux|mac|win) ;;
        *) err "不支持的平台: ${PLATFORM}（可选: linux/mac/win）"; exit 1 ;;
    esac

    mkdir -p "${OUTPUT_DIR}"/{jdk,tomcat}
    log "前置检查通过"
}

# --------------------- 下载依赖 ---------------------
download_deps() {
    log "开始下载依赖包（平台: ${PLATFORM}）..."

    local jdk_url
    jdk_url=$(get_jdk_url "$PLATFORM")
    local jdk_sha256
    jdk_sha256=$(get_jdk_sha256 "$PLATFORM")

    log "下载 JDK ${JDK_VERSION}..."
    download_file "$jdk_url" "${OUTPUT_DIR}/jdk" "$jdk_sha256" || {
        err "JDK 下载失败"; exit 1
    }

    local tomcat_url
    tomcat_url=$(get_tomcat_url)
    local tomcat_sha256
    tomcat_sha256=$(get_tomcat_sha256)
    log "下载 Tomcat ${TOMCAT_VERSION}..."
    download_file "$tomcat_url" "${OUTPUT_DIR}/tomcat" "$tomcat_sha256" || {
        err "Tomcat 下载失败"; exit 1
    }

    log "依赖包下载完成"
}

# --------------------- 输出摘要 ---------------------
print_summary() {
    echo
    info "=================== 下载完成 ==================="
    info " 输出目录   : ${OUTPUT_DIR}"
    info " 平台       : ${PLATFORM}"
    info " JDK 版本   : ${JDK_VERSION}"
    info " Tomcat 版本: ${TOMCAT_VERSION}"
    info ""
    info " 文件清单:"
    ls -lh "${OUTPUT_DIR}/jdk/" 2>/dev/null | sed 's/^/    /' || true
    ls -lh "${OUTPUT_DIR}/tomcat/" 2>/dev/null | sed 's/^/    /' || true
    info "================================================"
    info ""
    info "下一步:"
    info "  1) 校验下载: ./hop-download-deps.sh --verify"
    info "  2) 打包离线包: ./hop-package.sh --jdk ${OUTPUT_DIR}/jdk/*.tar.gz --tomcat ${OUTPUT_DIR}/tomcat/*.tar.gz"
}

# --------------------- 主流程 ---------------------
main() {
    if [ "$LIST_ONLY" = "true" ]; then
        list_versions
    fi

    if [ "$VERIFY_ONLY" = "true" ]; then
        verify_downloads
    fi

    log "Qi Hop 离线依赖下载（平台: ${PLATFORM}）"
    preflight
    download_deps
    print_summary
}

main "$@"