# ![Load file content in memory transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/loadfileinput.svg) Load file content in memory

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

### File 选项卡

| Option | Description |
|---|---|
| Filename is defined in a field | 从字段获取文件名 |
| get filename from a field | 包含文件名的字段 |
| File or directory | 要添加的文件或目录路径 |
| Add | 将文件或目录添加到已选文件 |
| Browse | 使用本地文件浏览器选择路径 |
| Regular Expression | 允许基于正则表达式包含文件 |
| Exclude Regular Expression | 允许基于正则表达式排除文件 |
| Selected files | 要加载到内存中的已选文件 |
| Show filename(s) | 预览已选文件 |

### Content 选项卡

| Option | Description |
|---|---|
| Encoding | 文件编码（UTF、ISO 等） |
| Ignore empty file | 忽略空文件 |
| Ignore missing path | 忽略缺失路径 |
| Limit | 限制从文件加载的行数 |
| Include filename in output? | 允许在输出中包含文件名 |
| Filename fieldname | 包含文件名的字段 |
| Rownum in output? | 允许在输出中包含行号 |
| Rownum filename | 包含行号的字段 |
| Add files to result filesname | 将文件添加到结果文件名 |

### Fields 选项卡

要从文件中加载的字段。

| Option | Description |
|---|---|
| Name | 导入字段的名称。 |
| Element | 文件内容或大小 |
| Type | 此字段的数据类型 |
| Format | 格式掩码 |
| Length | 字段长度 |
| Precision | precision 选项取决于字段类型，但仅支持 Number；返回浮点位数 |
| Currency | 用于表示货币的符号 |
| Decimal | 小数点；可以是点或逗号 |
| Group | 分隔四位及以上数字中千位单位的方法。 |
| Trim type | 裁剪类型：none、left、right、both |
| Repeat | 如果此行中的对应值为空，则输入 'Y' 以重用上一个非空行的值。 |
| Get fields | 根据文件内容检索可用字段 |

### Additional output 选项卡

| Option | Description |
|---|---|
| Short filename field | 包含不带路径信息但带扩展名的文件名的字段名。 |
| Extension field | 包含文件扩展名的字段名。 |
| Path field | 包含操作系统格式路径的字段名。 |
| Is hidden field | 包含文件是否隐藏的字段名（boolean）。 |
| Last modification field | 包含最后修改时间的字段名。 |
| Uri field | 包含 URI 的字段名。 |
| Root uri field | 仅包含 URI 根部分的字段名。 |

### 按钮

| Option | Description |
|---|---|
| Preview rows | 预览此 Transform 生成的行。 |
