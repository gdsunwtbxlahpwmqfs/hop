#!/bin/bash
# =============================================================
# Qi Hop LLM 镜像离线下载脚本
# 功能：下载 LiteLLM 和 Qdrant Docker 镜像并保存为 tar 文件，支持离线导入
# 用法：
#   ./hop-download-llm-images.sh                          # 默认下载 linux/amd64 镜像
#   ./hop-download-llm-images.sh --platform linux         # 指定目标平台（linux/mac/win）
#   ./hop-download-llm-images.sh --arch arm64             # 指定架构（amd64/arm64）
#   ./hop-download-llm-images.sh --output /tmp/images     # 指定输出目录
#   ./hop-download-llm-images.sh --list                   # 列出支持的镜像
#   ./hop-download-llm-images.sh --verify                 # 仅校验已下载镜像
#   ./hop-download-llm-images.sh --import /path/to/tar    # 导入单个镜像到 Docker
#   ./hop-download-llm-images.sh --import-all /path/      # 导入目录下所有镜像
# =============================================================

set -Eeuo pipefail

RED='\033[0;31m'; GREEN='\033[0;32m'; YELLOW='\033[1;33m'; BLUE='\033[0;34m'; NC='\033[0m'
log()  { echo -e "${GREEN}[$(date '+%H:%M:%S')]${NC} $*"; }
warn() { echo -e "${YELLOW}[$(date '+%H:%M:%S')] WARN:${NC} $*"; }
err()  { echo -e "${RED}[$(date '+%H:%M:%S')] ERROR:${NC} $*" >&2; }
info() { echo -e "${BLUE}[$(date '+%H:%M:%S')]${NC} $*"; }

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
OUTPUT_DIR="${SCRIPT_DIR}/llm-images"
PLATFORM="linux"
ARCH="amd64"
VERIFY_ONLY=false
LIST_ONLY=false
IMPORT_FILE=""
IMPORT_DIR=""

while [[ $# -gt 0 ]]; do
    case "$1" in
        --platform)     PLATFORM="$2"; shift 2 ;;
        --arch)         ARCH="$2"; shift 2 ;;
        --output)       OUTPUT_DIR="$2"; shift 2 ;;
        --verify)       VERIFY_ONLY=true; shift ;;
        --list)         LIST_ONLY=true; shift ;;
        --import)       IMPORT_FILE="$2"; shift 2 ;;
        --import-all)   IMPORT_DIR="$2"; shift 2 ;;
        -h|--help)
            sed -n '2,17p' "$0"
            exit 0 ;;
        *) err "未知参数: $1"; exit 1 ;;
    esac
done

IMAGES=(
    "ghcr.io/berriai/litellm:main-stable"
    "qdrant/qdrant:v1.12.4"
)

get_image_filename() {
    local img="$1"
    local platform="$2"
    local arch="$3"
    echo "${img//\//_}_${platform}_${arch}.tar.gz"
}

check_cmd() {
    command -v "$1" >/dev/null 2>&1 || { err "缺少必要命令: $1"; return 1; }
}

sha256_hash() {
    if command -v sha256sum >/dev/null 2>&1; then
        sha256sum "$1" 2>/dev/null | awk '{print $1}'
    elif command -v shasum >/dev/null 2>&1; then
        shasum -a 256 "$1" 2>/dev/null | awk '{print $1}'
    else
        err "未找到 sha256sum 或 shasum"
        return 1
    fi
}

list_images() {
    info "=================== 支持的 LLM 镜像列表 ==================="
    echo
    info "平台: ${PLATFORM}"
    info "架构: ${ARCH}"
    echo
    info "镜像列表:"
    for img in "${IMAGES[@]}"; do
        info "  ${img}"
    done
    echo
    info "支持的平台:"
    info "  linux   - Linux x86_64/aarch64"
    info "  mac     - macOS x86_64/arm64"
    info "  win     - Windows x86_64"
    echo
    info "使用示例:"
    echo "  ./hop-download-llm-images.sh --platform linux --arch amd64"
    echo "  ./hop-download-llm-images.sh --import-all ${OUTPUT_DIR}"
    exit 0
}

pull_and_save_image() {
    local img="$1"
    local platform="$2"
    local arch="$3"
    local filename=$(get_image_filename "$img" "$platform" "$arch")
    local output_path="${OUTPUT_DIR}/${filename}"

    if [ -f "$output_path" ]; then
        log "已存在，跳过: ${filename}"
        return 0
    fi

    log "拉取镜像: ${img} (${platform}/${arch})"
    if ! docker pull --platform "${platform}/${arch}" "$img" 2>/dev/null; then
        err "拉取失败: ${img}"
        return 1
    fi

    log "保存镜像: ${filename}"
    if ! docker save "$img" | gzip > "$output_path"; then
        err "保存失败: ${filename}"
        return 1
    fi

    local size
    size=$(du -h "$output_path" | cut -f1)
    log "保存完成: ${filename} (${size})"
    return 0
}

verify_images() {
    log "校验已下载的镜像文件（平台: ${PLATFORM}，架构: ${ARCH}）..."
    local all_pass=0

    for img in "${IMAGES[@]}"; do
        local filename=$(get_image_filename "$img" "$PLATFORM" "$ARCH")
        local output_path="${OUTPUT_DIR}/${filename}"
        if [ -f "$output_path" ]; then
            local size
            size=$(du -h "$output_path" | cut -f1)
            local sha256
            sha256=$(sha256_hash "$output_path")
            log "${GREEN}✓${NC} ${filename}: ${size} (SHA256: ${sha256})"
        else
            err "${filename}: 文件不存在"
            all_pass=1
        fi
    done

    if [ "$all_pass" -eq 0 ]; then
        log "所有镜像文件校验通过"
    else
        err "部分镜像文件缺失"
        exit 1
    fi
    exit 0
}

import_single_image() {
    local tar_file="$1"
    if [ ! -f "$tar_file" ]; then
        err "文件不存在: ${tar_file}"
        exit 1
    fi

    log "导入镜像: ${tar_file}"
    if ! gunzip -c "$tar_file" | docker load; then
        err "导入失败: ${tar_file}"
        exit 1
    fi
    log "导入完成"
}

import_all_images() {
    local dir="$1"
    if [ ! -d "$dir" ]; then
        err "目录不存在: ${dir}"
        exit 1
    fi

    local tar_files
    tar_files=$(ls "${dir}"/*.tar.gz 2>/dev/null || true)
    if [ -z "$tar_files" ]; then
        err "未找到 tar.gz 文件: ${dir}"
        exit 1
    fi

    log "导入目录下所有镜像: ${dir}"
    for tar_file in $tar_files; do
        import_single_image "$tar_file"
    done
    log "全部导入完成"
}

preflight() {
    log "前置检查..."
    check_cmd docker || exit 1

    if ! docker info >/dev/null 2>&1; then
        err "docker daemon 未运行，或当前用户无 docker 权限"
        exit 1
    fi

    case "$PLATFORM" in
        linux|mac|win) ;;
        *) err "不支持的平台: ${PLATFORM}（可选: linux/mac/win）"; exit 1 ;;
    esac

    case "$ARCH" in
        amd64|arm64) ;;
        *) err "不支持的架构: ${ARCH}（可选: amd64/arm64）"; exit 1 ;;
    esac

    mkdir -p "$OUTPUT_DIR"
    log "前置检查通过"
}

download_images() {
    log "开始下载 LLM 镜像（平台: ${PLATFORM}，架构: ${ARCH}）..."

    for img in "${IMAGES[@]}"; do
        pull_and_save_image "$img" "$PLATFORM" "$ARCH" || {
            err "镜像下载失败: ${img}"; exit 1
        }
    done

    log "镜像下载完成"
}

print_summary() {
    echo
    info "=================== 下载完成 ==================="
    info " 输出目录   : ${OUTPUT_DIR}"
    info " 平台       : ${PLATFORM}"
    info " 架构       : ${ARCH}"
    info ""
    info " 文件清单:"
    ls -lh "${OUTPUT_DIR}/" 2>/dev/null | sed 's/^/    /' || true
    info "================================================"
    info ""
    info "下一步:"
    info "  1) 校验下载: ./hop-download-llm-images.sh --platform ${PLATFORM} --arch ${ARCH} --verify"
    info "  2) 复制到离线机器并导入:"
    info "     ./hop-download-llm-images.sh --import-all ${OUTPUT_DIR}"
    info "  3) 部署 LLM 栈: ./hop-deploy-llm.sh --skip-pull"
}

main() {
    if [ "$LIST_ONLY" = "true" ]; then
        list_images
    fi

    if [ "$VERIFY_ONLY" = "true" ]; then
        verify_images
    fi

    if [ -n "$IMPORT_FILE" ]; then
        import_single_image "$IMPORT_FILE"
        exit 0
    fi

    if [ -n "$IMPORT_DIR" ]; then
        import_all_images "$IMPORT_DIR"
        exit 0
    fi

    log "Qi Hop LLM 镜像离线下载（平台: ${PLATFORM}，架构: ${ARCH}）"
    preflight
    download_images
    print_summary
}

main "$@"