#!/bin/bash
set -euo pipefail

BASE="/Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop"
MANUAL="$BASE/docs/hop-assistant-manual"

echo "=== Phase 2: Sync remaining .hpl files to hop-assistant-manual ==="
echo ""

# Helper: copy files checking basename dedup against manual
copy_if_new() {
    local src="$1"
    local target_dir="$2"
    local bn
    bn=$(basename "$src")

    # Skip if filename already exists in any manual subdirectory
    if find "$MANUAL" -name "$bn" | grep -q .; then
        return
    fi

    local full_target="$MANUAL/$target_dir/samples"
    mkdir -p "$full_target"
    cp "$src" "$full_target/"
    echo "  COPY: $bn -> $target_dir/samples/"
}

echo "--- integration-tests/database → 数据库操作类 ---"
for f in "$BASE/integration-tests/database"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/数据库操作类"; done

echo "--- integration-tests/cratedb → 数据库操作类 ---"
for f in "$BASE/integration-tests/cratedb"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/数据库操作类"; done

echo "--- integration-tests/duckdb → 数据库操作类 ---"
for f in "$BASE/integration-tests/duckdb"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/数据库操作类"; done

echo "--- integration-tests/monetdb → 数据库操作类 ---"
for f in "$BASE/integration-tests/monetdb"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/数据库操作类"; done

echo "--- integration-tests/mongo/ → 输入类 + 批量加载类 ---"
for f in "$BASE/integration-tests/mongo/tests/mongo-insert"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/输入类"; done
for f in "$BASE/integration-tests/mongo/tests/mongo-delete"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/批量加载类"; done
for f in "$BASE/integration-tests/mongo/tests/mongo-update"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/批量加载类"; done
for f in "$BASE/integration-tests/mongo/tests/mongo-uuid"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/批量加载类"; done
for f in "$BASE/integration-tests/mongo/tests/mongo-json"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/输入类"; done
for f in "$BASE/integration-tests/mongo/tests/mongo-execMql"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/数据库操作类"; done
for f in "$BASE/integration-tests/mongo/tests/mdi-tests"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/映射与子管道类"; done
for f in "$BASE/integration-tests/mongo/tests/shared"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/批量加载类"; done

echo "--- integration-tests/cassandra/ → 输入类 ---"
for f in "$BASE/integration-tests/cassandra/tests/cassandra-input-output"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/输入类"; done
for f in "$BASE/integration-tests/cassandra/tests/shared"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/输入类"; done

echo "--- integration-tests/vertica → 批量加载类 ---"
for f in "$BASE/integration-tests/vertica"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/批量加载类"; done

echo "--- integration-tests/opensearch → 输出类 ---"
for f in "$BASE/integration-tests/opensearch"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/输出类"; done

echo "--- integration-tests/kafka → 消息队列类 ---"
for f in "$BASE/integration-tests/kafka"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/消息队列类"; done

echo "--- integration-tests/json → 输入类/输出类 ---"
for f in "$BASE/integration-tests/json"/*.hpl; do
    [ -f "$f" ] || continue
    bn=$(basename "$f")
    case "$bn" in
        *output*) copy_if_new "$f" "03-转换插件/输出类" ;;
        *normalize*) copy_if_new "$f" "03-转换插件/数据验证与质量类" ;;
        *) copy_if_new "$f" "03-转换插件/输入类" ;;
    esac
done

echo "--- integration-tests/xml → 输出类/输入类 ---"
for f in "$BASE/integration-tests/xml"/*.hpl; do
    [ -f "$f" ] || continue
    bn=$(basename "$f")
    case "$bn" in
        *get-data*|*input*) copy_if_new "$f" "03-转换插件/输入类" ;;
        *) copy_if_new "$f" "03-转换插件/输出类" ;;
    esac
done

echo "--- integration-tests/neo4j/ → Neo4j图数据库类 (03) + Neo4j操作类 (04) ---"
for f in "$BASE/integration-tests/neo4j/tests"/*/; do
    [ -d "$f" ] || continue
    testname=$(basename "$f")
    case "$testname" in
        neo4j-action-*) for h in "$f"/*.hpl; do [ -f "$h" ] && copy_if_new "$h" "04-动作插件/Neo4j操作类"; done ;;
        *) for h in "$f"/*.hpl; do [ -f "$h" ] && copy_if_new "$h" "03-转换插件/Neo4j图数据库类"; done ;;
    esac
done
for f in "$BASE/integration-tests/neo4j"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/Neo4j图数据库类"; done
# shared/remove-everything
for f in "$BASE/integration-tests/neo4j/tests/shared/remove-everything"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/Neo4j图数据库类"; done

echo "--- integration-tests/ldap/ → 输入类 + 输出类 ---"
for f in "$BASE/integration-tests/ldap/input"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/输入类"; done
for f in "$BASE/integration-tests/ldap/output"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/输出类"; done

echo "--- integration-tests/http → 网络与服务类 ---"
for f in "$BASE/integration-tests/http"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/网络与服务类"; done

echo "--- integration-tests/mail → 网络与服务类 ---"
for f in "$BASE/integration-tests/mail"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/网络与服务类"; done

echo "--- integration-tests/sftp → 网络与服务类 ---"
for f in "$BASE/integration-tests/sftp"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/网络与服务类"; done

echo "--- integration-tests/ssh → 网络与服务类 ---"
for f in "$BASE/integration-tests/ssh"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/网络与服务类"; done

echo "--- integration-tests/azure → 云服务类 ---"
for f in "$BASE/integration-tests/azure"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/云服务类"; done

echo "--- integration-tests/gcp → 云服务类 ---"
for f in "$BASE/integration-tests/gcp"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/云服务类"; done

echo "--- integration-tests/minio → 文件与编码操作类 ---"
for f in "$BASE/integration-tests/minio"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/文件与编码操作类"; done

echo "--- integration-tests/spreadsheet → 输入类 ---"
for f in "$BASE/integration-tests/spreadsheet"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/输入类"; done

echo "--- integration-tests/parameters_and_variables → 计算与字段操作类 ---"
for f in "$BASE/integration-tests/parameters_and_variables"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/计算与字段操作类"; done

echo "--- integration-tests/partitioning → 流程控制类 ---"
for f in "$BASE/integration-tests/partitioning"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/流程控制类"; done

echo "--- integration-tests/sort_and_unique → 统计与分组类 ---"
for f in "$BASE/integration-tests/sort_and_unique"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/统计与分组类"; done

echo "--- integration-tests/spark → Beam大数据类 ---"
for f in "$BASE/integration-tests/spark"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/Beam大数据类"; done

echo "--- integration-tests/beam_directrunner → Beam大数据类 ---"
for f in "$BASE/integration-tests/beam_directrunner"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/Beam大数据类"; done

echo "--- integration-tests/flink → Beam大数据类 ---"
for f in "$BASE/integration-tests/flink"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/Beam大数据类"; done

echo "--- integration-tests/scripting → 脚本与编程类 ---"
for f in "$BASE/integration-tests/scripting"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/脚本与编程类"; done

echo "--- integration-tests/resolver → 06-元数据类型 ---"
for f in "$BASE/integration-tests/resolver"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "06-元数据类型"; done

echo "--- integration-tests/aes → 09-密码插件 ---"
for f in "$BASE/integration-tests/aes"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "09-密码插件"; done

echo "--- integration-tests/mdi → 映射与子管道类 ---"
for f in "$BASE/integration-tests/mdi"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/映射与子管道类"; done

echo "--- integration-tests/actions → 04-动作插件 ---"
for f in "$BASE/integration-tests/actions"/*.hpl; do
    [ -f "$f" ] || continue
    bn=$(basename "$f")
    case "$bn" in
        *repeat*|*loop*|*abort*|*success*|*wait*|*start*|*hop*)
            copy_if_new "$f" "04-动作插件/工作流控制类" ;;
        *ftp*|*sftp*|*scp*|*http*|*ssh*|*mail*|*ping*|*telnet*)
            copy_if_new "$f" "04-动作插件/网络与通信类" ;;
        *sql*|*table*|*database*|*truncate*)
            copy_if_new "$f" "04-动作插件/数据库操作类" ;;
        *file*|*copy*|*move*|*delete*|*zip*|*compress*)
            copy_if_new "$f" "04-动作插件/文件操作类" ;;
        *script*|*shell*|*eval*|*javascript*)
            copy_if_new "$f" "04-动作插件/脚本与评估类" ;;
        *snmp*|*nagios*|*log*|*monitor*)
            copy_if_new "$f" "04-动作插件/消息与监控类" ;;
        *xml*|*xsd*|*xsl*|*dtd*|*validate*)
            copy_if_new "$f" "04-动作插件/XML与验证类" ;;
        *pgp*|*encrypt*|*decrypt*|*sign*)
            copy_if_new "$f" "04-动作插件/PGP加密类" ;;
        *neo4j*|*cypher*|*graph*)
            copy_if_new "$f" "04-动作插件/Neo4j操作类" ;;
        *)  copy_if_new "$f" "04-动作插件/其他动作" ;;
    esac
done

echo "--- integration-tests/deserialize → 输入类 ---"
for f in "$BASE/integration-tests/deserialize"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件/输入类"; done

echo "--- integration-tests/hop_server → 03-转换插件 ---"
for f in "$BASE/integration-tests/hop_server"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件"; done

echo "--- integration-tests/samples → 03-转换插件 ---"
for f in "$BASE/integration-tests/samples"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件"; done

echo "--- integration-tests/snmp → 04-动作插件/消息与监控类 ---"
for f in "$BASE/integration-tests/snmp"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "04-动作插件/消息与监控类"; done

echo "--- integration-tests/transforms → 03-转换插件 (按文件名分类) ---"
for f in "$BASE/integration-tests/transforms"/*.hpl; do
    [ -f "$f" ] || continue
    bn=$(basename "$f")
    case "$bn" in
        # 输入类
        0013-row-generator*|0030-data-grid*|0035-get-variables*|0038-getfilenames*|0039-csv-input*|0040-text-file-input*|0041-properties*|0053-excel-input*|0059-get-files-rows*|0060-get-subfolder*|0063-generate-random*|0070-excel-input*|0072-read-parquet*|0085-data-set-input*|0100-*-input*|0100-load-file-content*|0101-check-ods*)
            copy_if_new "$f" "03-转换插件/输入类" ;;
        # 输出类
        0065-create-excel*|0065-check-excel*|0073-json-output*|0094-xml-output*|0101-create-ods*)
            copy_if_new "$f" "03-转换插件/输出类" ;;
        # 数据库操作类
        0017-database-lookup*|0080-duplicate-field*)
            copy_if_new "$f" "03-转换插件/数据库操作类" ;;
        # 流程控制类
        0009-blocking*|0010-blocking*|0036-rows-filter*|0057-filter-rows*|0058-flattener*|0079-delay-rows*|0081-copy-rows*|0082-repeat*)
            copy_if_new "$f" "03-转换插件/流程控制类" ;;
        # 统计与分组类
        0006-groupby*|0007-analytic-query*|0023-number-range*|0034-unique-rows*|0090-sort-rows*|0102-sorted-merge*)
            copy_if_new "$f" "03-转换插件/统计与分组类" ;;
        # 字符串与文本处理类
        0003-field-splitter*|0004-concat-fields*|0019-split-field*|0026-replace-in-string*|0031-stringcut*|0042-formula*|0054-split-fields*|0086-string-operations*|0089-regex*)
            copy_if_new "$f" "03-转换插件/字符串与文本处理类" ;;
        # 计算与字段操作类
        0001-add-sequence*|0011-calculator*|0015-add-checksum*|0016-coalesce*|0024-if-null*|0025-null-if*|0028-set-field*|0032-fields-change*|0043-checksum*|0064-set-variables*|0074-select-values*|0074-selet-values*|0076-concat-fields*|0078-add-snowflake*)
            copy_if_new "$f" "03-转换插件/计算与字段操作类" ;;
        # 脚本与编程类
        0046-java-filter*|0066-script-*|0069-user-defined*|0071-concurrent*|0087-javascript*)
            copy_if_new "$f" "03-转换插件/脚本与编程类" ;;
        # 映射与子管道类
        0002-pipeline-executor*|0002-repeating*|0036-actionpipeline*|0048-simple-mapping*|0083-workflow-executor*|0097-pipeline-executor*|0098-pipeline-executor*)
            copy_if_new "$f" "03-转换插件/映射与子管道类" ;;
        # 查找与连接类
        0014-merge-join*|0022-stream-lookup*|0044-join-rows*|0047-multiway-merge*|0077-merge-rows*)
            copy_if_new "$f" "03-转换插件/查找与连接类" ;;
        # 数据验证与质量类
        0012-fuzzymatch*|0021-row-denormaliser*|0020-row-normaliser*|0045-value-mapper*|0051-creditcard*|0067-data-validator*|0070-schema-mapping*|0091-stream-schema*)
            copy_if_new "$f" "03-转换插件/数据验证与质量类" ;;
        # 文件与编码操作类
        0037-apache-tika*|0055-file-metadata*|0056-result-files*)
            copy_if_new "$f" "03-转换插件/文件与编码操作类" ;;
        # Avro
        0018-avro*)
            copy_if_new "$f" "03-转换插件/Avro数据格式类" ;;
        # Parquet (输入/输出)
        0029-parquet-input*)
            copy_if_new "$f" "03-转换插件/输入类" ;;
        0029-parquet-output*|0072-prepare-parquet*)
            copy_if_new "$f" "03-转换插件/输出类" ;;
        # 网络与服务类
        0062-execute-process*)
            copy_if_new "$f" "03-转换插件/网络与服务类" ;;
        # 日志与监控类
        0005-metastructure*)
            copy_if_new "$f" "03-转换插件/日志与监控类" ;;
        # 剩余归到通用
        0050-cube*|0052-edi*|0061-reservoir*|0084-string-to*|0088-yaml*|0094-token*|0095-data-stream*|0096-data-stream*|0099-standardize*|run-all*)
            copy_if_new "$f" "03-转换插件" ;;
        *)
            copy_if_new "$f" "03-转换插件" ;;
    esac
done

echo "--- plugins non-samples files ---"
# repeat demo
for f in "$BASE/plugins/actions/repeat/src/main/resources/demo"/*.hpl; do
    [ -f "$f" ] && copy_if_new "$f" "04-动作插件/工作流控制类"
done

# pipeline executor test resource
for f in "$BASE/plugins/transforms/pipelineexecutor/src/test/resources"/*/*.hpl; do
    [ -f "$f" ] && copy_if_new "$f" "03-转换插件/映射与子管道类"
done
# also check nested
for f in "$BASE/plugins/transforms/pipelineexecutor/src/test/resources"/*/*/*.hpl; do
    [ -f "$f" ] && copy_if_new "$f" "03-转换插件/映射与子管道类"
done

# row generator test resource
for f in "$BASE/plugins/transforms/rowgenerator/src/test/resources"/*/*.hpl; do
    [ -f "$f" ] && copy_if_new "$f" "03-转换插件/输入类"
done

# formula build resources (utilities, not samples - skip)
# git test resources (git operations test, not pipeline samples - skip)

# pipeline/ test resources (if they exist)
for f in "$BASE/pipeline"/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件"; done
for f in "$BASE/pipeline"/*/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件"; done
for f in "$BASE/pipeline"/*/*/*.hpl; do [ -f "$f" ] && copy_if_new "$f" "03-转换插件"; done

echo ""
echo "=== Done! ==="
