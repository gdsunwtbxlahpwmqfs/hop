# ![Oracle Bulk Loader transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/orabulkloader.svg) Oracle Bulk Loader

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称。 |
| Connection | 目标表所在的数据库连接名称。 |
| Target schema | 要写入数据的表的 Schema 名称。 |
| Target table | 目标表的名称。 |
| Sqldr path | sqlldr 工具的完整路径（包括 sqlldr）。 |
| Load method | 加载数据时要使用的 SQL*Loader 方法： |
| Load action | Append、Insert、Replace、Truncate。这些映射到要执行的 sqlldr 操作。 |
| Maximum errors | sqlldr 中止前允许的错误行数。对应 sqlldr 的 "ERROR" 属性。 |
| Commit | 提交的行数，对应 sqlldr 的 "ROWS" 属性，在常规路径加载和直接路径加载之间有所不同。 |
| Bind Size | 对应 sqlldr 的 "BINDSIZE" 属性。 |
| Read Size | 对应 sqlldr 的 "READSIZE" 属性。 |
| Control file | 用作 sqlldr 控制文件的文件名。 |
| Data file | 写入数据的文件名。 |
| Log file | 日志文件的名称（可选）。 |
| Bad file | 包含被拒绝记录的 bad 文件名称（可选）。 |
| Discard file | 包含不匹配控制文件中任何记录选择条件的已过滤记录的 discard 文件名称（可选）。 |
| Encoding | 以特定编码对数据进行编码，除了下拉列表中的编码外，还可以选择任何有效的编码。 |
| Oracle Character Set | 要使用的 Oracle 字符集。 |
| Alternate Record Terminator | 指定此值以覆盖 SQL*Loader 的默认记录终止符。 |
| Direct path | 开启直接路径加载（对应 sqlldr 中的 DIRECT=TRUE）。 |
| Erase cfg/dat files after use | 此选项会在使用后删除控制文件和数据文件。 |
| Fail on warning | 此选项使 Transform 在加载警告时失败。 |
| Fail on error | 此选项使 Transform 在加载错误时失败。 |
| Load data in parallel | 此选项使数据并行加载。并行加载仅在直接路径选项下可用。 |
| Fields to load a | 此表包含要加载数据的字段列表，属性包括： |
