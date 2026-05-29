#!/bin/bash
set -e
cd "$(dirname "$0")/.."
./mvnw install -Dfast-build -DskipTests "$@"
