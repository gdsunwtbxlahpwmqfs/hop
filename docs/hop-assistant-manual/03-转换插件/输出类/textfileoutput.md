# ![Text File Output transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/textfileoutput.svg) Text File Output

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## Options

### File 选项卡

File 选项卡用于定义正在创建的文件的基本属性，例如：

| Option | Description |
|---|---|
| Transform name | Transform 的名称。此名称在单个 Pipeline 中必须唯一。 |
| Filename | 指定输出文本文件的文件名和位置。点击 Browse 选择位置。 |
| Create parent folder | 启用以在创建文件时创建父文件夹。否则文件夹必须已存在，否则写入失败。 |
| Do not create file at start | 启用以在没有行被处理时避免创建空文件。否则文件在处理任何数据之前就被创建，可能产生空文件。 |
| Accept file name from field? | 启用以在输入流的字段中指定文件名 |
| File name field | 当启用前一个选项时，指定运行时将包含文件名的字段。 |
| Extension | 指定文件扩展名。创建文件时自动在文件名和扩展名之间添加句点字符。例如 name.txt。 |
| Include transform nr in filename | 如果您以多个副本运行 Transform（启动一个 Transform 的多个副本），副本编号将包含在文件名中，位于扩展名之前，并用下划线与文件名分隔。例如 name_0。 |
| Include partition nr in filename? | 在文件名中包含数据分区编号，如果数据未分区则为 null。 |
| Include date in filename | 在文件名中包含系统日期。用下划线与文件名分隔，默认使用 YYYYMMDD 格式。例如 name_20230405。 |
| Include time in filename | 在文件名中包含系统时间。用下划线与文件名分隔，默认使用 hhmmss 格式。例如 name_235959。 |
| Specify Date time format | 启用以指定日期和时间格式。否则这些组件使用默认值。 |
| Date time format | 从列表中选择要应用于附加到文件名的日期和时间值的格式。 |
| Show filename(s) | 查看将生成的文件列表。 |
| Add file name to result | 这将所有已处理的文件名添加到内部结果文件名集中，以便进一步处理。 |

### Content 选项卡

Content 选项卡包含以下用于描述正在读取内容的选项：

| Option | Description |
|---|---|
| Append | 勾选以将行追加到指定文件的末尾。否则文件将被替换。 |
| Separator | 指定分隔单行文本中各字段的字符。 |
| Insert TAB | 点击在 Separator 字段中插入制表符。 |
| Enclosure | 一对字符串可以用来包裹某些字段。 |
| Force the enclosure around fields? | 此选项强制传入字符串类型的所有字段（与 Text File Output 字段定义中可能更改的字段类型无关）使用上面 Enclosure 属性中指定的字符进行包裹。 |
| Disable the enclosure fix? | 当字符串字段包含包裹字符时，它会被包裹，并且包裹字符会被转义。 |
| Header | 如果您希望文本文件有标题行，请启用此选项。 |
| Footer | 如果您希望文本文件有页脚行，请启用此选项。 |
| Format | 可以是 DOS 或 UNIX。 |
| Encoding | 指定要使用的文本文件编码，或留空以使用系统上的默认编码。 |
| Compression | 允许您压缩文件并指定压缩类型（.zip 或 .gzip），用于压缩输出。 |
| Right pad fields | 在字段末尾添加空格（或删除末尾字符），直到它们达到 Fields 选项卡上指定的长度。 |
| Fast data dump (no formatting) | 通过不包含任何格式化信息来提高将大量数据转储到文本文件时的性能。 |
| Split every ... rows | 指定行数以将文件拆分为每块该行数的块。 |
| Add Ending line of file | 指定输出文件的替代结束行。 |

### Fields 选项卡

Fields 选项卡用于定义正在导出字段的属性。
下表描述了配置字段属性的每个选项：

| Option | Description |
|---|---|
| Schema Definition | 要引用的 [Schema Definition](../../06-元数据类型/static-schema-definition.md) 名称。 |
| Name | 字段的名称。 |
| Type | 字段的数据类型。可以是 String、Date 或 Number。 |
| Format | 选择要应用于字段值的格式。仅适用于数字和日期。 |
| Length a | Length 选项取决于字段类型： |
| Precision | 对于数字，指定浮点数字的位数 |
| Currency | 用于表示货币的符号，如美元（$10,000.00）或欧元（E5.000,00） |
| Decimal | 用于表示浮点数小数点的字符。小数点可以是 "."（10,000.00）或 ","（5.000,00）。 |
| Group | 用于表示数字千位分隔符的字符。可以是 ","（10,000.00）或 "."（5.000,00） |
| Trim type | 用于删除字符串值中空格的修剪方法。可以是 left（删除前导空格）、right（删除尾随空格）、both 或 none。 |
| Null | 如果字段值为 null，指定要插入字段中的值。 |
| Get Fields | 点击从输入字段流中检索字段列表。 |
| Rounding Type a | 将数字写入文件时，您可以指定使用的舍入类型，默认使用 `Half Even`，更多信息请查看 Rounding Types 部分。 |
| Minimal width | 以使文本文件中行的结果宽度最小的方式调整 fields 选项卡中的选项。 |

### Rounding Types

Number 和 BigNumber 数据类型字段的舍入基于 [Java Rounding Mode](https://docs.oracle.com/javase/8/docs/api/java/math/RoundingMode)

默认使用 `Half Even` 舍入模式，此舍入模式将向"最近邻居"舍入，除非两个邻居等距，在这种情况下向偶数邻居舍入。

示例：从 1 位舍入到 0 位
5.5 -> 6
2.5 -> 2
-2.5 -> -2
-5.5 -> -6

#### Unnecessary
断言所请求的操作具有精确结果的舍入模式，因此无需舍入。当您尝试减少数字的精度时，此模式将抛出错误

#### Ceiling
向正无穷方向舍入的舍入模式。

#### Down
向零方向舍入的舍入模式。

#### Floor
向负无穷方向舍入的舍入模式。

#### Half Down
向"最近邻居"舍入的舍入模式，除非两个邻居等距，在这种情况下向下舍入。

#### Half Even
向"最近邻居"舍入的舍入模式，除非两个邻居等距，在这种情况下向偶数邻居舍入。

#### Half Up
向"最近邻居"舍入的舍入模式，除非两个邻居等距，在这种情况下向上舍入。

#### Up
远离零方向舍入的舍入模式。

#### 示例

| Input Number | Up | Down | Ceiling | Floor | Half Up | Half Down | Half Even | Unnecessary |
|---|---|---|---|---|---|---|---|---|
| 5.5 | 6 | 5 | 6 | 5 | 6 | 5 | 6 | throw ArithmeticException |
| 2.5 | 3 | 2 | 3 | 2 | 3 | 2 | 2 | throw ArithmeticException |
| 1.6 | 2 | 1 | 2 | 1 | 2 | 2 | 2 | throw ArithmeticException |
| 1.1 | 2 | 1 | 2 | 1 | 1 | 1 | 1 | throw ArithmeticException |
| 1.0 | 1 | 1 | 1 | 1 | 1 | 1 | 1 | 1 |
| -1.0 | -1 | -1 | -1 | -1 | -1 | -1 | -1 | -1 |
| -1.1 | -2 | -1 | -1 | -2 | -1 | -1 | -1 | throw ArithmeticException |
| -1.6 | -2 | -1 | -1 | -2 | -2 | -2 | -2 | throw ArithmeticException |
| -2.5 | -3 | -2 | -3 | -3 | -3 | -2 | -2 | throw ArithmeticException |
| -5.5 | -6 | -5 | -6 | -6 | -6 | -5 | -6 | throw ArithmeticException |
