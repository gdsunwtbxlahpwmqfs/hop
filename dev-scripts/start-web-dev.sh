#!/bin/bash
set -e
cd "$(dirname "$0")/.."

# Clean up previously extracted directories
echo "==> Cleaning up previously extracted directories..."
rm -rf "assemblies/web/target/webapp"
rm -rf "assemblies/client/target/hop"

# Check if hop.war exists
if [ ! -f "assemblies/web/target/hop.war" ]; then
  echo "==> Web assembly not found. Building first..."
  ./dev-scripts/build-web.sh
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

# Copy Hop core/beam libraries into the webapp.
# hop.war does NOT bundle hop-core/hop-engine (see docker/web.Dockerfile which does
# the same COPY). Without this, Tomcat fails with NoClassDefFoundError: HopException.
WEBAPP_LIB="assemblies/web/target/webapp/WEB-INF/lib"
CLIENT_ZIP=$(ls assemblies/client/target/hop-client-*.zip 2>/dev/null | head -1)
if [ -n "$CLIENT_ZIP" ]; then
  echo "==> Extracting core/beam libs from client assembly..."
  unzip -q -o "$CLIENT_ZIP" "hop/lib/core/*" "hop/lib/beam/*" -d assemblies/client/target/
  cp assemblies/client/target/hop/lib/core/*.jar "$WEBAPP_LIB/"
  cp assemblies/client/target/hop/lib/beam/*.jar "$WEBAPP_LIB/"
  # Remove the RCP fragment: it conflicts with hop-ui-rap (both provide
  # TextSizeUtilFacadeImpl) and references SWT methods absent in RAP.
  rm -f "$WEBAPP_LIB"/hop-ui-rcp*
else
  echo "==> Warning: client assembly zip not found; hop-core will be missing from WEB-INF/lib"
fi

# Fix SWT jar: replace macOS cocoa with Linux GTK (built on macOS, runs in Linux container)

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
PLUGINS_ZIP=$(ls assemblies/plugins/target/hop-assemblies-plugins-*-SNAPSHOT.zip 2>/dev/null | head -1)
if [ ! -d "$PLUGINS_DIR" ] || [ "$PLUGINS_ZIP" -nt "$PLUGINS_DIR" ]; then
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
