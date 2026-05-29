#!/bin/bash
set -e
cd "$(dirname "$0")/.."

# Check if web assembly exists
if [ ! -d "assemblies/web/target/webapp" ]; then
  echo "==> Web assembly not found. Building first..."
  ./dev-scripts/build-web.sh
fi

echo "==> Starting Hop Web development server..."
echo "    Web UI:  http://localhost:8080/ui"
echo "    REST:    http://localhost:8080/hop/"
echo "    Debug:   port 5005 (JDWP)"
echo ""
docker compose -f docker-compose.dev.yml up "$@"
