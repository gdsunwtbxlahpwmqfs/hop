#!/bin/bash
#
# Download JDBC drivers that are not included in Apache Hop distribution
# due to licensing restrictions (GPL, proprietary, etc.)
#
# These drivers should be placed in the HOP_SHARED_JDBC_FOLDERS directory
# and the HOP_SHARED_JDBC_FOLDERS environment variable should point to this directory.
#
# Usage:
#   ./download_shared_jars.sh
#
# For drivers that need to be downloaded from vendor websites (not Maven),
# please refer to todo_list.md
#

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
TARGET_DIR="${SCRIPT_DIR}/jdbc"

# Maven Central base URL
MAVEN_BASE="https://repo1.maven.org/maven2"

# Create target directory
mkdir -p "${TARGET_DIR}"

echo "========================================"
echo "Apache Hop JDBC Driver Downloader"
echo "========================================"
echo ""
echo "Target directory: ${TARGET_DIR}"
echo ""

# ============================================================
# Step 1: Download drivers from Maven Central
# ============================================================
# Format: "groupId:artifactId:version:description"
MAVEN_DRIVERS=(
    # MySQL Connector/J 8+ (GPL License - scope=provided in Hop)
    "com.mysql:mysql-connector-j:9.1.0:MySQL Connector/J (GPL, MySQL 8+)"

    # MySQL Connector/J 5.x (for older MySQL versions)
    "mysql:mysql-connector-java:5.1.49:MySQL Connector/J 5.x (GPL, MySQL 5.x)"

    # MariaDB Connector/J (LGPL License)
    "org.mariadb.jdbc:mariadb-java-client:3.5.2:MariaDB Connector/J (LGPL)"

    # Snowflake JDBC Driver
    "net.snowflake:snowflake-jdbc:4.2.0:Snowflake JDBC Driver"

    # Bouncy Castle - Required for Snowflake certificate-based authentication
    "org.bouncycastle:bcprov-jdk18on:1.79:Bouncy Castle Provider"
    "org.bouncycastle:bcpkix-jdk18on:1.79:Bouncy Castle PKIX"

    # Sybase ASE / MS SQL Server (JTDS - open source, LGPL)
    "net.sourceforge.jtds:jtds:1.3.1:jTDS JDBC Driver (Sybase ASE, MS SQL Server)"

    # DB2 JDBC (IBM JCC driver)
    "com.ibm.db2:jcc:11.5.9.0:IBM Data Server Driver for JDBC (DB2)"

    # Vertica JDBC
    "com.vertica.jdbc:vertica-jdbc:25.3.0-0:Vertica JDBC Driver"

    # ClickHouse JDBC
    "com.clickhouse:clickhouse-jdbc:0.6.0:ClickHouse JDBC Driver"

    # H2 Database (for Generic database testing)
    "com.h2database:h2:2.2.224:H2 Database Engine"

    # SQLite JDBC (for Generic database)
    "org.xerial:sqlite-jdbc:3.45.3.0:SQLite JDBC Driver"

    # PostgreSQL JDBC (included for completeness)
    "org.postgresql:postgresql:42.7.3:PostgreSQL JDBC Driver"

    # Apache Derby (for Generic database testing)
    "org.apache.derby:derby:10.17.1.0:Apache Derby"
)

download_maven_driver() {
    local groupId="$1"
    local artifactId="$2"
    local version="$3"
    local description="$4"

    local groupPath
    groupPath=$(echo "$groupId" | sed 's/\./\//g')
    local jarUrl="${MAVEN_BASE}/${groupPath}/${artifactId}/${version}/${artifactId}-${version}.jar"
    local jarFile="${TARGET_DIR}/${artifactId}-${version}.jar"

    echo "Downloading from Maven: ${description}"
    echo "  URL: ${jarUrl}"

    if [[ -f "${jarFile}" ]]; then
        echo "  [SKIP] Already exists"
        echo ""
        return 0
    fi

    if curl -fsSL -o "${jarFile}" "${jarUrl}"; then
        echo "  [OK] Downloaded"
    else
        echo "  [ERROR] Failed to download from Maven"
        return 1
    fi
    echo ""
}

# ============================================================
# Step 2: Download drivers from vendor websites (direct URLs)
# ============================================================
# Format: "url|filename|description" (use | as separator since URL contains :)
URL_DRIVERS=(
    # Oracle JDBC 23ai (ojdbc11 - JDK11/21 compatible)
    # Oracle provides direct download links without login
    "https://download.oracle.com/otn-pub/otn_software/jdbc/238/ojdbc11.jar|ojdbc11-23.8.0.25.04.jar|Oracle JDBC Driver (ojdbc11 23ai)"

    # Oracle JDBC 21c (ojdbc11 - JDK11/21 compatible, alternative URL)
    "https://download.oracle.com/otn-pub/otn_software/jdbc/213/ojdbc11.jar|ojdbc11-21.3.0.0.jar|Oracle JDBC Driver (ojdbc11 21c)"

    # Oracle JDBC 19c (ojdbc8 - JDK8/11 compatible)
    "https://download.oracle.com/otn-pub/otn_software/jdbc/1918/ojdbc8.jar|ojdbc8-19.18.0.0.jar|Oracle JDBC Driver (ojdbc8 19c)"
)

download_url_driver() {
    local url="$1"
    local filename="$2"
    local description="$3"

    local jarFile="${TARGET_DIR}/${filename}"

    echo "Downloading from vendor: ${description}"
    echo "  URL: ${url}"

    if [[ -f "${jarFile}" ]]; then
        echo "  [SKIP] Already exists"
        echo ""
        return 0
    fi

    if curl -fL -o "${jarFile}" "${url}"; then
        echo "  [OK] Downloaded"
    else
        echo "  [WARN] Failed to download from vendor"
        return 1
    fi
    echo ""
}

# ============================================================
# Main execution
# ============================================================
failed=0

echo "========================================"
echo "Step 1/2: Downloading from Maven Central"
echo "========================================"
echo ""

for driver in "${MAVEN_DRIVERS[@]}"; do
    IFS=':' read -r groupId artifactId version description <<< "$driver"
    if ! download_maven_driver "$groupId" "$artifactId" "$version" "$description"; then
        ((failed++))
    fi
done

echo "========================================"
echo "Step 2/2: Downloading from vendor websites"
echo "========================================"
echo ""

for driver in "${URL_DRIVERS[@]}"; do
    IFS='|' read -r url filename description <<< "$driver"
    if ! download_url_driver "$url" "$filename" "$description"; then
        ((failed++))
    fi
done

# ============================================================
# Summary
# ============================================================
echo "========================================"
echo "Download Summary"
echo "========================================"

# List of drivers requiring manual download
MANUAL_DRIVERS=(
    "Google BigQuery - Simba JDBC Driver"
    "Teradata JDBC Driver"
    "Oracle RDB - Oracle RDB JDBC Driver (oracle.rdb.jdbc.rdbThin.Driver)"
)

if [[ $failed -eq 0 ]]; then
    echo "All automated downloads completed successfully!"
else
    echo "Warning: ${failed} driver(s) failed to download"
fi

echo ""
echo "Driver files location: ${TARGET_DIR}"
echo ""
echo "To use these drivers with Docker:"
echo "  - Set HOP_SHARED_JDBC_FOLDERS=/opt/hop/jdbc"
echo "  - Mount ./resources/jdbc:/opt/hop/jdbc"
echo ""
echo "Driver compatibility notes:"
echo "  - MySQL 8+: use mysql-connector-j-9.1.0.jar (driver: com.mysql.cj.jdbc.Driver)"
echo "  - MySQL 5.x: use mysql-connector-java-5.1.49.jar (driver: org.gjt.mm.mysql.Driver)"
echo "  - Oracle 19c/21c/23ai: use ojdbc8-*.jar or ojdbc11-*.jar (driver: oracle.jdbc.driver.OracleDriver)"
echo "  - Oracle RDB: requires manual download (driver: oracle.rdb.jdbc.rdbThin.Driver)"
echo "  - Generic database: use h2-*.jar, sqlite-jdbc-*.jar, or derby-*.jar as needed"
echo ""
echo "Drivers requiring manual download (see todo_list.md):"
for driver in "${MANUAL_DRIVERS[@]}"; do
    echo "  - ${driver}"
done
echo ""
echo "Downloaded files:"
echo ""
ls -lh "${TARGET_DIR}"/*.jar 2>/dev/null || echo "No JAR files found"

exit $failed
