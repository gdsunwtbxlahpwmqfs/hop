#!/bin/bash
set -e
cd "$(dirname "$0")/.."
echo "==> Building Web assembly..."
./mvnw install -Dfast-build -DskipTests -Pbase,assemblies
echo ""
echo "==> Web assembly ready at: assemblies/web/target/webapp/"
echo "==> Start dev server: docker compose -f docker-compose.dev.yml up"
