#!/bin/bash
# =============================================================
# Qi Hop 服务停止脚本（hop-start.sh stop 的快捷别名）
#
# 设计原则：薄包装（thin wrapper）
#   - 不重复实现 stop 逻辑，避免代码漂移
#   - 单一数据源：所有停止逻辑保留在 hop-start.sh 的 do_stop()
#   - 透传所有参数，行为与 hop-start.sh stop 完全一致
#
# 用法：
#   ./hop-stop.sh                    # 停止默认实例
#   ./hop-stop.sh --instance bi      # 停止 bi 实例
#   ./hop-stop.sh --base /opt/qi    # 指定 INSTALL_BASE
#
# 等价命令：
#   ./hop-start.sh stop
#   ./hop-start.sh --instance bi stop
# =============================================================

set -Euo pipefail

# 定位同目录下的 hop-start.sh
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
START_SCRIPT="${SCRIPT_DIR}/hop-start.sh"

if [ ! -x "$START_SCRIPT" ]; then
    echo -e "\033[0;31m[ERROR]\033[0m 未找到 hop-start.sh: $START_SCRIPT" >&2
    exit 1
fi

# 透传所有参数，在末尾追加 stop 子命令
# 支持两种调用形式：
#   ./hop-stop.sh --instance bi        -> hop-start.sh --instance bi stop
#   ./hop-stop.sh                      -> hop-start.sh stop
exec "$START_SCRIPT" "$@" stop
