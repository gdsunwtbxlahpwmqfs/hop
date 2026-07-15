# ![JSON Input transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/JSI.svg) JSON Input

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## 选项

### 文件选项卡

| 选项 | 描述 |
|---|---|
| Source is from a previous transform a | 选择此项以从先前定义的字段中检索数据源。 |
| Select field | 指定从前一个 transform 中用作数据源的字段名称。 |
| Use field as file names | 选择此项以指示数据源为文件名。 |
| Read source as URL | 选择此项以指示是否应通过 URL 访问数据源。 |
| Do not pass field downstream | 选择此项以从输出流中移除数据源字段。 |
| File or directory |  |
| Regular expression | 指定用于匹配指定目录内文件名的正则表达式。 |
| Exclude regular expression | 指定用于排除指定目录内文件名的正则表达式。 |
| File/Directory | 在 File or directory 中指定后点击 Add 指示的数据源位置。 |
| Wildcard (RegExp) | 在 Regular expression 中指定的通配符。 |
| Exclude wildcard | 在 Exclude regular expression 中指定的排除通配符。 |
| Required | 必需的输入数据源位置。 |
| Include subfolders | 数据源位置中是否包含子文件夹。 |
| Delete | 从表中移除数据源 |
| Edit | 从表中移除数据源并将其返回到 File or directory 选项。 |
| Show filename(s) | 显示成功连接到 JSON Input transform 的数据源文件名。 |

### 内容选项卡

内容选项卡包含以下用于配置要检索哪些数据的选项：

| 选项 | 描述 |
|---|---|
| Ignore empty file | 选择此项以跳过空文件。 |
| Do not raise an error if no files | 选择此项以在没有可处理文件时继续执行。 |
| Ignore missing path | 选择此项以在发生错误时（(1) 没有字段匹配 JSON path 或 (2) 所有值均为 null）继续处理文件。 |
| Default path leaf to null | 选择此项以对缺失路径返回 null 值。 |
| Limit | 指定从 transform 生成的记录数量限制。 |
| Include filename in output | 选择此项以在结果中添加包含文件名的字符串字段。 |
| Rownum in output | 选择此项以在结果中添加包含行号的整数字段。 |
| Add filenames to result | 选择此项以将已处理文件添加到结果文件列表中。 |

### 字段选项卡

字段选项卡显示用于从 JSON 结构中提取值的字段定义。
此选项卡中的表包含以下列：

| 选项 | 描述 |
|---|---|
| Name | 映射到 JSON 输入流中相应字段的字段名称。 |
| Path | JSON 输入流中字段名称的完整路径。Hop 使用 JayWay 库处理 JSON Path 表达式，文档位于 https://github.com/json-path/JsonPath。 |
| Type | 输入字段的数据类型。 |
| Format | 用于转换原始字段格式的可选掩码。 |
| Length | 字段的长度。 |
| Precision | 数字类型字段的小数位数。 |
| Currency | 货币符号（例如 $ 或 €）。 |
| Decimal | 小数点可以是 .（例如 5,000.00）或 ,（例如 5.000,00）。 |
| Group | 分隔符可以是 ,（例如 10,000.00）或 .（例如 5.000,00）。 |
| Trim type | 要应用于字符串的修剪方法。 |
| Repeat | 如果某行为空，则重复上一行对应的值。 |

*选择字段*

点击字段选项卡中的 Select Fields 按钮可打开 Select Fields 窗口。
勾选源文件中要包含在输出中的每个字段旁边的复选框。
此 transform 中选择的所有字段都会被添加到表中。
您可以通过在搜索框中输入字段名来搜索字段。

*从片段中选择字段*

点击 Select fields from snippet 按钮并粘贴整个 JSON 文本，它将填充字段选项卡中的 Name、Path、Type 列。示例：当从先前 REST client 的输出结果中复制 JSON 时，这非常有用。

*日期和时间戳*

对于 Hop 的 Date 和 Timestamp 类型，请使用 Format 列指定输入格式。例如 Date：yyyy-MM-dd。您可以从下拉列表中选择格式，或直接输入格式文本。Hop 使用 SimpleDateFormat (Java Platform SE 8)，文档位于 https://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html

*时间戳格式示例：*

- 格式：yyyy-MM-dd'T'HH:mm:ss.SSSZ，结果：2021-10-26T20:51:43.795+0000

- 格式：2024-04-22T00:00:00.000Z，结果：2024-04-22T00:00:00.000Z

*正则表达式*

您可以在 Path 表达式中使用正则表达式。

*过滤以字母 'a' 开头的 Path 示例：* $.data[?(@=~/a.*/i)]

### 额外输出字段选项卡

额外输出字段选项卡包含以下用于指定要处理的文件附加信息的选项：

| 选项 | 描述 |
|---|---|
| Short filename field | 指定包含不带路径信息但带扩展名的文件名的字段。 |
| Extension field | 指定包含文件扩展名的字段。 |
| Path field | 指定包含操作系统格式路径的字段。 |
| Size field | 指定包含数据大小的字段。 |
| Is hidden field | 指示文件是否隐藏的字段（布尔值）。 |
| Last modification field | 指示文件最后修改日期的字段。 |
| Uri field | 指定包含 URI 的字段。 |
| Root uri field | 指定仅包含 URI 根部分的字段。 |

## 注意事项
在处理输入 JSON 文件时，如果 JSON 记录包含一个或多个含有 null 值的字段，默认情况下 null 值将出现在 transform 输出中。

例如，如果我们有这样一个 JSON 文件
```json
{
  "persons" : [
    {
      "id": "1",
      "name": "name 1"
    },
    {
      "id": "2",
      "name": "name 2"
    },
    {
      "id": "3",
      "name": null
    },
    {
      "id": "4",
      "name": "name 4"
    }
  ]
}
```

使用以下字段定义提取 id 和 Name 字段时：

| 字段名 | Json Path |
|---|---|
| id | `$.persons.*.id` |
| Name | `$.persons.*.name` |

在默认行为下，输出将是

```
id;Name
1;Name 1
2;Name 2
3;null
4;Name 4
```

现在让我们仅选择 `name` 字段，看看会发生什么

```
Name
Name 1
Name 2
Name 4
```

您会注意到，在这种情况下您只得到了 3 个行集（null 行被从结果中省略了）

要更改 Hop 对 JSON 文件中 null 值的处理行为，使 null 值不被纳入 JSON 输出，请更改 `HOP_JSON_INCLUDE_NULLS` 配置变量并将其值设为 N

```
HOP_JSON_INPUT_INCLUDE_NULLS = N
```

重启 Hop 后，当再次运行 pipeline 时，您将得到 3 行结果，因为 null 值将被省略。
