# ![Microsoft Excel input transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/excelinput.svg) Microsoft Excel input

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## 选项

### 文件选项卡

| 选项 | 描述 |
|---|---|
| Transform Name | Transform 的名称；该名称在单个 transform 中必须唯一。 |
| Spread sheet type (engine) a | 此字段允许您指定电子表格类型。 |
| File or directory | 指定输入文本文件的位置和/或名称。 |
| Regular expression | 指定要使用的正则表达式，用于在上一个选项指定的目录中选择文件。 |
| Exclude Regular Expression | 排除（在给定位置下）符合此正则表达式条件的所有文件。 |
| Selected Files | 包含已选文件（或通配符选择）的列表，以及指定文件是否为必需的属性。 |
| Accept filenames from previous transforms | 允许从 transform 中的前一个 transform 读取文件名。 |
| Show filenames(s)... | 显示基于当前选定文件定义将加载的所有文件的列表。 |
| Preview rows | 点击 Preview 可查看指定 Excel 文件的内容。 |

### 工作表

在此选项卡中，您可以指定要读取的 Excel 工作簿中的工作表名称。
对于每个工作表名称，您可以指定起始行和起始列。

### 内容

| 选项 | 描述 |
|---|---|
| Header | 如果指定的工作表包含需要跳过的标题行，请启用此项。 |
| No empty rows | 如果不希望此 transform 的输出中出现空行，请启用此项。 |
| Stop on empty row | 使 transform 在遇到空行时停止读取当前文件的当前工作表。 |
| Limit | 将行数限制为该数值（零 (0) 表示所有行）。 |
| Encoding | 指定要使用的文本文件编码。 |

### 错误处理

| 选项 | 描述 |
|---|---|
| Strict types? | 如果勾选，Hop 将报告输入中的数据类型错误。 |
| Ignore errors? | 如果要在解析过程中忽略错误，请启用此项。 |
| Skip error lines? | 如果勾选，Hop 将跳过包含错误的行。 |
| Warnings file directory | 生成警告时，它们会被放置在此目录中。 |
| Error files directory | 发生错误时，它们会被放置在此目录中。 |
| Failing line numbers files directory | 当某行发生解析错误时，该行号会被放置在此目录中。 |

### 字段选项卡

字段选项卡用于指定必须从 Excel 文件中读取的字段。您可以选择使用 [Schema Definition](../../06-元数据类型/static-schema-definition.md) 或手动定义所需字段的布局。

`ignore manual fields` 选项会忽略在 transform 字段布局中手动定义的所有字段，仅使用 [Schema Definition](../../06-元数据类型/static-schema-definition.md) 中指定的布局。

如果工作表包含标题行，可使用 `Get fields from header` 自动填充可用字段。

Type 列用于对给定字段执行类型转换。
例如，如果要读取日期而 Excel 文件中是 String 值，请指定转换掩码。
注意：在 Number 转 Date 的情况下（例如 20051028 --> 2005年10月28日），请指定转换掩码 yyyyMMdd，因为在进行 String 到 Date 的转换之前会先进行隐式的 Number 到 String 转换。

| 选项 | 描述 |
|---|---|
| Schema Definition | 要引用的 [Schema Definition](../../06-元数据类型/static-schema-definition.md) 名称。 |
| Name | 字段的名称。 |
| Type | 字段的数据类型；String、Date 或 Number。 |
| Length | 长度选项取决于字段类型。 |
| Precision | 精度选项取决于字段类型，但仅支持 Number 类型；它返回小数位数。 |
| Trim type | 在处理前截断字段（左、右、两端）。 |
| Repeat | 如果设为 Y，当下一行中该字段为空时，将重复此值。 |
| Format | 格式掩码（数字类型）。 |
| Currency | 用于表示货币的符号。 |
| Decimal | 小数点；可以是点或逗号。 |
| Grouping | 在四位或更多位数字中分隔千位的方法。 |

### 额外输出字段选项卡

此选项卡用于检索要添加到 transform 输出的自定义 metadata 字段。
每个字段的用途由其名称定义，但您可以将这些字段用于任何目的。
每个项目定义一个输出字段，将包含以下信息。
其中部分字段可能缺失。

| 选项 | 描述 |
|---|---|
| Full filename field | 完整文件名（含扩展名）。 |
| Sheetname field | 您正在使用的工作表名称。 |
| Sheet row nr field | 当前工作表行号。 |
| Row nr written field | 已写入的行数。 |
| Short filename field | 包含不带路径信息但带扩展名的文件名的字段。 |
| Extension field | 包含文件扩展名的字段。 |
| Path field | 包含操作系统格式路径的字段。 |
| Size field | 包含文件大小（以字节为单位）的字段。 |
| Is hidden field | 指示文件是否隐藏的字段（布尔值）。 |
| Uri field | 包含 URI 的字段。 |
| Root uri field | 仅包含 URI 根部分的字段。 |
