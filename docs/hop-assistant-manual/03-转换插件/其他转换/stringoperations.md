# ![String operations transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/stringoperations.svg) String operations

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称。 |
| In stream field | 指定要转换的字段。 |
| Out stream field | 指定要创建的字段名称。 |
| Trim type | 指定修剪类型：none、left、right 或 both。 |
| Lower/Upper | 指定大写或小写。 |
| Padding | 指定左填充或右填充。 |
| Pad char | 指定填充字符。 |
| Pad Length | 指定填充的长度。 |
| InitCap | 转换为首字母大写。 |
| Escape | 定义 Escape 或 Unescape XML、HTML、使用 CDATA 或 Escape SQL。 |
| Digits | 指定是否移除数字或不对数字做任何操作。 |
| Remove Special character | 指定要移除的特殊字符。 |

## Metadata Injection 支持

此 transform 的所有字段均支持 metadata injection。
您可以使用 ETL Metadata Injection 在运行时将 metadata 传递给您的 Pipeline。

Metadata Injection 的值

| 字段 | 值 |
|---|---|
| In stream fields | 源字段列表 |
| Out stream fields | 目标字段列表 |
| Trim Type | 0 = none |
| Lower/Upper | 0 = none |
| Padding | 0 = none |
| Pad Char | 用于填充的字符 |
| Pad Length | 填充长度 |
| InitCap | 0 = no |
| Escape | 0 = none |
| Digits | 0 = none |
| Remove Special character | 0 = none |
