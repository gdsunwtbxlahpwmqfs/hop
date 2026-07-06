#!/bin/bash
set -e
cd "$(dirname "$0")/.."

HOP_REST_URL="${HOP_REST_URL:-http://localhost:8080/hop/api/v1}"

echo "==> Hop Knowledge Base Index Builder"
echo "    REST API: $HOP_REST_URL"
echo ""

echo "==> Generating markdown documentation from .hpl pipeline files..."
python3 scripts/generate-hpl-md.py
echo ""

echo "==> Checking if Hop Web service is reachable..."
if ! curl -s -f "$HOP_REST_URL/knowledgebase/status" >/dev/null 2>&1; then
  echo "    ERROR: Hop Web service not reachable at $HOP_REST_URL"
  echo "    Please start the dev server first: ./dev-scripts/start-web-dev.sh"
  exit 1
fi

echo "==> Getting current index status..."
STATUS_RESP=$(curl -s "$HOP_REST_URL/knowledgebase/status")
echo "    $STATUS_RESP"
echo ""

if echo "$STATUS_RESP" | grep -q '"enabled":false'; then
  echo "    ERROR: RAG is disabled (HOP_KB_ENABLED not set to true)"
  echo "    Please check docker-compose.dev.yml and .env configuration"
  exit 1
fi

echo "==> Clearing existing index..."
CLEAR_RESP=$(curl -s -w "\n%{http_code}" -X DELETE "$HOP_REST_URL/knowledgebase")
CLEAR_HTTP_CODE=$(echo "$CLEAR_RESP" | tail -1)
CLEAR_RESP_BODY=$(echo "$CLEAR_RESP" | sed '$d')

echo "    HTTP Status: $CLEAR_HTTP_CODE"
echo "    Response: $CLEAR_RESP_BODY"
echo ""

if [ "$CLEAR_HTTP_CODE" -ne 200 ]; then
  echo "    WARNING: Failed to clear existing index: $CLEAR_RESP_BODY"
  echo "    Continuing with index build..."
  echo ""
fi

echo "==> Starting index build..."
BUILD_RESP=$(curl -s -w "\n%{http_code}" -X POST "$HOP_REST_URL/knowledgebase/index")

HTTP_CODE=$(echo "$BUILD_RESP" | tail -1)
RESP_BODY=$(echo "$BUILD_RESP" | sed '$d')

echo "    HTTP Status: $HTTP_CODE"
echo "    Response: $RESP_BODY"
echo ""

if [ "$HTTP_CODE" -ne 200 ]; then
  echo "==> Index build failed!"
  exit 1
fi

echo "==> Index build completed successfully!"
CHUNKS=$(echo "$RESP_BODY" | python3 -c "import sys,json; d=json.load(sys.stdin); print(d.get('chunks', 0))")
echo "    $CHUNKS chunks indexed"