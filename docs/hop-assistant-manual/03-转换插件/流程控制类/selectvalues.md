# ![Select Values transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/selectvalues.svg) Select Values

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

### 常规设置

| 选项 | 描述 |
|---|---|
| Transform Name | Transform 的名称：此名称在单个 pipeline 中必须唯一。 |

### Select & Alter 标签页

此标签页包含选择要转发到下一个 transform 的字段以及更改其顺序的选项。
`Get fields to select` 按钮将根据现有的输入 transform 检索可用字段，并填充此标签页中的条目。

| 选项 | 描述 |
|---|---|
| Fieldname | 输入流中字段的名称 |
| Rename to | 字段的新名称。 |
| Length | 字段长度 |
| Precision | Precision 选项取决于字段类型，但仅支持 Number 类型；它返回小数点后的位数 |
| Include unspecified fields, ordered by name | 如果要隐式选择输入流中未在 Fields 部分显式选择的所有其他字段，请启用此项 |

要复制字段，只需多次指定相同的字段名称并使用不同的重命名值即可。

### Remove 标签页

此标签页允许您从输入流中移除字段。
点击 `Get fields to remove` 可从前面的 transform 导入字段（这会用所有字段填充表格，您可能需要移除那些想要保留在流中的字段）。

### Meta-data 标签页

此标签页下的选项允许您重命名输入字段、将其转换为不同的数据类型，以及更改其长度和精度。
点击 `Get fields to change` 可从前面的 transform 导入字段。

| 选项 | 描述 |
|---|---|
| Fieldname | 输入字段的名称 |
| Rename to | 如果要重命名此字段，请在此处输入新名称 |
| Type | 此字段的数据类型 |
| Length | 字段长度 |
| Precision | Precision 选项取决于字段类型，但仅支持 Number 类型；它返回小数点后的位数 |
| Binary to Normal? | 在适当时将字符串转换为数值数据类型 |
| Format | 格式掩码（数值类型或日期格式） |
| Date Format Lenient? | 决定日期解析器是严格模式还是宽松模式。 |
| Date Locale | 指定用于日期转换和计算的日期区域设置。 |
| Date Time Zone | 指定用于日期转换和计算的日期时区。 |
| Lenient number conversion | 当此选项设置为 Y 时，数字会一直解析直到遇到非数值字符（例如破折号或斜杠），并停止解析而不报错。 |
| Encoding | 指定要使用的文本文件编码。 |
| Decimal | 小数点符号；可以是点或逗号 |
| Grouping | 一种分隔四位及以上数字中千位单位的方法。 |
| Currency | 用于表示货币的符号 |
| Rounding type | 此选项允许您定义 Number 转换为 String 时的舍入方式。 |

## 特殊用例

### 复制字段

您可以将单个字段的多个副本发送到下一个 transform，前提是它们具有不同的名称。只需在 *Select & Alter* 标签页的多行中输入要复制的字段，并在 `Rename to` 列中为每个字段指定不同的名称即可。

### 多标签页的使用

虽然不推荐（为了更好的清晰性和简洁性），但可以同时填写多个标签页。它们将按以下顺序处理：

1. 首先处理 *Select & Alter* 标签页：如果已填充，字段将按指定的方式进行过滤、重命名和重新排序
2. 然后处理 *Remove* 标签页，但您无法移除在上一步中未选择的字段：如果您已重命名它们，则必须在 `Fieldname` 列中使用新名称
3. 最后处理 *Meta-data* 标签页：您无法更改未选择（步骤 1）和已移除（步骤 2）的字段，如果您已重命名它们，则必须在 `Fieldname` 列中使用新名称
