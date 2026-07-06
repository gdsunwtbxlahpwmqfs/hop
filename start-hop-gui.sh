#!/bin/bash

set -e

HOP_CLIENT_ZIP="./assemblies/client/target/hop-client-2.19.0-SNAPSHOT.zip"
HOP_INSTALL_DIR="./hop-client-run"
HOP_DIR="$HOP_INSTALL_DIR/hop"

echo "============================================"
echo "  Qi Hop GUI 启动脚本"
echo "============================================"

if [ ! -f "$HOP_CLIENT_ZIP" ]; then
    echo "错误: 未找到 Hop 客户端包: $HOP_CLIENT_ZIP"
    echo "请先运行: ./mvnw clean install -pl assemblies/client -am -DskipTests"
    exit 1
fi

echo "解压 Hop 客户端到 $HOP_INSTALL_DIR..."
rm -rf "$HOP_INSTALL_DIR" 2>/dev/null || true
mkdir -p "$HOP_INSTALL_DIR"
unzip -q -o "$HOP_CLIENT_ZIP" -d "$HOP_INSTALL_DIR"
echo "解压完成"

echo ""
echo "检查 LiteLLM 代理..."
if ! docker ps | grep -q "hop-litellm-dev"; then
    echo "启动 LiteLLM 代理..."
    cd /Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop
    docker compose -f docker-compose.dev.yml up -d litellm
    sleep 3
    echo "LiteLLM 代理已启动"
else
    echo "LiteLLM 代理已在运行"
fi

echo ""
echo "设置环境变量..."
export HOP_LLM_ENABLED=true
export HOP_LLM_API_URL=http://localhost:4000/v1
export HOP_LLM_API_KEY=sk-hop-litellm-dev
export HOP_LLM_MODEL=qwen-plus

echo "  HOP_LLM_ENABLED: $HOP_LLM_ENABLED"
echo "  HOP_LLM_API_URL: $HOP_LLM_API_URL"
echo "  HOP_LLM_MODEL: $HOP_LLM_MODEL"

echo ""
echo "启动 Qi Hop GUI..."
cd "$HOP_DIR"
./hop-gui.sh "$@"