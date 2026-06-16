#!/bin/bash
set -e
cd "$(dirname "$0")/.."
echo "==> Building Web assembly for Linux platform..."

# Clean previous web artifacts to avoid stale SWT jars
rm -rf assemblies/beam/target
rm -rf assemblies/client/target
rm -rf assemblies/core/target
rm -rf assemblies/plugins/target
rm -rf assemblies/static/target
rm -rf assemblies/web/target

./mvnw clean install -Dfast-build -DskipTests \
  -Dswt.artifactId=org.eclipse.swt.gtk.linux.x86_64 \
  -Denv=linux \
  -P'!swt-mac,assemblies'
echo ""
echo "==> Web assembly ready at: assemblies/web/target/webapp/"
echo "==> Start dev server: docker compose -f docker-compose.dev.yml up"
