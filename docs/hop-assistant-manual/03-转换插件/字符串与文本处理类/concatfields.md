# ![Concat Fields transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/concatfields.svg) Concat Fields

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## Options

### General

| Option | Description |
|---|---|
| Transform name | Transform 的名称。 |
| Target Field Name | 目标字段的名称（String 类型） |
| Length of Target Field | String 类型的长度（设置 String 类型的 metadata，Fast Data Dump 选项也使用此项进行性能优化） |
| Separator | 指定分隔单行文本中各字段的字符。 |
| Enclosure | 一对字符串可以用来包裹某些字段。 |
| Force enclosure? | 强制在添加到拼接字符串的每个字段前后添加包裹字符。 |
| Remove concatenated fields from output? | 如果勾选，用于创建拼接字段（在 `Target Field Name` 中指定）的字段将从输出中移除，在后续 Transform 中不可用。 |

### Fields Tab

此选项卡与 Text File Output transform 的 fields 选项卡完全相同，具有相同的功能。

| Option | Description |
|---|---|
| Name | 字段的名称。 |
| Type | 字段的类型可以是 String、Date 或 Number。 |
| Format | 用于转换的格式掩码。 |
| Length a | Length 选项取决于字段类型，如下所示： |
| Precision a | Precision 选项取决于字段类型，如下所示： |
| Currency | 用于表示货币的符号，如 $10,000.00 或 E5.000,00 |
| Decimal | 小数点可以是 "."（10,000.00）或 ","（5.000,00） |
| Group | 分隔符可以是 ","（10,000.00）或 "."（5.000,00） |
| Trim type | 应用于字符串的修剪方法。 |
| Null | 用于替换该字段 null 值的字符串，默认情况下 null 被替换为空字符串。 |
| Get | 点击从输入字段流中检索字段列表 |
| Minimal width | 以使文本文件中行的结果宽度最小的方式调整 fields 选项卡中的选项。 |
