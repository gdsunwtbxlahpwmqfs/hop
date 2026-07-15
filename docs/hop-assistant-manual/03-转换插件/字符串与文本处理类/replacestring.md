# ![Replace in String transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/replaceinstring.svg) Replace in String

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## Options

| Option | Description |
|---|---|
| Transform name | Transform 的名称。此名称在一个 plugin 中必须唯一。 |
| In stream field | 需要进行替换的字段。 |
| Out stream field | 结果字段。如果 Out stream field 为空，将替换 In stream field 中的值。否则，替换值将输出到新字段中。 |
| use RegEx | 指定是否使用正则表达式来指定要替换的值。此值必须为 Y 或 N。 |
| Search | 搜索特定模式。可以使用通配符（*）匹配多个字符。如果使用正则表达式，请在此字段中指定模式。 |
| Replace with | 指定用于替换搜索模式的值。如果使用正则表达式，请在此处指定字符串的替换部分。 |
| Set empty string? | 指定字段值是否替换为空字符串。此值必须为 Y 或 N。 |
| Replace with field | 使用您指定字段中的值替换搜索模式。 |
| Whole word | 指定是否必须整词匹配搜索模式才能进行替换。此值必须为 Y 或 N。 |
| Case sensitive | 指定搜索是否区分大小写。此值必须为 Y 或 N。 |
| Is Unicode | 指定搜索是否使用 Unicode 编码。此值必须为 Y 或 N。 |

## 示例

您可以按如下方式提取字符串的某些部分：

- regex = ^([0-9]{openvar}4{closevar})([0-9]{openvar}7{closevar})$
- replace with = $2

这将用最后 7 位数字替换所有 11 位数字。
