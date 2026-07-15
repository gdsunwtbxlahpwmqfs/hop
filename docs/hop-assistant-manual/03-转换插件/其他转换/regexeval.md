# ![Regex Evaluation transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/regexeval.svg) Regex Evaluation

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 用法

### 模式匹配

此 transform 的主要用途是检查输入字段是否与给定模式匹配：会向流中添加一个布尔字段，指示是否匹配。

该模式旨在匹配整个输入字段，而不仅仅是其中的一部分。例如，给定输入：

```
"Author, Ann" - 53 posts
```
正则表达式 `\d* posts` 不会匹配，即使输入的一部分（`53 posts`）确实与模式匹配。要获得实际匹配，需要在模式中添加 `.*`：

```regexp
.*\d* posts
```

### 捕获文本

此 transform 还可以捕获输入的部分内容并将其存储到流的新字段中：只需在正则表达式中添加常用的分组操作符（简单括号）即可。

使用与上面相同的输入文本，创建一个包含两个捕获组的正则表达式：

```regexp
^"([^"]*)" - (\d*) posts$
```

Transform 将捕获值 `Author, Ann` 和 `53`，因此您可以在 Pipeline 中创建两个新字段（例如一个用于名称，一个用于帖子数量）。

## 选项

### 常规

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称。 |

### 捕获组字段

| 选项 | 描述 |
|---|---|
| New field | 从正则表达式生成的新字段名称。 |
| Type | 数据类型。 |
| Length | 字段的长度。 |
| Precision | 数字类型字段的小数位数。 |
| Format | 用于转换原始字段格式的可选掩码。 |
| Group | 分组符号可以是 ","（例如 10,000.00）或 "."（例如 5.000,00） |
| Decimal | 用作小数点的字符。 |
| Currency | 货币符号（例如 $ 或 €） |
| Null If | 将此值视为 null。 |
| Default | 当传入文件中的字段未指定（为空）时的默认值。 |
| Trim | 应用于字符串的修剪方法。 |

### 设置 选项卡

Settings 选项卡包含以下选项：

| 选项 | 描述 |
|---|---|
| Field to evaluate | 指定来自传入 Hop 流中要与正则表达式匹配的字段名称。 |
| Result field name | 指定输出字段的名称。 |
| Create fields for capture groups | 选择此项可根据正则表达式中的捕获组创建新字段。 |
| Replace previous fields | 选择此项可使用为捕获组字段名创建的字段替换传入 Hop 流中的同名字段。 |
| Regular expression | 指定您的正则表达式。 |
| Use variable substitution | 选择此项可在评估正则表达式模式之前将变量引用展开为其值。 |

### 测试正则表达式

您可以使用以下正则表达式评估窗口，针对三个不同的输入字符串测试您的正则表达式。
如果您的表达式包含组字段，请在比较部分输入一个字符串，字符串下方的选项将根据您的组进行拆分。

| 选项 | 描述 |
|---|---|
| Please enter a new regular expression or modify | 指定您的正则表达式。 |
| Values to test | 指定用于测试字符串的值（Value1、Value2 或 Value3）。 |
| Capture from value | 显示捕获字符串的值。 |
| Captured fields | 显示捕获组的值。 |

### 内容 选项卡

Content 选项卡包含以下选项：

| 选项 | 描述 |
|---|---|
| Ignore differences in Unicode encodings | 选择此项可忽略不同的 Unicode 字符编码。 |
| Enables case-insensitive matching a | 选择此项可使用不区分大小写的匹配。 |
| Permit whitespace and comments in pattern a | 选择此项可忽略空格和以 `#` 开头的嵌入注释直到行尾。 |
| Enable dotall mode a | 选择此项可使点字符表达式匹配包含行终止符。 |
| Enable multiline mode a | 选择此项可匹配输入序列中行的开头 `^` 或结尾 `$`。 |
| Enable Unicode-aware case folding a | 将此选项与 Enables case-insensitive matching 选项结合使用，以执行符合 Unicode 标准的不区分大小写的匹配。 |
| Enables Unix lines mode a | 选择此项可仅在 `.`、`^` 和 `$` 的行为中识别 Unix 行终止符。 |

## 示例

### 子文本匹配

如前所述，该模式旨在匹配整个输入字段，即当提供的输入 _就是_ 该模式时。

如果您只需要测试输入是否 _包含_ 该模式，则需要调整正则表达式使其匹配整个输入字段。您还应该包含分组操作符（括号）来获取您打算匹配的子文本，例如：

- 输入数据：`THIS IS A TITLE <PROCESSING_TAG>`
- 正则表达式 1：`<.*>` -> 不返回匹配，因为模式不匹配整个输入
- 正则表达式 2：`.*(<.*>)` -> 返回匹配，您可以通过分组操作符捕获值 `<PROCESSING_TAG>`

因此，您可以将行定界操作符 `^` 和 `$` 视为正则表达式中隐含的：上面的示例分别等价于 `^<.*>$` 和 `^.*(<.*>)$`。

### 嵌套捕获组

假设您的输入字段包含类似 `"Author, Ann" - 53 posts.` 的文本值。

以下正则表达式创建了四个捕获组，可用于解析出不同部分：

```regexp
^"(([^"]`), ([^"]`))" - (\d+) posts\.$
```

此表达式创建以下四个捕获组，它们将成为输出字段：

| 字段名 | 正则表达式段 | 值 |
|---|---|---|
| Fullname | `(([^"]`), ([^"]`))` | `Author, Ann` |
| Lastname | `([^"]+)` （第一次出现） | `Author` |
| Firstname | `([^"]+)` （第二次出现） | `Ann` |
| Number of posts | `(\d+)` | `53` |

在此示例中，每个捕获组都必须有对应的字段定义。

如果正则表达式中的捕获组数量与指定的字段数量不匹配，transform 将失败并在日志中写入错误。
