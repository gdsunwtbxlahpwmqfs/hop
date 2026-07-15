# ![YAML Input transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/yamlinput.svg) YAML Input

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## Options

### File 选项卡

| Option | Description |
|---|---|
| Transform name | Transform 的名称。 |
| Source is defined in a field? | 源是否来自前一个 Transform 中的字段。 |
| Source is a filename | 源是否为文件名，否则为目录。 |
| get source from a field | 包含文件名或目录的字段。 |
| File or directory | 文件或目录路径。 |
| Add | 将文件或目录添加到 Selected files 列表。 |
| Browse | 浏览本地文件系统以查找文件或目录。 |
| Regular Expression |  |
| Selected files | 选定的文件或目录。 |
| Delete | 从列表中删除选定行。 |
| Edit | 移动选定行进行编辑。 |
| Show filename(s) | 预览文件名。 |

### Content 选项卡

| Option | Description |
|---|---|
| Ignore empty file | 是否忽略空文件。 |
| Do not raise an error if no files | 如果没有可用文件，是否引发错误。 |
| Limit | 设置要读取的限制。 |
| Include filename in output? | 将文件名添加到输出行。 |
| Filename fieldname | 包含文件名的字段。 |
| Rownum in output? | 将行号添加到输出行。 |
| Rownum fieldname | 包含行号的字段。 |
| Add files to result filesname |  |

### Fields 选项卡

| Option | Description |
|---|---|
| Name | 输出字段的名称。 |
| Key | 元素的键。 |
| Type | 要转换为的数据类型。 |
| Format | 数据类型转换中使用的格式或转换掩码。 |
| Length | 输出数据类型的长度。 |
| Precision | 输出数据类型的精度。 |
| Currency | 数据类型转换中使用的货币符号。 |
| Decimal | 数据类型转换中使用的数字小数符号。 |
| Group | 数据类型转换中使用的数字分组符号。 |
| Trim type | 数据类型转换中使用的修剪类型。 |
| Get fields | 从输入中近似获取字段。 |
