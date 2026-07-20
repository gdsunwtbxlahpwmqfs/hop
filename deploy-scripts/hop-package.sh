#!/bin/bash
# =============================================================
# Qi Hop 离线部署包打包脚本
# 总结：构建 Hop 并打包成离线部署包（hop-offline-<版本>.tar.gz），含 JDK/Tomcat/脚本/LLM 助手
# 功能：构建 Hop 产物 -> 组织离线包目录 -> 生成 SHA256 校验 -> 打包 tar.gz
# 用法：
#   ./hop-package.sh                          # 使用默认配置打包
#   ./hop-package.sh --skip-build             # 跳过 Maven 构建（已有产物时）
#   ./hop-package.sh --target linux           # 指定目标平台 SWT（linux/mac/win）
#   ./hop-package.sh --jdk /path/jdk.tar.gz   # 指定 JDK 离线包路径
#   ./hop-package.sh --tomcat /path/tc.tar.gz # 指定 Tomcat 离线包路径
#   ./hop-package.sh --llm-images /path/img   # 指定 LLM 离线镜像目录（默认 deploy-scripts/llm-images）
#   ./hop-package.sh --output /tmp            # 指定输出目录
#
# 离线包结构（解压后）：
#   hop-offline-<ver>/
#   ├── jdk/              # JDK 21 离线包（--jdk 指定）
#   ├── tomcat/           # Tomcat 10 离线包（--tomcat 指定）
#   ├── hop/              # Hop 产物（WAR / Client / Plugins）
#   ├── jdbc-drivers/     # JDBC 驱动
#   ├── config/           # GUI 配置（disabledGuiElements.xml 等运行时配置）
#   ├── scripts/          # 主部署脚本（deploy/start/stop/verify/uninstall）
#   ├── llm/              # LLM 助手（可选，独立部署，详见说明书第 10 节）
#   │   ├── images/       # LLM 离线镜像（litellm / qdrant，默认从 llm-images/ 拷贝）
#   │   └── ...           # docker-compose.llm.yml / litellm-config.yaml / hop-deploy-llm.sh
#   ├── docs/             # 离线部署说明书
#   └── MANIFEST.txt      # 版本清单
# =============================================================

set -Eeuo pipefail

# --------------------- 颜色与日志 ---------------------
RED='\033[0;31m'; GREEN='\033[0;32m'; YELLOW='\033[1;33m'; BLUE='\033[0;34m'; NC='\033[0m'
log()  { echo -e "${GREEN}[$(date '+%H:%M:%S')]${NC} $*"; }
warn() { echo -e "${YELLOW}[$(date '+%H:%M:%S')] WARN:${NC} $*"; }
err()  { echo -e "${RED}[$(date '+%H:%M:%S')] ERROR:${NC} $*" >&2; }
info() { echo -e "${BLUE}[$(date '+%H:%M:%S')]${NC} $*"; }

# --------------------- 默认配置 ---------------------
PROJECT_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# 版本号：从 pom.xml 读取，失败则使用默认值
HOP_VERSION="$(grep -m1 '<revision>' "$PROJECT_ROOT/pom.xml" 2>/dev/null | sed -E 's/.*<revision>([^<]+)<\/revision>.*/\1/' || true)"
if [ -z "$HOP_VERSION" ]; then
    HOP_VERSION="$(grep -m1 '<version>' "$PROJECT_ROOT/pom.xml" 2>/dev/null | sed -E 's/.*<version>([^<]+)<\/version>.*/\1/' || echo "2.19.0-SNAPSHOT")"
fi

BUILD_DATE="$(date '+%Y%m%d-%H%M%S')"
PKG_NAME="hop-offline-${HOP_VERSION}-${BUILD_DATE}"
OUTPUT_DIR="${PROJECT_ROOT}/dist"
TARGET_PLATFORM="linux"
SKIP_BUILD=false
JDK_SRC="${SCRIPT_DIR}/downloads/jdk"
TOMCAT_SRC="${SCRIPT_DIR}/downloads/tomcat"
LLM_IMAGES_DIR="${SCRIPT_DIR}/llm-images"

# --------------------- 参数解析 ---------------------
while [[ $# -gt 0 ]]; do
    case "$1" in
        --skip-build) SKIP_BUILD=true; shift ;;
        --target) TARGET_PLATFORM="$2"; shift 2 ;;
        --jdk) JDK_SRC="$2"; shift 2 ;;
        --tomcat) TOMCAT_SRC="$2"; shift 2 ;;
        --llm-images) LLM_IMAGES_DIR="$2"; shift 2 ;;
        --output) OUTPUT_DIR="$2"; shift 2 ;;
        --version) HOP_VERSION="$2"; shift 2 ;;
        -h|--help)
            sed -n '2,28p' "$0"
            exit 0 ;;
        *) err "未知参数: $1"; exit 1 ;;
    esac
done

# 平台 -> SWT artifact 映射
case "$TARGET_PLATFORM" in
    linux) SWT_ARTIFACT="org.eclipse.swt.gtk.linux.x86_64"; SWT_ENV="linux" ;;
    mac)   SWT_ARTIFACT="org.eclipse.swt.cocoa.macosx.x86_64";  SWT_ENV="mac" ;;
    win)   SWT_ARTIFACT="org.eclipse.swt.win32.win32.x86_64";   SWT_ENV="win" ;;
    *) err "不支持的目标平台: $TARGET_PLATFORM（可选: linux/mac/win）"; exit 1 ;;
esac

# STAGING_DIR：打包时的组装输出目录
# 命名规范：PKG_DIR 专指部署输入（hop-deploy.sh），此处改用 STAGING_DIR 避免同名不同义冲突
STAGING_DIR="${OUTPUT_DIR}/${PKG_NAME}"

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
    err "未找到 sha256sum 或 shasum，无法生成校验和"
    exit 1
fi

# 计算单个文件的 sha256（只输出哈希值）
sha256_hash() {
    $SHA256_CMD "$1" 2>/dev/null | awk '{print $1}'
}

generate_sha256() {
    # 为指定目录下所有文件生成 SHA256SUMS
    local dir="$1"
    log "生成 SHA256 校验文件: $dir/SHA256SUMS"
    ( cd "$dir" && find . -type f ! -name 'SHA256SUMS' -exec $SHA256_CMD {} \; | sed 's|  \./|  |' > SHA256SUMS )
}

# --------------------- 前置检查 ---------------------
preflight() {
    log "前置检查..."
    check_cmd tar || exit 1
    check_cmd zip || exit 1
    # sha256 校验依赖已在工具函数区处理（sha256sum 或 shasum 二选一）

    if [ "$SKIP_BUILD" = "false" ]; then
        # 优先使用项目内置的 mvnw（Maven Wrapper），确保版本与项目一致
        if [ -x "$PROJECT_ROOT/mvnw" ]; then
            MVN="$PROJECT_ROOT/mvnw"
            log "使用 Maven Wrapper: $MVN"
        elif check_cmd mvn; then
            MVN="mvn"
            warn "使用系统 Maven（建议使用项目内置的 mvnw 以保证版本一致）"
        else
            err "未检测到 Maven（$PROJECT_ROOT/mvnw 或系统 mvn），请安装或使用 --skip-build"
            exit 1
        fi
    else
        warn "已跳过构建，请确保产物已存在于 assemblies/*/target/"
    fi
    log "前置检查通过"
}

# --------------------- 构建 Hop 产物 ---------------------
build_hop() {
    if [ "$SKIP_BUILD" = "true" ]; then
        warn "跳过 Maven 构建"
        return 0
    fi
    log "开始构建 Hop 产物（目标平台: $TARGET_PLATFORM)..."
    cd "$PROJECT_ROOT"

    # 预处理 monetdb-jdbc（离线环境兜底）
    MONETDB_JAR="$HOME/.m2/repository/monetdb/monetdb-jdbc/12.0/monetdb-jdbc-12.0.jar"
    if [ ! -f "$MONETDB_JAR" ]; then
        warn "monetdb-jdbc 12.0 不在本地仓库，尝试下载..."
        "$SCRIPT_DIR/../dev-scripts/build-quick.sh" 2>/dev/null || true
    fi

    # 构建 SWT profile 参数：禁用与目标平台冲突的 OS-detected profile
    # （对齐 dev-scripts/build-web.sh: 构建 linux 包时用 -P'!swt-mac,assemblies'）
    local swt_profiles="assemblies"
    case "$TARGET_PLATFORM" in
        linux) swt_profiles='!swt-mac,!swt-windows,assemblies' ;;
        mac)   swt_profiles='!swt-unix,!swt-windows,assemblies' ;;
        win)   swt_profiles='!swt-unix,!swt-mac,assemblies' ;;
    esac

    "$MVN" clean install -Pfast-build -DskipTests \
        -Dswt.artifactId="$SWT_ARTIFACT" \
        -Denv="$SWT_ENV" \
        -P"$swt_profiles" || {
        err "Maven 构建失败"
        exit 1
    }
    log "Hop 构建完成"
}

# --------------------- 组织离线包目录 ---------------------
organize_package() {
    log "组织离线包目录: $STAGING_DIR"
    rm -rf "$STAGING_DIR"
    mkdir -p "$STAGING_DIR"/{jdk,tomcat,hop,jdbc-drivers,config,scripts,llm,docs}

    local web_target="$PROJECT_ROOT/assemblies/web/target"
    local client_target="$PROJECT_ROOT/assemblies/client/target"
    local plugins_target="$PROJECT_ROOT/assemblies/plugins/target"

    # 1. Hop Web WAR（已包含 REST API，无需单独处理 rest 模块）
    if [ -f "$web_target/hop.war" ]; then
        cp "$web_target/hop.war" "$STAGING_DIR/hop/"
    else
        err "未找到 hop.war: $web_target/hop.war"
        err "请确认已执行完整构建（assemblies/web 模块产出 hop.war，已包含 REST API）"
        exit 1
    fi

    # 2. Hop Client（含核心库）
    if ls "$client_target"/hop-client-*.zip >/dev/null 2>&1; then
        cp "$client_target"/hop-client-*.zip "$STAGING_DIR/hop/"
    else
        err "未找到 hop-client-*.zip"
        exit 1
    fi

    # 3. Hop 插件包
    if ls "$plugins_target"/hop-assemblies-plugins-*.zip >/dev/null 2>&1; then
        cp "$plugins_target"/hop-assemblies-plugins-*.zip "$STAGING_DIR/hop/"
    else
        warn "未找到 hop-assemblies-plugins-*.zip（可选）"
    fi

    # 4. JDBC 驱动（从 client 包的 lib/jdbc 抽取，若存在）
    if [ -d "$client_target/hop/lib/jdbc" ]; then
        cp "$client_target"/hop/lib/jdbc/*.jar "$STAGING_DIR/jdbc-drivers/" 2>/dev/null || true
    fi
    # 补充：resources/jdbc-drivers 目录下手动放置的驱动（对齐 start-hop-web.sh / docker/web.Dockerfile）
    if [ -d "$PROJECT_ROOT/resources/jdbc-drivers" ]; then
        cp "$PROJECT_ROOT"/resources/jdbc-drivers/*.jar "$STAGING_DIR/jdbc-drivers/" 2>/dev/null || true
    fi

    # 4.1 GUI 配置：disabledGuiElements.xml
    #     运行时由 GuiRegistry 从 HOP_CONFIG_FOLDER 读取，用于隐藏 Git / Search 等辅助透视图
    #     （对齐 start-hop-web.sh:347 与 core/.../GuiRegistry.java:140）
    if [ -f "$PROJECT_ROOT/resources/disabledGuiElements.xml" ]; then
        cp "$PROJECT_ROOT/resources/disabledGuiElements.xml" "$STAGING_DIR/config/"
        log "已包含 GUI 配置: config/disabledGuiElements.xml"
    else
        warn "未找到 resources/disabledGuiElements.xml（可选，用于隐藏辅助透视图）"
    fi

    # 5. JDK 离线包
    if [ -n "$JDK_SRC" ] && [ -d "$JDK_SRC" ]; then
        cp "$JDK_SRC"/*.tar.gz "$STAGING_DIR/jdk/" 2>/dev/null || true
        local jdk_count=$(ls "$STAGING_DIR/jdk/"*.tar.gz 2>/dev/null | wc -l | tr -d ' ')
        if [ "$jdk_count" -gt 0 ]; then
            log "已包含 JDK 离线包: jdk/（共 ${jdk_count} 个文件）"
        else
            warn "JDK 目录为空: ${JDK_SRC}"
        fi
    else
        warn "未指定 JDK 离线包目录（--jdk），需在目标环境自备 JDK 21"
    fi

    # 6. Tomcat 离线包
    if [ -n "$TOMCAT_SRC" ] && [ -d "$TOMCAT_SRC" ]; then
        cp "$TOMCAT_SRC"/*.tar.gz "$STAGING_DIR/tomcat/" 2>/dev/null || true
        local tomcat_count=$(ls "$STAGING_DIR/tomcat/"*.tar.gz 2>/dev/null | wc -l | tr -d ' ')
        if [ "$tomcat_count" -gt 0 ]; then
            log "已包含 Tomcat 离线包: tomcat/（共 ${tomcat_count} 个文件）"
        else
            warn "Tomcat 目录为空: ${TOMCAT_SRC}"
        fi
    else
        warn "未指定 Tomcat 离线包目录（--tomcat），需在目标环境自备 Tomcat 10"
    fi

    # 7. 部署脚本（完整生命周期：deploy → start/stop → verify → uninstall）
    cp "$SCRIPT_DIR"/hop-deploy.sh    "$STAGING_DIR/scripts/"
    cp "$SCRIPT_DIR"/hop-start.sh     "$STAGING_DIR/scripts/"
    cp "$SCRIPT_DIR"/hop-stop.sh      "$STAGING_DIR/scripts/"
    cp "$SCRIPT_DIR"/hop-verify.sh    "$STAGING_DIR/scripts/"
    cp "$SCRIPT_DIR"/hop-uninstall.sh "$STAGING_DIR/scripts/"
    chmod +x "$STAGING_DIR/scripts/"*.sh

    # 8. LLM 助手（可选扩展，独立部署，需临时联网拉取 Docker 镜像）
    # 详见《离线部署说明书.md》第 10 节
    local llm_files=(
        hop-deploy-llm.sh
        docker-compose.llm.yml
        litellm-config.yaml
        env.example
    )
    local llm_missing=0
    for f in "${llm_files[@]}"; do
        if [ -f "$SCRIPT_DIR/$f" ]; then
            cp "$SCRIPT_DIR/$f" "$STAGING_DIR/llm/"
        else
            warn "LLM 助手文件缺失，跳过: $SCRIPT_DIR/$f"
            llm_missing=$((llm_missing + 1))
        fi
    done
    # 仅在所有文件齐全时赋予可执行权限，避免部分缺失误导用户
    if [ "$llm_missing" -eq 0 ]; then
        chmod +x "$STAGING_DIR/llm/hop-deploy-llm.sh"
        log "已包含 LLM 助手脚本（独立部署，详见说明书第 10 节）"
    else
        warn "LLM 助手脚本不完整（缺失 ${llm_missing} 个文件），llm/ 目录可能不可用"
    fi

    # 8.1 LLM 镜像（可选，通过 --llm-images 指定离线镜像目录）
    if [ -n "$LLM_IMAGES_DIR" ] && [ -d "$LLM_IMAGES_DIR" ]; then
        mkdir -p "$STAGING_DIR/llm/images"
        cp "$LLM_IMAGES_DIR"/*.tar.gz "$STAGING_DIR/llm/images/" 2>/dev/null || true
        local llm_img_count=$(ls "$STAGING_DIR/llm/images/"*.tar.gz 2>/dev/null | wc -l | tr -d ' ')
        if [ "$llm_img_count" -gt 0 ]; then
            log "已包含 LLM 离线镜像: llm/images/（共 ${llm_img_count} 个镜像文件）"
        else
            warn "LLM 镜像目录为空: ${LLM_IMAGES_DIR}"
        fi
    else
        warn "未指定 LLM 离线镜像目录（--llm-images），部署时需临时联网拉取镜像"
    fi

    # 9. 离线部署说明书（hop-assistant-manual 文档已通过 Maven 打包进 WAR，此处不再单独包含）
    if [ -f "$PROJECT_ROOT/离线部署说明书.md" ]; then
        cp "$PROJECT_ROOT/离线部署说明书.md" "$STAGING_DIR/docs/"
        log "已包含离线部署说明书: docs/离线部署说明书.md"
    else
        warn "未找到离线部署说明书.md"
    fi

    # 10. 版本清单 manifest
    cat > "$STAGING_DIR/MANIFEST.txt" <<EOF
# Qi Hop 离线部署包清单
hop_version=${HOP_VERSION}
build_date=${BUILD_DATE}
target_platform=${TARGET_PLATFORM}
package_name=${PKG_NAME}
generated_by=deploy-scripts/hop-package.sh

# 包含组件
hop_war=$(ls "$STAGING_DIR/hop/"*.war 2>/dev/null | xargs -n1 basename 2>/dev/null || echo "N/A")
hop_client=$(ls "$STAGING_DIR/hop/"hop-client-*.zip 2>/dev/null | xargs -n1 basename || echo "N/A")
hop_plugins=$(ls "$STAGING_DIR/hop/"hop-assemblies-plugins-*.zip 2>/dev/null | xargs -n1 basename || echo "N/A")
jdk=$(ls "$STAGING_DIR/jdk/"*.tar.gz 2>/dev/null | xargs -n1 basename || echo "未包含")
tomcat=$(ls "$STAGING_DIR/tomcat/"*.tar.gz 2>/dev/null | xargs -n1 basename || echo "未包含")
jdbc_drivers=$(ls "$STAGING_DIR/jdbc-drivers/"*.jar 2>/dev/null | wc -l | tr -d ' ')

# 运行时配置（config/）
disabled_gui_elements=$([ -f "$STAGING_DIR/config/disabledGuiElements.xml" ] && echo "included" || echo "未包含")
config_files=$(ls "$STAGING_DIR/config/" 2>/dev/null | paste -sd, - || echo "N/A")

# 主部署脚本清单（scripts/）
deploy_scripts=$(ls "$STAGING_DIR/scripts/"*.sh 2>/dev/null | xargs -n1 basename 2>/dev/null | paste -sd, - || echo "N/A")

# LLM 助手（llm/，可选扩展，独立部署）
# 详见《离线部署说明书.md》第 10 节
# 部署方式：临时联网拉取 Docker 镜像（litellm + qdrant），与主部署解耦
llm_assistant=included
llm_files=$(ls "$STAGING_DIR/llm/" 2>/dev/null | paste -sd, - || echo "N/A")
llm_deploy_script=llm/hop-deploy-llm.sh

# 文档（docs/）
# 离线部署说明书：部署与运维指南
# hop-assistant-manual：插件帮助文档已通过 Maven 打包进 WAR，此处不再单独包含
offline_manual=$([ -f "$STAGING_DIR/docs/离线部署说明书.md" ] && echo "included" || echo "未包含")
EOF

    log "目录组织完成"
}

# --------------------- 生成校验与打包 ---------------------
finalize_package() {
    log "生成校验文件并打包..."
    generate_sha256 "$STAGING_DIR"

    # 打包为 tar.gz
    cd "$OUTPUT_DIR"
    tar -czf "${PKG_NAME}.tar.gz" "${PKG_NAME}"

    # 生成顶层校验（兼容 macOS/Linux）
    sha256_hash "${PKG_NAME}.tar.gz" > "${PKG_NAME}.tar.gz.sha256"

    log "打包完成"
    echo
    info "=================== 离线包信息 ==================="
    info " 包名    : ${PKG_NAME}.tar.gz"
    info " 路径    : ${OUTPUT_DIR}/${PKG_NAME}.tar.gz"
    info " 校验    : ${OUTPUT_DIR}/${PKG_NAME}.tar.gz.sha256"
    info " 大小    : $(du -h "${PKG_NAME}.tar.gz" | cut -f1)"
    info " 清单    : ${STAGING_DIR}/MANIFEST.txt"
    echo
    info " 包内目录："
    info "   scripts/  → 主部署脚本（hop-deploy/start/stop/verify/uninstall.sh）"
    info "   llm/      → LLM 助手（可选，独立部署，详见说明书第 10 节）"
    info "   hop/      → Hop 产物（WAR / Client / Plugins）"
    info "   jdk/      → JDK 21 离线包（若 --jdk 指定）"
    info "   tomcat/   → Tomcat 10 离线包（若 --tomcat 指定）"
    info "   jdbc-drivers/ → JDBC 驱动"
    info "   config/   → GUI 配置（disabledGuiElements.xml）"
info "   docs/     → 文档（离线部署说明书 + hop-assistant-manual 插件帮助文档）"
info "==================================================="
}

# --------------------- 主流程 ---------------------
main() {
    log "Qi Hop 离线部署包打包（版本: $HOP_VERSION, 平台: $TARGET_PLATFORM)"
    preflight
    build_hop
    organize_package
    finalize_package
}

main "$@"
