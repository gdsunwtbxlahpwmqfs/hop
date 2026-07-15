# ![JSON Normalize Input transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/JsonNormalizeInput.svg) JSON Normalize Input

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Apache Spark, Flink, Dataflow | 未针对此 Transform 验证；在依赖分布式执行前请在您的环境中进行验证。 |

## 选项

### File 选项卡
与 JSON Input 概念相同：从文件/文件夹、URL 或前一个 Transform 的字段获取数据（`Source is from a previous transform`）。详见 [JSON Input – File tab](../输入类/jsoninput.md#_file_tab.md)。

### Content 选项卡
除了常规文件和行限制选项（见 [JSON Input – Content tab](../输入类/jsoninput.md#_content_tab.md)）外，Content 选项卡还增加了：

| Option | Description |
|---|---|
| Record JsonPath | 必须评估为记录*数组*的 JsonPath。每个元素成为一行输出。示例：对于 `{ "orders": [ {...}, ... ] }` 使用 `$.orders[*]`，或当文件根为数组时使用 `$[*]`。 |
| Field separator | 在展平嵌套对象时放置在路径段之间的字符（默认 `.`）。 |
| Max flatten depth | 要展平的最大对象嵌套深度。`-1` 表示无限制。 |
| Array handling | 如何处理展平时遇到的 JSON 数组：字符串化（默认）、仅单个元素或报错。 |
| Beyond max depth | 当达到最大深度时：字符串化、省略或报错。 |
| Ignore missing field paths | 如果启用，Fields 选项卡上列出的字段在某行中缺失时将设置为 null，而不是使该行失败。 |

### Fields 选项卡

Fields 选项卡列出了要从每个*展平后*的记录（由 **Record JsonPath** 选择的每个元素）中提取的输出字段。
该表包含以下列：

| Option | Description |
|---|---|
| Name | Hop 流中输出字段的名称。 |
| Path | 嵌套对象合并为点号（或自定义分隔符）路径后，每个记录*内*值的展平键。这*不是*整个文档的 JsonPath——那是 Content 选项卡上 **Record JsonPath** 的作用。示例：`orderId`、`customer.name`。该值必须与该行通过展平生成的键匹配（除非启用 **Ignore missing field paths**，在这种情况下缺失的键产生 null）。此列不使用通配符、过滤器和正则表达式——仅使用展平字段的路径字符串。 |
| Type | 输入字段的数据类型。 |
| Format | 用于转换原始字段格式的可选掩码。 |
| Length | 字段的长度。 |
| Precision | 数字类型字段的浮点位数。 |
| Currency | 货币符号（例如 $ 或 €）。 |
| Decimal | 小数点可以是 .（例如 5,000.00）或 ,（例如 5.000,00）。 |
| Group | 分组分隔符可以是 ,（例如 10,000.00）或 .（例如 5.000,00）。 |
| Trim type | 要应用于字符串的裁剪方法。 |
| Repeat | 如果某行为空，则重复上一行的对应值。 |

*选择字段*

在 Fields 选项卡中点击 *Select fields* 以打开 Select Fields 窗口。
选择您想要在输出中包含的每个字段旁边的复选框。
此处选择的所有字段都将添加到表中。
您可以在搜索框中输入字段名来搜索字段。

*从片段选择字段*

点击 *Select fields from snippet* 并粘贴 JSON 文本——通常是*一个代表性对象*（您的 **Record JsonPath** 选择的数组的一个元素）——以填充 Fields 选项卡中的 *Name*、*Path* 和 *Type* 列。这有助于在应用 Content 选项卡设置（分隔符、深度、数组处理）后发现展平路径。

*日期和时间戳*

对于 Hop 的 *Date* 和 *Timestamp* 类型，使用 *Format* 列指定输入格式。例如，对于 Date：`yyyy-MM-dd`。您可以从下拉列表中选择格式或直接输入格式文本。Hop 使用 [SimpleDateFormat,window=_blank](https://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat)（Java 8）。

*时间戳格式示例：*

- Format: yyyy-MM-dd'T'HH:mm:ss.SSSZ and Result: 2021-10-26T20:51:43.795+0000

- Format: 2024-04-22T00:00:00.000Z and Result: 2024-04-22T00:00:00.000Z

### Additional output fields 选项卡

Additional output fields 选项卡包含以下选项，用于在 Transform 从文件读取时添加文件 metadata：

| Option | Description |
|---|---|
| Short filename field | 指定包含不带路径信息但带扩展名的文件名的字段。 |
| Extension field | 指定包含文件扩展名的字段。 |
| Path field | 指定包含操作系统格式路径的字段。 |
| Size field | 指定包含数据大小的字段。 |
| Is hidden field | 指定指示文件是否隐藏的字段（Boolean）。 |
| Last modification field | 指定指示文件最后修改日期的字段。 |
| Uri field | 指定包含 URI 的字段。 |
| Root uri field | 指定仅包含 URI 根部分的字段。 |
