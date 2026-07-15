# ![Read data (key, value) from properties files transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/propertyinput.svg) Read data (key, value) from properties files

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

### 文件 选项卡

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称。此名称在单个 Pipeline 中必须唯一。 |
| Filename is defined in a field? | 要读取的文件名来自输入流中的某个字段。 |
| Get filename from field | 指定要从中读取文件名的字段。 |
| File or directory | 指定输入文本文件的位置和/或名称。 |
| Add | 点击将文件/目录/通配符组合添加到下方的已选文件列表（网格）中。 |
| Browse | 点击从文件浏览器中选择要加载的文件。 |
| Regular expression | 指定用于在前一选项指定的目录中选择文件的正则表达式。 |
| Exclude Regular | 指定用于从指定目录中排除文件的正则表达式。 |
| Selected Files | 包含已选文件（或通配符选择）的列表，以及指定该文件是否必需的属性。 |
| Delete | 点击从列表中删除所选文件或通配符选择。 |
| Edit | 点击修改所选文件或通配符。 |
| Show filenames(s)... | 显示基于当前所选文件定义将加载的所有文件列表 |

### 内容 选项卡

| 选项 | 描述 |
|---|---|
| File Type | 文件的类型。应为 "Properties file"。 |
| Encoding | 文件使用的字符编码。 |
| Section | 如有必要，指定要读取的文件区段。 |
| Limit | 指定从文件中读取的最大行数，或指定 0 表示无限制。 |
| Substitute variable for value | 如果文件是通过变量提供的，则解析该变量以获取文件名。 |
| Include filename in output? | 允许您指定一个字段名，以便在此 transform 的输出中包含文件名。 |
| Rownum in output | 允许您指定一个字段名，以便在此 transform 的输出中包含行号（Integer 类型）。 |
| Reset rownum per file? | 启用后，在每个单独文件开始时将生成的行号重置为 1。否则，行号将计算所有文件中的所有行。 |
| Include section name in output? | 允许您指定一个字段名，以便在此 transform 的输出中包含区段名。 |
| Add filename to result? | 将读取的文件名添加到此 Pipeline 的结果中。 |

### 字段 选项卡

| 选项 | 描述 |
|---|---|
| Name | 输出字段的名称 |
| Column | 要读取的属性 |
| Type | 字段的数据类型。如果源文件不使用相同的类型，则可能需要转换。以下所有选项均适用于转换后的结果。 |
| Format | 数据类型转换中使用的格式或转换掩码 |
| Length | 输出数据类型的最大字符数，或 -1 表示无限制。 |
| Precision | 用于显示浮点数的小数位数。 |
| Currency | 用于显示货币值的货币符号。 |
| Decimal | 用于数字的小数点符号。通常为 "."（10.5）或 ","（10,5）。 |
| Group | 用于数字的千位分隔符符号。通常为 ","（5,200,820）或 "."（5.200.820）。 |
| Trim type | 用于去除字符串值中空格的修剪方法。可以是 left（去除前导空格）、right（去除尾部空格）、both 或 none。 |
| Repeat | 如果列值为空（null），则重复前一行的列值 |

### 额外输出字段 选项卡

| 选项 | 描述 |
|---|---|
| Short filename field | 用于包含不含路径的文件名的字段。 |
| Extension field | 用于包含文件扩展名的字段。 |
| Path field | 用于包含文件路径的字段。 |
| Size field | 用于包含文件大小的字段。 |
| Is hidden field | 用于包含文件是否隐藏的字段。 |
| Last modification field | 用于包含文件最后修改日期的字段。 |
| Uri field | 用于包含文件完整源 URL 的字段。根据文件来源，可以是 http:// 或 file:// URL。 |
| Root uri field | 用于包含文件源 URL 根路径的字段，包括协议和顶级地址。 |
