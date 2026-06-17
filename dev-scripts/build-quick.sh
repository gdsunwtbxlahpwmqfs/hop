#!/bin/bash
set -e

PROJECT_ROOT=$(cd "$(dirname "$0")/.." && pwd)
cd "$PROJECT_ROOT"

MONETDB_GROUP="monetdb"
MONETDB_ARTIFACT="monetdb-jdbc"
MONETDB_VERSION="12.0"

MONETDB_LOCAL_PATH="$HOME/.m2/repository/${MONETDB_GROUP}/${MONETDB_ARTIFACT}/${MONETDB_VERSION}/${MONETDB_ARTIFACT}-${MONETDB_VERSION}.jar"

if [ ! -f "$MONETDB_LOCAL_PATH" ]; then
    echo "monetdb-jdbc ${MONETDB_VERSION} not found in local Maven repository"
    echo "Downloading from Clojars repository..."
    
    TEMP_DIR=$(mktemp -d)
    cd "$TEMP_DIR"
    
    if curl -f -O "https://repo.clojars.org/${MONETDB_GROUP}/${MONETDB_ARTIFACT}/${MONETDB_VERSION}/${MONETDB_ARTIFACT}-${MONETDB_VERSION}.jar"; then
        echo "Installing to local Maven repository..."
        "$PROJECT_ROOT/mvnw" install:install-file \
            -Dfile="${MONETDB_ARTIFACT}-${MONETDB_VERSION}.jar" \
            -DgroupId="${MONETDB_GROUP}" \
            -DartifactId="${MONETDB_ARTIFACT}" \
            -Dversion="${MONETDB_VERSION}" \
            -Dpackaging=jar
        echo "Successfully installed monetdb-jdbc ${MONETDB_VERSION}"
    else
        echo "Failed to download monetdb-jdbc. Continuing build without it..."
    fi
    
    cd "$PROJECT_ROOT"
    rm -rf "$TEMP_DIR"
else
    echo "monetdb-jdbc ${MONETDB_VERSION} already present in local Maven repository"
fi

./mvnw install -Dfast-build -DskipTests "$@"