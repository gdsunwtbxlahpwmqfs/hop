#!/bin/bash
set -e
cd "$(dirname "$0")/.."

# Clean up previously extracted directories
echo "==> Cleaning up previously extracted directories..."
rm -rf "assemblies/core/target/hop"
rm -rf "assemblies/beam/target/hop"
rm -rf "assemblies/web/target/webapp"
rm -rf "assemblies/client/target/hop"

# Check if hop.war exists
if [ ! -f "assemblies/web/target/hop.war" ]; then
  echo "==> Web assembly not found. Building first..."
  ./dev-scripts/build-web.sh
fi

# Check if core assembly exists
CORE_ZIP=$(ls assemblies/core/target/hop-core-assembly-*-SNAPSHOT.zip 2>/dev/null | head -1)
CORE_DIR="assemblies/core/target/hop/lib/core"
if [ -z "$CORE_ZIP" ]; then
  echo "==> Core assembly zip not found. Building..."
  ./mvnw clean package -pl assemblies/core -am -Dfast-build -DskipTests
  CORE_ZIP=$(ls assemblies/core/target/hop-core-assembly-*-SNAPSHOT.zip 2>/dev/null | head -1)
elif [ ! -d "$CORE_DIR" ] || [ "$CORE_ZIP" -nt "$CORE_DIR" ]; then
  echo "==> Extracting core assembly..."
  rm -rf "assemblies/core/target/hop"
  unzip -q "$CORE_ZIP" -d "assemblies/core/target/"
fi

# Check if webapp directory needs to be (re)extracted from war
# Re-extract if:
# 1. webapp directory is empty/missing, OR
# 2. hop.war is newer than webapp directory
WEBAPP_DIR="assemblies/web/target/webapp"
if [ -z "$(ls -A "$WEBAPP_DIR" 2>/dev/null)" ] || [ "assemblies/web/target/hop.war" -nt "$WEBAPP_DIR" ]; then
  echo "==> Extracting webapp from war file..."
  rm -rf "$WEBAPP_DIR"
  mkdir -p "$WEBAPP_DIR"
  unzip -q assemblies/web/target/hop.war -d "$WEBAPP_DIR"
fi

# Fix SWT jar: replace macOS cocoa with Linux GTK (built on macOS, runs in Linux container)
WEBAPP_LIB="assemblies/web/target/webapp/WEB-INF/lib"
LINUX_SWT="$HOME/.m2/repository/org/eclipse/platform/org.eclipse.swt.gtk.linux.x86_64/3.132.0/org.eclipse.swt.gtk.linux.x86_64-3.132.0.jar"
if ls "$WEBAPP_LIB"/org.eclipse.swt.cocoa.* >/dev/null 2>&1; then
  echo "==> Replacing macOS SWT with Linux GTK SWT..."
  rm -f "$WEBAPP_LIB"/org.eclipse.swt.cocoa.*
  if [ -f "$LINUX_SWT" ]; then
    cp "$LINUX_SWT" "$WEBAPP_LIB/"
  else
    echo "==> Warning: Linux SWT jar not found in local Maven repo"
  fi
fi

# Check if plugins directory needs to be (re)extracted from plugins zip
PLUGINS_DIR="assemblies/client/target/hop/plugins"
PLUGINS_ZIP="assemblies/plugins/target/hop-assemblies-plugins-2.19.0-SNAPSHOT.zip"
if [ -f "$PLUGINS_ZIP" ] || [ "$PLUGINS_ZIP" -nt "$PLUGINS_DIR" ]; then
  echo "==> Extracting plugins..."
  rm -rf "$PLUGINS_DIR"
  mkdir -p "$PLUGINS_DIR/tmp"
  unzip -q "$PLUGINS_ZIP" -d "$PLUGINS_DIR/tmp"
  mv "$PLUGINS_DIR/tmp/plugins/"* "$PLUGINS_DIR/"
  rm -rf "$PLUGINS_DIR/tmp"
fi

echo "==> Starting Hop Web development server..."
echo "    Web UI:  http://localhost:8080/ui"
echo "    REST:    http://localhost:8080/hop/"
echo "    Debug:   port 5005 (JDWP)"
echo ""

# Stop and remove existing container if it exists
if docker compose -f docker-compose.dev.yml ps -q | grep -q .; then
  echo "==> Stopping and removing existing container..."
  docker compose -f docker-compose.dev.yml down --remove-orphans
fi

docker compose -f docker-compose.dev.yml up "$@"
