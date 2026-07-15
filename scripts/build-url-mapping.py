#!/usr/bin/env python3
"""
Phase 2: 构建 documentationUrl → 本地 MD 文件的映射查找表。
输出:
  - docs/hop-assistant-manual/url-mapping.json
  - /tmp/未匹配插件清单.md
"""
import os
import re
import json
import sys
from pathlib import Path
from collections import defaultdict
from datetime import datetime

PROJECT_ROOT = Path(__file__).resolve().parent.parent
MANUAL_DIR = PROJECT_ROOT / "docs" / "hop-assistant-manual"
OUTPUT_JSON = MANUAL_DIR / "url-mapping.json"
OUTPUT_UNMATCHED = Path("/tmp/未匹配插件清单.md")

ANNOTATION_PATTERNS = {
    "Transform": re.compile(
        r'@Transform\s*\(\s*id\s*=\s*"([^"]+)"'
        r'(?:.*?name\s*=\s*"([^"]*)")?'
        r'(?:.*?documentationUrl\s*=\s*"([^"]*)")?',
        re.DOTALL,
    ),
    "Action": re.compile(
        r'@Action\s*\(\s*id\s*=\s*"([^"]+)"'
        r'(?:.*?name\s*=\s*"([^"]*)")?'
        r'(?:.*?documentationUrl\s*=\s*"([^"]*)")?',
        re.DOTALL,
    ),
    "DatabaseMetaPlugin": re.compile(
        r'@DatabaseMetaPlugin\s*\(\s*type\s*=\s*"([^"]+)"'
        r'(?:.*?typeDescription\s*=\s*"([^"]*)")?'
        r'(?:.*?documentationUrl\s*=\s*"([^"]*)")?',
        re.DOTALL,
    ),
    "HopMetadata": re.compile(
        r'@HopMetadata\s*\('
        r'(?:.*?key\s*=\s*"([^"]*)")?'
        r'(?:.*?name\s*=\s*"([^"]*)")?'
        r'(?:.*?documentationUrl\s*=\s*"([^"]*)")?',
        re.DOTALL,
    ),
    "HopPerspectivePlugin": re.compile(
        r'@HopPerspectivePlugin\s*\('
        r'(?:.*?id\s*=\s*"([^"]*)")?'
        r'(?:.*?name\s*=\s*"([^"]*)")?'
        r'(?:.*?documentationUrl\s*=\s*"([^"]*)")?',
        re.DOTALL,
    ),
    "VariableResolverPlugin": re.compile(
        r'@VariableResolverPlugin\s*\(\s*id\s*=\s*"([^"]+)"'
        r'(?:.*?name\s*=\s*"([^"]*)")?'
        r'(?:.*?documentationUrl\s*=\s*"([^"]*)")?',
        re.DOTALL,
    ),
}

MANUAL_OVERRIDE = {
    "/pipeline/transforms/jsoninput.html": "03-转换插件/输入类/JSON输入.md",
    "/pipeline/transforms/csvinput.html": "03-转换插件/输入类/CSV文件输入.md",
    "/pipeline/transforms/tableinput.html": "03-转换插件/输入类/表输入.md",
    "/pipeline/transforms/excelinput.html": "03-转换插件/输入类/Excel输入.md",
    "/pipeline/transforms/textfileinput.html": "03-转换插件/输入类/文本文件输入.md",
    "/pipeline/transforms/xmlinput.html": "03-转换插件/输入类/XML流输入.md",
    "/pipeline/transforms/xmlinputstream.html": "03-转换插件/输入类/XML流输入.md",
    "/pipeline/transforms/ldapinput.html": "03-转换插件/输入类/LDAP输入.md",
    "/pipeline/transforms/mongodbinput.html": "03-转换插件/输入类/MongoDB输入.md",
    "/pipeline/transforms/cassandra-input.html": "03-转换插件/输入类/Cassandra输入.md",
    "/pipeline/transforms/salesforceinput.html": "03-转换插件/输入类/Salesforce输入.md",
    "/pipeline/transforms/splunkinput.html": "03-转换插件/输入类/Splunk输入.md",
    "/pipeline/transforms/googlesheets.html": "03-转换插件/输入类/GoogleSheets输入.md",
    "/pipeline/transforms/google-analytics.html": "03-转换插件/输入类/GoogleAnalytics输入.md",
    "/pipeline/transforms/parquet-file-input.html": "03-转换插件/输入类/Parquet文件输入.md",
    "/pipeline/transforms/avro-file-input.html": "03-转换插件/输入类/Avro文件输入.md",
    "/pipeline/transforms/propertiesinput.html": "03-转换插件/输入类/属性输入.md",
    "/pipeline/transforms/yamlinput.html": "03-转换插件/输入类/YAML输入.md",
    "/pipeline/transforms/sasinput.html": "03-转换插件/输入类/SAS输入.md",
    "/pipeline/transforms/jdbcmetadata.html": "03-转换插件/输入类/JDBC元数据.md",
    "/pipeline/transforms/getfilenames.html": "03-转换插件/输入类/获取文件名.md",
    "/pipeline/transforms/gettablenames.html": "03-转换插件/输入类/获取表名.md",
    "/pipeline/transforms/getsubfolders.html": "03-转换插件/输入类/获取子文件夹名.md",
    "/pipeline/transforms/getvariable.html": "03-转换插件/输入类/获取变量.md",
    "/pipeline/transforms/getsysteminfo.html": "03-转换插件/输入类/获取系统信息.md",
    "/pipeline/transforms/rowgenerator.html": "03-转换插件/输入类/行生成器.md",
    "/pipeline/transforms/fuzzymatch.html": "03-转换插件/查找与连接类/模糊匹配.md",
    "/pipeline/transforms/mergesort.html": "03-转换插件/流程控制类/排序合并.md",
    "/pipeline/transforms/sort.html": "03-转换插件/流程控制类/排序行.md",
    "/pipeline/transforms/combinationlookup.html": "03-转换插件/数据库操作类/组合查找/更新.md",
    "/pipeline/transforms/dimensionlookup.html": "03-转换插件/数据库操作类/维度查找/更新.md",
    "/pipeline/transforms/denormaliser.html": "03-转换插件/统计与分组类/行反正规化.md",
    "/pipeline/transforms/normaliser.html": "03-转换插件/统计与分组类/行正规化.md",
    "/pipeline/transforms/groupby.html": "03-转换插件/统计与分组类/分组.md",
    "/pipeline/transforms/unique.html": "03-转换插件/统计与分组类/去除重复记录.md",
    "/pipeline/transforms/aggregaterows.html": "03-转换插件/统计与分组类/聚合记录.md",
    "/pipeline/transforms/concatfields.html": "03-转换插件/字符串与文本处理类/合并字段.md",
    "/pipeline/transforms/splitfields.html": "03-转换插件/字符串与文本处理类/拆分字段.md",
    "/pipeline/transforms/replacestring.html": "03-转换插件/字符串与文本处理类/替换字符串.md",
    "/pipeline/transforms/regexevaluation.html": "03-转换插件/字符串与文本处理类/正则表达式匹配.md",
    "/pipeline/transforms/calculator.html": "03-转换插件/计算与字段操作类/计算器.md",
    "/pipeline/transforms/addconstant.html": "03-转换插件/计算与字段操作类/常量.md",
    "/pipeline/transforms/sequenc generator.html": "03-转换插件/计算与字段操作类/自动增长.md",
    "/pipeline/transforms/rowgenerator.html": "03-转换插件/输入类/行生成器.md",
    "/pipeline/transforms/mapping.html": "03-转换插件/映射与子管道类/映射.md",
    "/pipeline/transforms/mappinginput.html": "03-转换插件/映射与子管道类/映射输入.md",
    "/pipeline/transforms/mappingoutput.html": "03-转换插件/映射与子管道类/映射输出.md",
    "/pipeline/transforms/workflow.html": "03-转换插件/映射与子管道类/执行工作流.md",
    "/pipeline/transforms/pipeline.html": "03-转换插件/映射与子管道类/执行管道.md",
    "/pipeline/transforms/abort.html": "03-转换插件/流程控制类/终止记录流.md",
    "/pipeline/transforms/sample.html": "03-转换插件/流程控制类/采样行.md",
    "/pipeline/transforms/firstrows.html": "03-转换插件/流程控制类/取前N条记录.md",
    "/pipeline/transforms/lastrows.html": "03-转换插件/流程控制类/取后N条记录.md",
    "/pipeline/transforms/blockingtransform.html": "03-转换插件/流程控制类/阻塞数据转换.md",
    "/pipeline/transforms/filterrows.html": "03-转换插件/流程控制类/过滤记录.md",
    "/pipeline/transforms/switchcase.html": "03-转换插件/流程控制类/Switch/Case.md",
    "/pipeline/transforms/creditcardvalidator.html": "03-转换插件/数据验证与质量类/信用卡验证.md",
    "/pipeline/transforms/tableexists.html": "03-转换插件/数据库操作类/表是否存在.md",
    "/pipeline/transforms/columnexists.html": "03-转换插件/数据库操作类/列是否存在.md",
    "/pipeline/transforms/delete.html": "03-转换插件/数据库操作类/删除.md",
    "/pipeline/transforms/update.html": "03-转换插件/数据库操作类/更新.md",
    "/pipeline/transforms/insert.html": "03-转换插件/数据库操作类/插入/更新.md",
    "/pipeline/transforms/proc.html": "03-转换插件/数据库操作类/调用数据库存储过程.md",
    "/pipeline/transforms/databasejoin.html": "03-转换插件/数据库操作类/数据库连接.md",
    "/pipeline/transforms/databaselookup.html": "03-转换插件/数据库操作类/数据库查找.md",
    "/pipeline/transforms/dblookuptemp.html": "03-转换插件/数据库操作类/数据库查找(内存缓存).md",
    "/pipeline/transforms/symmetriccrypto.html": "03-转换插件/安全与加密类/Symmetric Crypt decrypt.md",
    "/pipeline/transforms/pgpdecrypt.html": "03-转换插件/安全与加密类/PGP解密.md",
    "/pipeline/transforms/pgpencrypt.html": "03-转换插件/安全与加密类/PGP加密.md",
    "/pipeline/transforms/rest.html": "03-转换插件/网络与服务类/REST客户端.md",
    "/pipeline/transforms/mail.html": "03-转换插件/网络与服务类/邮件.md",
    "/pipeline/transforms/mailinput.html": "03-转换插件/网络与服务类/邮件信息输入.md",
    "/pipeline/transforms/http.html": "03-转换插件/网络与服务类/HTTP客户端.md",
    "/pipeline/transforms/webserviceavailable.html": "03-转换插件/网络与服务类/Web服务可用.md",
    "/pipeline/transforms/neoodbc.html": "03-转换插件/Neo4j图数据库类/Neo4j Cypher Builder.md",
    "/pipeline/transforms/neo4j-output.html": "03-转换插件/Neo4j图数据库类/Neo4j输出.md",
    "/pipeline/transforms/avro-decode.html": "03-转换插件/Avro数据格式类/Avro Decode.md",
    "/pipeline/transforms/avro-encode.html": "03-转换插件/Avro数据格式类/Avro Encode.md",
    "/pipeline/transforms/avro-file-output.html": "03-转换插件/Avro数据格式类/Avro文件输出.md",
    "/pipeline/transforms/streamlookup.html": "03-转换插件/查找与连接类/流查询.md",
    "/pipeline/transforms/mergediff.html": "03-转换插件/查找与连接类/合并连接.md",
    "/pipeline/transforms/sortmergejoin.html": "03-转换插件/查找与连接类/排序合并连接.md",
    "/pipeline/transforms/joinrows.html": "03-转换插件/查找与连接类/笛卡尔积.md",
    "/pipeline/transforms/constant.html": "03-转换插件/计算与字段操作类/常量.md",
    "/pipeline/transforms/addchecksum.html": "03-转换插件/计算与字段操作类/校验和.md",
    "/pipeline/transforms/addsequence.html": "03-转换插件/计算与字段操作类/自动增长.md",
    "/pipeline/transforms/selectvalues.html": "03-转换插件/流程控制类/选择/移除值.md",
    "/pipeline/transforms/rowstoresult.html": "03-转换插件/流程控制类/获取记录到结果.md",
    "/pipeline/transforms/getresultfilenames.html": "03-转换插件/流程控制类/从结果获取文件名.md",
    "/pipeline/transforms/resultrowsname.html": "03-转换插件/流程控制类/从结果获取行.md",
    "/pipeline/transforms/javadoc.html": "03-转换插件/脚本与编程类/Java代码.md",
    "/pipeline/transforms/script.html": "03-转换插件/脚本与编程类/脚本.md",
    "/pipeline/transforms/javascript.html": "03-转换插件/脚本与编程类/JavaScript.md",
    "/pipeline/transforms/python.html": "03-转换插件/脚本与编程类/Python.md",
    "/pipeline/transforms/groovy.html": "03-转换插件/脚本与编程类/Groovy.md",
    "/pipeline/transforms/blockuntiltransformsfinish.html": "03-转换插件/流程控制类/等待转换完成.md",
    "/pipeline/transforms/delay.html": "03-转换插件/流程控制类/延迟.md",
    "/pipeline/transforms/clonerow.html": "03-转换插件/流程控制类/克隆行.md",
    "/pipeline/transforms/append.html": "03-转换插件/流程控制类/追加流.md",
    "/pipeline/transforms/detectemptystream.html": "03-转换插件/流程控制类/检测空流.md",
    "/pipeline/transforms/detectlastrow.html": "03-转换插件/流程控制类/识别最后一行.md",
    "/pipeline/transforms/copiers.html": "03-转换插件/流程控制类/复制记录到结果.md",
    "/pipeline/transforms/fileexists.html": "03-转换插件/文件与编码操作类/文件是否存在.md",
    "/pipeline/transforms/changeencoding.html": "03-转换插件/文件与编码操作类/文件编码转换.md",
    "/pipeline/transforms/compresswithzip.html": "03-转换插件/文件与编码操作类/解压缩.md",
    "/pipeline/transforms/xmlfileoutput.html": "03-转换插件/输出类/XML文件输出.md",
    "/pipeline/transforms/tableoutput.html": "03-转换插件/输出类/表输出.md",
    "/pipeline/transforms/accessoutput.html": "03-转换插件/输出类/MicrosoftAccess输出.md",
    "/pipeline/transforms/jsonoutput.html": "03-转换插件/输出类/JSON输出.md",
    "/pipeline/transforms/jsonoutputenhanced.html": "03-转换插件/输出类/增强JSON输出.md",
    "/pipeline/transforms/xmloutput.html": "03-转换插件/输出类/XML输出.md",
    "/pipeline/transforms/xmloutputadvanced.html": "03-转换插件/输出类/XML输出高级.md",
    "/pipeline/transforms/addxml.html": "03-转换插件/输出类/添加XML.md",
    "/pipeline/transforms/propertiesoutput.html": "03-转换插件/输出类/属性输出.md",
    "/pipeline/transforms/parquet-file-output.html": "03-转换插件/输出类/Parquet文件输出.md",
    "/pipeline/transforms/csvoutput.html": "03-转换插件/输出类/CSV文件输出.md",
    "/pipeline/transforms/textfileoutput.html": "03-转换插件/输出类/文本文件输出.md",
    "/pipeline/transforms/fixedformat.html": "03-转换插件/输出类/固定格式输出.md",
    "/workflow/actions/ftp.html": "04-动作插件/网络与通信类/FTP获取文件.md",
    "/workflow/actions/ftpput.html": "04-动作插件/网络与通信类/FTP发送文件.md",
    "/workflow/actions/sftp.html": "04-动作插件/网络与通信类/SFTP获取文件.md",
    "/workflow/actions/sftpput.html": "04-动作插件/网络与通信类/SFTP发送文件.md",
    "/workflow/actions/ftpdelete.html": "04-动作插件/网络与通信类/FTP删除文件.md",
    "/workflow/actions/http.html": "04-动作插件/网络与通信类/HTTP.md",
    "/workflow/actions/httppost.html": "04-动作插件/网络与通信类/HTTP POST.md",
    "/workflow/actions/mail.html": "04-动作插件/网络与通信类/发送邮件.md",
    "/workflow/actions/ping.html": "04-动作插件/网络与通信类/Ping.md",
    "/workflow/actions/telnet.html": "04-动作插件/网络与通信类/Telnet.md",
    "/workflow/actions/snmptrap.html": "04-动作插件/消息与监控类/SNMP Trap.md",
    "/workflow/actions/writetolog.html": "04-动作插件/消息与监控类/写入日志.md",
    "/workflow/actions/nagios.html": "04-动作插件/消息与监控类/Nagios.md",
    "/workflow/actions/shell.html": "04-动作插件/脚本与评估类/Shell脚本.md",
    "/workflow/actions/javascript.html": "04-动作插件/脚本与评估类/JavaScript.md",
    "/workflow/actions/simpleeval.html": "04-动作插件/脚本与评估类/简单评估.md",
    "/workflow/actions/setvariables.html": "04-动作插件/脚本与评估类/设置变量.md",
    "/workflow/actions/start.html": "04-动作插件/工作流控制类/开始.md",
    "/workflow/actions/stop.html": "04-动作插件/工作流控制类/中止.md",
    "/workflow/actions/success.html": "04-动作插件/工作流控制类/成功.md",
    "/workflow/actions/failure.html": "04-动作插件/工作流控制类/失败.md",
    "/workflow/actions/wait.html": "04-动作插件/工作流控制类/等待.md",
    "/workflow/actions/waitfor.html": "04-动作插件/工作流控制类/等待.md",
    "/workflow/actions/pipeline.html": "04-动作插件/工作流控制类/管道.md",
    "/workflow/actions/workflow.html": "04-动作插件/工作流控制类/工作流.md",
    "/workflow/actions/repeat.html": "04-动作插件/工作流控制类/循环.md",
    "/workflow/actions/createfolder.html": "04-动作插件/文件操作类/创建文件夹.md",
    "/workflow/actions/copyfiles.html": "04-动作插件/文件操作类/复制文件.md",
    "/workflow/actions/deletefile.html": "04-动作插件/文件操作类/删除文件.md",
    "/workflow/actions/deletefiles.html": "04-动作插件/文件操作类/删除多个文件.md",
    "/workflow/actions/movefiles.html": "04-动作插件/文件操作类/移动文件.md",
    "/workflow/actions/renamefile.html": "04-动作插件/文件操作类/重命名文件.md",
    "/workflow/actions/filesexist.html": "04-动作插件/文件操作类/文件是否存在.md",
    "/workflow/actions/foldersempty.html": "04-动作插件/文件操作类/文件夹是否为空.md",
    "/workflow/actions/createfile.html": "04-动作插件/文件操作类/创建文件.md",
    "/workflow/actions/writefile.html": "04-动作插件/文件操作类/写入文件.md",
    "/workflow/actions/addresultfilenames.html": "04-动作插件/文件操作类/处理结果文件名.md",
    "/workflow/actions/resultfilenames.html": "04-动作插件/文件操作类/添加文件名到结果.md",
    "/workflow/actions/dostounix.html": "04-动作插件/文件操作类/DOS与Unix格式转换.md",
    "/workflow/actions/sql.html": "04-动作插件/数据库操作类/SQL.md",
    "/workflow/actions/truncate.html": "04-动作插件/数据库操作类/截断表.md",
    "/workflow/actions/eval.html": "04-动作插件/数据库操作类/表输入.md",
    "/workflow/actions/mergetable.html": "04-动作插件/数据库操作类/合并SQL.md",
    "/workflow/actions/xmlwellformed.html": "04-动作插件/XML与验证类/XML格式检查.md",
    "/workflow/actions/xsd.html": "04-动作插件/XML与验证类/XSD验证.md",
    "/workflow/actions/dtd.html": "04-动作插件/XML与验证类/DTD验证.md",
    "/workflow/actions/xslt.html": "04-动作插件/XML与验证类/XSL转换.md",
    "/workflow/actions/pgp.html": "04-动作插件/PGP加密类/PGP解密.md",
    "/workflow/actions/pgpsign.html": "04-动作插件/PGP加密类/PGP签名验证.md",
    "/workflow/actions/neo4j-cypherscript.html": "04-动作插件/Neo4j操作类/Neo4j Cypher脚本.md",
    "/workflow/actions/neo4j-constraint.html": "04-动作插件/Neo4j操作类/Neo4j约束.md",
    "/workflow/actions/neo4j-index.html": "04-动作插件/Neo4j操作类/Neo4j索引.md",
    "/workflow/actions/neo4j-database-update.html": "04-动作插件/Neo4j操作类/Neo4j更新数据库.md",
    "/workflow/actions/neo4j-model.html": "04-动作插件/Neo4j操作类/Neo4j图模型.md",
    "/workflow/actions/genpdf.html": "04-动作插件/其他动作/生成PDF文档.md",
    "/database/databases/postgresql.html": "05-数据库插件/PostgreSQL.md",
    "/database/databases/mysql.html": "05-数据库插件/MySQL.md",
    "/database/databases/oracle.html": "05-数据库插件/Oracle.md",
    "/database/databases/mssql.html": "05-数据库插件/MS-SQLServer.md",
    "/database/databases/h2.html": "05-数据库插件/H2.md",
    "/database/databases/sqlite.html": "05-数据库插件/SQLite.md",
    "/database/databases/derby.html": "05-数据库插件/Apache-Derby.md",
    "/database/databases/hive.html": "05-数据库插件/Apache-Hive.md",
    "/database/databases/impala.html": "05-数据库插件/Cloudera-Impala.md",
    "/database/databases/firebird.html": "05-数据库插件/Firebird.md",
    "/database/databases/ingres.html": "05-数据库插件/Ingres.md",
    "/database/databases/interbase.html": "05-数据库插件/Interbase.md",
    "/database/databases/informix.html": "05-数据库插件/Informix.md",
    "/database/databases/sybase.html": "05-数据库插件/Sybase.md",
    "/database/databases/sybaseiq.html": "05-数据库插件/Sybase-IQ.md",
    "/database/databases/access.html": "05-数据库插件/Microsoft-Access.md",
    "/database/databases/maxdb.html": "05-数据库插件/MaxDB-SAP-DB.md",
    "/database/databases/redshift.html": "05-数据库插件/Redshift.md",
    "/database/databases/bigquery.html": "05-数据库插件/Google-BigQuery.md",
    "/database/databases/databricks.html": "05-数据库插件/Databricks.md",
    "/database/databases/snowflake.html": "05-数据库插件/Snowflake.md",
    "/database/databases/clickhouse.html": "05-数据库插件/ClickHouse.md",
    "/database/databases/duckdb.html": "05-数据库插件/DuckDB.md",
    "/database/databases/vertica.html": "05-数据库插件/Vectorwise.md",
    "/database/databases/netezza.html": "05-数据库插件/Netezza.md",
    "/database/databases/greenplum.html": "05-数据库插件/Greenplum.md",
    "/database/databases/monetdb.html": "05-数据库插件/MonetDB.md",
    "/database/databases/doris.html": "05-数据库插件/Apache-Doris.md",
    "/database/databases/cratedb.html": "05-数据库插件/CrateDB.md",
    "/database/databases/tigergraph.html": "05-数据库插件/TigerGraph.md",
    "/database/databases/teradata.html": "05-数据库插件/Teradata.md",
    "/database/databases/exasol.html": "05-数据库插件/Exasol.md",
    "/database/databases/sapdb.html": "05-数据库插件/MaxDB-SAP-DB.md",
    "/metadata-types/mongodb-connection.html": "06-元数据类型/MongoDB连接.md",
    "/metadata-types/rest-connection.html": "06-元数据类型/REST连接.md",
    "/metadata-types/s3-connection.html": "06-元数据类型/MinIO-S3连接.md",
    "/metadata-types/hop-server.html": "06-元数据类型/Hop-Server.md",
    "/metadata-types/pipeline-run-config.html": "06-元数据类型/管道运行配置.md",
    "/metadata-types/workflow-run-config.html": "06-元数据类型/工作流运行配置.md",
    "/metadata-types/data-set.html": "06-元数据类型/数据集.md",
    "/metadata-types/rdbms-connection.html": "06-元数据类型/关系型数据库连接.md",
    "/metadata-types/mail-server-connection.html": "06-元数据类型/邮件服务器连接.md",
    "/metadata-types/splunk-connection.html": "06-元数据类型/Splunk连接.md",
    "/metadata-types/cassandra/cassandra-connection.html": "06-元数据类型/Cassandra连接.md",
    "/metadata-types/neo4j/neo4j-connection.html": "06-元数据类型/Neo4j连接.md",
    "/metadata-types/neo4j/neo4j-graphmodel.html": "06-元数据类型/Neo4j图模型.md",
    "/metadata-types/neo4j/neo4j-location.html": "06-元数据类型/Neo4j位置类型.md",
    "/metadata-types/pipeline-log.html": "06-元数据类型/管道日志.md",
    "/metadata-types/workflow-log.html": "06-元数据类型/工作流日志.md",
    "/metadata-types/pipeline-probe.html": "06-元数据类型/管道探针.md",
    "/metadata-types/pipeline-unit-test.html": "06-元数据类型/管道单元测试.md",
    "/metadata-types/execution-information.html": "06-元数据类型/执行信息位置.md",
    "/metadata-types/execution-data-profiling.html": "06-元数据类型/执行数据画像.md",
    "/metadata-types/static-schema-definition.html": "06-元数据类型/静态模式定义.md",
    "/metadata-types/partition-schema.html": "06-元数据类型/分区模式.md",
    "/metadata-types/data-stream.html": "06-元数据类型/数据流.md",
    "/metadata-types/beam-file-definition.html": "06-元数据类型/Beam文件定义.md",
    "/metadata-types/async-web-service.html": "06-元数据类型/异步Web服务.md",
    "/metadata-types/webdav-connection.html": "06-元数据类型/WebDAV连接.md",
    "/metadata-types/azure-connection.html": "06-元数据类型/Azure认证.md",
    "/metadata-types/google-storage-auth.html": "06-元数据类型/Google存储认证.md",
    "/metadata-types/arrow-flight-connection.html": "06-元数据类型/Apache-Arrow-Flight.md",
    "/metadata-types/arrow-stream.html": "06-元数据类型/Apache-Arrow文件流.md",
    "/metadata-types/arrow-random-access.html": "06-元数据类型/Apache-Arrow随机访问文件.md",
    "/metadata-types/variable-resolver/google-secret-manager-variable-resolver.html": "06-元数据类型/Google-Secret-Manager变量解析器.md",
    "/metadata-types/variable-resolver/hashicorp-vault-variable-resolver.html": "06-元数据类型/Hashicorp-Vault变量解析器.md",
    "/metadata-types/variable-resolver/azure-key-vault-variable-resolver.html": "06-元数据类型/Azure-Key-Vault变量解析器.md",
    "/metadata-types/pipeline-resolver.html": "06-元数据类型/管道变量解析器.md",
    "/metadata-types/variable-resolver/overview.html": "06-元数据类型/变量解析器总览.md",
    # Additional mappings for previously unmatched entries
    "/metadata-types/s3-connection.html": "06-元数据类型/minio-connection.md",
    "/hop-gui/perspective-git.html": "10-HopGUI/hop-gui-git.md",
    "/hop-gui/perspective-git-commit.html": "10-HopGUI/hop-gui-git.md",
}


def find_java_files(root):
    for dirpath, _, filenames in os.walk(root):
        for f in filenames:
            if f.endswith(".java"):
                yield os.path.join(dirpath, f)


def extract_annotations(filepath):
    with open(filepath, "r", encoding="utf-8", errors="replace") as f:
        content = f.read()
    results = []
    for ann_type, pattern in ANNOTATION_PATTERNS.items():
        for m in pattern.finditer(content):
            groups = m.groups()
            entry = {
                "annotationType": ann_type,
                "pluginId": groups[0] if groups[0] else "",
                "name": groups[1] if len(groups) > 1 and groups[1] else "",
                "documentationUrl": groups[2] if len(groups) > 2 and groups[2] else "",
                "file": filepath,
            }
            if entry["documentationUrl"]:
                results.append(entry)
    return results


def collect_md_files(root):
    md_files = []
    for dirpath, _, filenames in os.walk(root):
        for f in filenames:
            if f.endswith(".md") and f != "README.md":
                full = os.path.join(dirpath, f)
                rel = os.path.relpath(full, root)
                md_files.append(rel)
    return sorted(md_files)


def normalize_url(url):
    if not url:
        return ""
    url = url.strip()
    if not url.startswith("/"):
        url = "/" + url
    return url


def match_by_override(url, md_files):
    if url in MANUAL_OVERRIDE:
        target = MANUAL_OVERRIDE[url]
        for md in md_files:
            if md == target:
                return md
    return None


def extract_url_id(url):
    parts = url.rstrip("/").split("/")
    filename = parts[-1] if parts else ""
    return filename.replace(".html", "").replace(".htm", "")


def match_by_keywords(url_id, md_files, annotation_type):
    url_id_lower = url_id.lower().replace("-", "").replace("_", "")
    best_match = None
    best_score = 0
    for md in md_files:
        md_name = Path(md).stem.lower()
        md_name_clean = md_name.replace("-", "").replace("_", "").replace(" ", "")
        if url_id_lower == md_name_clean:
            return md
        if url_id_lower in md_name_clean or md_name_clean in url_id_lower:
            score = len(url_id_lower) / max(len(md_name_clean), 1)
            if score > best_score:
                best_score = score
                best_match = md
    return best_match if best_score > 0.4 else None


def main():
    print("==> 收集插件注解...")
    annotations = []
    scan_dirs = [
        PROJECT_ROOT / "engine" / "src",
        PROJECT_ROOT / "core" / "src",
        PROJECT_ROOT / "ui" / "src",
    ]
    plugins_dir = PROJECT_ROOT / "plugins"
    if plugins_dir.is_dir():
        for cat in plugins_dir.iterdir():
            if cat.is_dir() and (cat / "pom.xml").exists():
                for sub in cat.iterdir():
                    if sub.is_dir() and (sub / "pom.xml").exists():
                        src_dir = sub / "src"
                        if src_dir.is_dir():
                            scan_dirs.append(src_dir)
    for scan_dir in scan_dirs:
        if scan_dir.is_dir():
            for jf in find_java_files(scan_dir):
                annotations.extend(extract_annotations(jf))

    print(f"   发现 {len(annotations)} 个带 documentationUrl 的注解")

    print("==> 收集本地 MD 文件...")
    md_files = collect_md_files(MANUAL_DIR)
    print(f"   发现 {len(md_files)} 个 MD 文件")

    print("==> 构建映射...")
    mappings = []
    matched = 0
    unmatched = 0

    for ann in sorted(annotations, key=lambda x: (x["annotationType"], x["pluginId"])):
        url = normalize_url(ann["documentationUrl"])
        md_path = match_by_override(url, md_files)
        status = "matched" if md_path else "unmatched"
        if not md_path:
            url_id = extract_url_id(url)
            md_path = match_by_keywords(url_id, md_files, ann["annotationType"])
            if md_path:
                status = "matched"

        if status == "matched":
            matched += 1
        else:
            unmatched += 1

        mappings.append({
            "annotationType": ann["annotationType"],
            "pluginId": ann["pluginId"],
            "pluginName": ann["name"],
            "documentationUrl": url,
            "status": status,
            "mdRelativePath": md_path,
            "note": "TODO: 需要创建对应的本地文档" if status == "unmatched" else None,
        })

    total = len(mappings)
    print(f"   匹配: {matched}, 未匹配: {unmatched}, 总计: {total}")

    output = {
        "version": "1.0",
        "generatedAt": datetime.now().isoformat(),
        "stats": {
            "total": total,
            "matched": matched,
            "unmatched": unmatched,
            "matchRate": f"{matched / total * 100:.1f}%" if total > 0 else "0%",
        },
        "mappings": mappings,
    }
    with open(OUTPUT_JSON, "w", encoding="utf-8") as f:
        json.dump(output, f, ensure_ascii=False, indent=2)
    print(f"==> 映射表已生成: {OUTPUT_JSON}")

    lines = []
    lines.append("# 未匹配插件清单")
    lines.append(f"\n> 生成时间: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}")
    lines.append(f"> 总计 {unmatched} 个插件未匹配到本地文档")
    lines.append("")

    by_type = defaultdict(list)
    for m in mappings:
        if m["status"] == "unmatched":
            by_type[m["annotationType"]].append(m)

    type_order = ["Transform", "Action", "DatabaseMetaPlugin", "HopMetadata", "HopPerspectivePlugin", "VariableResolverPlugin"]
    for ann_type in type_order:
        items = by_type.get(ann_type, [])
        if not items:
            continue
        lines.append(f"## {ann_type}（{len(items)} 个未匹配）")
        lines.append("")
        lines.append("| 序号 | 插件ID | 名称 | documentationUrl |")
        lines.append("|------|--------|------|------------------|")
        for i, item in enumerate(items, 1):
            lines.append(f"| {i} | `{item['pluginId']}` | {item['pluginName']} | `{item['documentationUrl']}` |")
        lines.append("")

    with open(OUTPUT_UNMATCHED, "w", encoding="utf-8") as f:
        f.write("\n".join(lines))
    print(f"==> 未匹配清单已生成: {OUTPUT_UNMATCHED}")


if __name__ == "__main__":
    main()
