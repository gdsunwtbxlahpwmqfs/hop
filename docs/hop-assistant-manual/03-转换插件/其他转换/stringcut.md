# ![Strings cut transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/stringcut.svg) Strings cut

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称。 |
| In stream field | 要截取子字符串的字段名称。 |
| Out stream field | 当在此处给定字段名称时，transform 将创建一个包含子字符串的新字段。 |
| Cut from | 指定开始截取子字符串的位置。 |
| Cut to | 指定结束截取子字符串的位置。 |

## 示例

考虑以下字符串（此处显示了正长度和负长度的引用）：
....
|0        |10       |20       |30       |40
The quick brown fox jumps over the lazy dog
   |-40      |-30      |-20      |-10      
....

以下所有示例中的文本被视为输入字符串：

| Cut from | Cut to | 结果 | 备注 |
|---|---|---|---|
| 0 | 9 | `The quick` | 使用 `0` 引用第一个字符 |
| 10 | 19 | `brown fox` | 使用两个正索引从输入开头获取子字符串 |
| 0 | -12 | `the lazy dog` | 使用负索引从输入末尾获取子字符串 |
| -13 | -23 | `jumps over` | 使用两个负索引从输入末尾获取子字符串 |
| 20 | 10 | (null) | 当第一个索引小于第二个索引时，返回 `null` |
| 10 | 10 | (空字符串) | 当两个索引相等时，返回空字符串（不是 `null`） |
| 20 | 100 | `jumps over the lazy dog` | 当第二个索引大于输入长度时，返回的子字符串延伸到输入的末尾 |
| 50 | 100 | (null) | 当第一个索引大于输入长度时，返回 `null` |
| 10 | -10 | `The quick brown fox jumps over the lazy dog` | 当两个索引符号不同时，返回整个输入 |
