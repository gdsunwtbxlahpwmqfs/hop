#!/bin/bash
set -euo pipefail

BASE="/Users/yixiao/Documents/a723wlkjgzs_code/mygithub/hop"
MANUAL="$BASE/docs/hop-assistant-manual"

echo "=== Copying .hpl sample files to hop-assistant-manual ==="
echo ""

# === Define category mapping: source glob -> target category subdirectory ===

# Each line: SOURCE_PATTERN  TARGET_DIR
# SOURCE_PATTERN is relative to BASE

copy_samples() {
    local src_pattern="$1"
    local target_dir="$2"
    local full_target="$MANUAL/$target_dir/samples"

    mkdir -p "$full_target"

    # Expand the glob pattern to find files
    for f in $BASE/$src_pattern; do
        if [ -f "$f" ]; then
            cp "$f" "$full_target/"
            echo "  COPY: $(basename $f) -> $target_dir/samples/"
        fi
    done
}

copy_samples_nested() {
    local src_pattern="$1"
    local target_dir="$2"
    local strip_prefix="$3"

    mkdir -p "$MANUAL/$target_dir/samples"

    for f in $BASE/$src_pattern; do
        if [ -f "$f" ]; then
            # Get relative path after strip_prefix
            local rel_path="${f#$BASE/$strip_prefix}"
            local dest="$MANUAL/$target_dir/samples/$rel_path"
            mkdir -p "$(dirname "$dest")"
            cp "$f" "$dest"
            echo "  COPY: $rel_path -> $target_dir/samples/"
        fi
    done
}

echo "--- 03-转换插件/输入类 ---"
copy_samples "plugins/transforms/tableinput/src/main/samples/transforms/*.hpl"          "03-转换插件/输入类"
copy_samples "plugins/transforms/getvariable/src/main/samples/transforms/*.hpl"         "03-转换插件/输入类"
copy_samples "plugins/transforms/systemdata/src/main/samples/transforms/*.hpl"         "03-转换插件/输入类"
copy_samples "plugins/transforms/fake/src/main/samples/transforms/*.hpl"               "03-转换插件/输入类"
copy_samples "plugins/transforms/getfilenames/src/main/samples/transforms/*.hpl"       "03-转换插件/输入类"
copy_samples "plugins/transforms/textfile/src/main/samples/transforms/csvinput*.hpl"   "03-转换插件/输入类"
copy_samples "plugins/transforms/textfile/src/main/samples/transforms/textfileinput*.hpl" "03-转换插件/输入类"
copy_samples "plugins/transforms/textfile/src/main/samples/workflows/csvinput*.hpl"    "03-转换插件/输入类"
copy_samples "plugins/transforms/json/src/main/samples/transforms/json-input*.hpl"     "03-转换插件/输入类"
copy_samples "plugins/transforms/xml/src/main/samples/transforms/get-data-from-xml*.hpl" "03-转换插件/输入类"

echo ""
echo "--- 03-转换插件/输出类 ---"
copy_samples "plugins/transforms/tableoutput/src/main/samples/transforms/*.hpl"         "03-转换插件/输出类"
copy_samples "plugins/transforms/textfile/src/main/samples/transforms/textfileoutput*.hpl" "03-转换插件/输出类"
copy_samples "plugins/transforms/textfile/src/main/samples/workflows/textfileoutput*.hpl" "03-转换插件/输出类"
copy_samples "plugins/transforms/json/src/main/samples/transforms/json-output*.hpl"     "03-转换插件/输出类"
copy_samples "plugins/transforms/xml/src/main/samples/transforms/add-xml*.hpl"         "03-转换插件/输出类"
copy_samples "plugins/transforms/xml/src/main/samples/transforms/xml-output*.hpl"      "03-转换插件/输出类"

echo ""
echo "--- 03-转换插件/数据库操作类 ---"
copy_samples "plugins/transforms/databaselookup/src/main/samples/transforms/*.hpl"      "03-转换插件/数据库操作类"
copy_samples "plugins/transforms/databasejoin/src/main/samples/transforms/*.hpl"        "03-转换插件/数据库操作类"
copy_samples "plugins/transforms/insertupdate/src/main/samples/transforms/*.hpl"        "03-转换插件/数据库操作类"
copy_samples "plugins/transforms/tablecompare/src/main/samples/transforms/*.hpl"        "03-转换插件/数据库操作类"
copy_samples "plugins/transforms/columnexists/src/main/samples/transforms/*.hpl"        "03-转换插件/数据库操作类"
copy_samples "plugins/transforms/dynamicsqlrow/src/main/samples/transforms/*.hpl"       "03-转换插件/数据库操作类"
copy_samples "plugins/transforms/dimensionlookup/src/main/samples/transforms/*.hpl"     "03-转换插件/数据库操作类"

echo ""
echo "--- 03-转换插件/批量加载类 ---"
copy_samples "plugins/transforms/verticabulkloader/src/main/samples/transforms/*.hpl"   "03-转换插件/批量加载类"

echo ""
echo "--- 03-转换插件/流程控制类 ---"
copy_samples "plugins/transforms/abort/src/main/samples/transforms/*.hpl"               "03-转换插件/流程控制类"
copy_samples "plugins/transforms/filterrows/src/main/samples/transforms/*.hpl"          "03-转换插件/流程控制类"
copy_samples "plugins/transforms/blockingtransform/src/main/samples/transforms/*.hpl"   "03-转换插件/流程控制类"
copy_samples "plugins/transforms/blockuntiltransformsfinish/src/main/samples/transforms/*.hpl" "03-转换插件/流程控制类"
copy_samples "plugins/transforms/clonerow/src/main/samples/transforms/*.hpl"            "03-转换插件/流程控制类"
copy_samples "plugins/transforms/append/src/main/samples/transforms/*.hpl"              "03-转换插件/流程控制类"
copy_samples "plugins/transforms/switchcase/src/main/samples/transforms/*.hpl"          "03-转换插件/流程控制类"
copy_samples "plugins/transforms/detectemptystream/src/main/samples/transforms/*.hpl"   "03-转换插件/流程控制类"
copy_samples "plugins/transforms/detectlastrow/src/main/samples/transforms/*.hpl"       "03-转换插件/流程控制类"
copy_samples "plugins/transforms/delay/src/main/samples/transforms/*.hpl"               "03-转换插件/流程控制类"

echo ""
echo "--- 03-转换插件/查找与连接类 ---"
copy_samples "plugins/transforms/streamlookup/src/main/samples/transforms/*.hpl"        "03-转换插件/查找与连接类"
copy_samples "plugins/transforms/mergejoin/src/main/samples/transforms/*.hpl"           "03-转换插件/查找与连接类"
copy_samples "plugins/transforms/joinrows/src/main/samples/transforms/*.hpl"            "03-转换插件/查找与连接类"
copy_samples "plugins/transforms/closure/src/main/samples/transforms/*.hpl"             "03-转换插件/查找与连接类"
copy_samples "plugins/transforms/fuzzymatch/src/main/samples/transforms/*.hpl"          "03-转换插件/查找与连接类"

echo ""
echo "--- 03-转换插件/统计与分组类 ---"
copy_samples "plugins/transforms/groupby/src/main/samples/transforms/*.hpl"             "03-转换插件/统计与分组类"
copy_samples "plugins/transforms/analyticquery/src/main/samples/transforms/*.hpl"       "03-转换插件/统计与分组类"
copy_samples "plugins/transforms/numberrange/src/main/samples/transforms/*.hpl"         "03-转换插件/统计与分组类"
copy_samples "plugins/transforms/uniquerows/src/main/samples/transforms/*.hpl"          "03-转换插件/统计与分组类"
copy_samples "plugins/transforms/sort/src/main/samples/transforms/*.hpl"                "03-转换插件/统计与分组类"
copy_samples "plugins/transforms/valuemapper/src/main/samples/transforms/*.hpl"         "03-转换插件/统计与分组类"

echo ""
echo "--- 03-转换插件/字符串与文本处理类 ---"
copy_samples "plugins/transforms/fieldsplitter/src/main/samples/transforms/*.hpl"       "03-转换插件/字符串与文本处理类"
copy_samples "plugins/transforms/concatfields/src/main/samples/transforms/*.hpl"        "03-转换插件/字符串与文本处理类"
copy_samples "plugins/transforms/stringoperations/src/main/samples/transforms/*.hpl"    "03-转换插件/字符串与文本处理类"
copy_samples "plugins/transforms/nullif/src/main/samples/transforms/*.hpl"              "03-转换插件/字符串与文本处理类"
copy_samples "plugins/transforms/formula/src/main/samples/transforms/*.hpl"             "03-转换插件/字符串与文本处理类"
copy_samples "plugins/transforms/html2text/src/main/samples/transforms/*.hpl"           "03-转换插件/字符串与文本处理类"
copy_samples "plugins/transforms/detectlanguage/src/main/samples/transforms/*.hpl"      "03-转换插件/字符串与文本处理类"
copy_samples "plugins/transforms/creditcardvalidator/src/main/samples/transforms/*.hpl" "03-转换插件/字符串与文本处理类"
copy_samples "plugins/transforms/standardizephonenumber/src/main/samples/transforms/*.hpl" "03-转换插件/字符串与文本处理类"
copy_samples "plugins/transforms/stanfordnlp/src/main/samples/transforms/*.hpl"         "03-转换插件/字符串与文本处理类"
copy_samples "plugins/transforms/tika/src/main/samples/transforms/*.hpl"                "03-转换插件/字符串与文本处理类"

echo ""
echo "--- 03-转换插件/计算与字段操作类 ---"
copy_samples "plugins/transforms/calculator/src/main/samples/transforms/*.hpl"          "03-转换插件/计算与字段操作类"
copy_samples "plugins/transforms/addsequence/src/main/samples/transforms/*.hpl"         "03-转换插件/计算与字段操作类"
copy_samples "plugins/transforms/constant/src/main/samples/transforms/*.hpl"            "03-转换插件/计算与字段操作类"
copy_samples "plugins/transforms/selectvalues/src/main/samples/transforms/*.hpl"        "03-转换插件/计算与字段操作类"
copy_samples "plugins/transforms/fieldschangesequence/src/main/samples/transforms/*.hpl" "03-转换插件/计算与字段操作类"
copy_samples "plugins/transforms/checksum/src/main/samples/transforms/*.hpl"            "03-转换插件/计算与字段操作类"
copy_samples "plugins/transforms/coalesce/src/main/samples/transforms/*.hpl"            "03-转换插件/计算与字段操作类"
copy_samples "plugins/transforms/ifnull/src/main/samples/transforms/*.hpl"              "03-转换插件/计算与字段操作类"

echo ""
echo "--- 03-转换插件/脚本与编程类 ---"
copy_samples "plugins/transforms/javascript/src/main/samples/transforms/*.hpl"          "03-转换插件/脚本与编程类"
copy_samples "plugins/transforms/janino/src/main/samples/transforms/*.hpl"              "03-转换插件/脚本与编程类"
copy_samples "plugins/transforms/drools/src/main/samples/transforms/*.hpl"              "03-转换插件/脚本与编程类"

echo ""
echo "--- 03-转换插件/映射与子管道类 ---"
copy_samples "plugins/transforms/mapping/src/main/samples/transforms/*.hpl"             "03-转换插件/映射与子管道类"
copy_samples "plugins/transforms/pipelineexecutor/src/main/samples/transforms/*.hpl"    "03-转换插件/映射与子管道类"
copy_samples "plugins/transforms/workflowexecutor/src/main/samples/transforms/*.hpl"    "03-转换插件/映射与子管道类"
# metainject (metadata injection) belongs to mapping-related
copy_samples "plugins/transforms/metainject/src/main/samples/metadata-injection/*.hpl"  "03-转换插件/映射与子管道类"
# filterrows MDI samples
copy_samples "plugins/transforms/filterrows/src/main/samples/metadata-injection/*.hpl"  "03-转换插件/映射与子管道类"
# assemblies loop samples (demonstrate pipeline/workflow executor)
copy_samples "assemblies/static/src/main/resources/config/projects/samples/loops/*.hpl" "03-转换插件/映射与子管道类"

echo ""
echo "--- 03-转换插件/消息队列类 ---"
copy_samples "plugins/transforms/kafka/src/main/samples/transforms/*.hpl"               "03-转换插件/消息队列类"

echo ""
echo "--- 03-转换插件/网络与服务类 ---"
copy_samples "plugins/transforms/ssh/src/main/samples/transforms/*.hpl"                 "03-转换插件/网络与服务类"
copy_samples "plugins/transforms/webserviceavailable/src/main/samples/transforms/*.hpl" "03-转换插件/网络与服务类"

echo ""
echo "--- 03-转换插件/Neo4j图数据库类 ---"
copy_samples "plugins/tech/neo4j/src/main/samples/neo4j/*.hpl"                          "03-转换插件/Neo4j图数据库类"

echo ""
echo "--- 03-转换插件/Avro数据格式类 ---"
copy_samples "plugins/tech/avro/src/main/samples/transforms/*.hpl"                      "03-转换插件/Avro数据格式类"

echo ""
echo "--- 03-转换插件/Beam大数据类 ---"
copy_samples "plugins/engines/beam/src/main/samples/beam/pipelines/*.hpl"               "03-转换插件/Beam大数据类"

echo ""
echo "--- 03-转换插件/日志与监控类 ---"
copy_samples "plugins/misc/reflection/src/main/samples/reflection/*.hpl"                "03-转换插件/日志与监控类"

echo ""
echo "--- 03-转换插件/数据验证与质量类 ---"
copy_samples "plugins/transforms/schemamapping/src/main/samples/transforms/*.hpl"       "03-转换插件/数据验证与质量类"
copy_samples "plugins/transforms/streamschemamerge/src/main/samples/transforms/*.hpl"   "03-转换插件/数据验证与质量类"
copy_samples "plugins/transforms/json/src/main/samples/transforms/json-normalize*.hpl"  "03-转换插件/数据验证与质量类"

echo ""
echo "--- 03-转换插件/AI与语言模型类 ---"
# Preserve subdirectory structure (fact-checking/, zero-shot/)
copy_samples_nested \
    "plugins/transforms/languagemodelchat/src/main/samples/transforms/languagemodelchat/*/*.hpl" \
    "03-转换插件/AI与语言模型类" \
    "plugins/transforms/languagemodelchat/src/main/samples/transforms/"

echo ""
echo "--- 03-转换插件/文件与编码操作类 ---"
copy_samples "plugins/transforms/zipfile/src/main/samples/transforms/*.hpl"             "03-转换插件/文件与编码操作类"
copy_samples "plugins/transforms/fileexists/src/main/samples/transforms/*.hpl"          "03-转换插件/文件与编码操作类"
copy_samples "plugins/transforms/filelocked/src/main/samples/transforms/*.hpl"          "03-转换插件/文件与编码操作类"

echo ""
echo "--- 04-动作插件/工作流控制类 ---"
copy_samples "plugins/actions/repeat/src/main/samples/actions/*.hpl"                    "04-动作插件/工作流控制类"

echo ""
echo "--- 06-元数据类型 ---"
copy_samples "plugins/resolvers/pipeline/src/main/samples/variable_resolvers/*.hpl"     "06-元数据类型"

echo ""
echo "--- 03-转换插件 (通用示例) ---"
copy_samples "assemblies/static/src/main/resources/config/projects/samples/pipelines/*.hpl" "03-转换插件"
copy_samples "assemblies/static/src/main/resources/config/projects/samples/workflows/parallel/*.hpl" "03-转换插件/流程控制类"

echo ""
echo "=== Done! ==="
