# ![Get Data From XML transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/GXD.svg) Get Data From XML

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

### Files 选项卡

Files 选项卡用于定义要读取的 XML 文件的位置。
下表包含 Files 选项卡的选项。

| Option | Description |
|---|---|
| Transform name | Transform 的名称。这在 Pipeline 中必须唯一。 |
| XML source is defined in a field | 要使用的 XML 数据存在于输入流的字段中。 |
| XML source is a filename | XML 数据可从文件中获取并读取。 |
| Read source as URL | 从 URL 指定的位置读取 XML 数据。 |
| Get XML source from a field | 指定包含 XML 数据、文件名或 URL 的字段。 |
| File or directory | 指定输入文本文件的位置和/或名称。 |
| Add | 点击将文件/目录/通配符组合添加到下方的已选文件列表（网格）中。 |
| Browse | 点击浏览文件所在位置。 |
| Regular expression | 指定用于在上一选项指定的目录中选择多个文件的正则表达式。 |
| Selected Files | 包含已选文件（或通配符选择）的列表，以及指定文件是否为必需的属性。 |
| Delete | 点击移除表中的所选文件。 |
| Edit | 点击修改表中的所选文件。 |
| Show filename(s)... | 显示基于当前所选文件定义将加载的所有文件列表 |

### Content 选项卡

| Option | Description |
|---|---|
| Loop XPath | 对于 XML 文件或数据中的每个匹配条目，向输出添加一行数据。 |
| Encoding | XML 文件名编码，以防 XML 文档中未指定编码。 |
| Namespace aware? | 如果 XML 文档在解析时需要考虑命名空间则启用。 |
| Ignore comments? | 启用以在解析时忽略 XML 文档中的所有注释。 |
| Validate XML? | 启用以在解析前验证 XML。 |
| Use token | 启用以使用 token 验证 XML。 |
| Ignore empty file | 启用以忽略任何无内容的文件。这些不是有效的 XML 文档。 |
| Do not raise an error if no files | 启用以在未找到文件时不执行任何操作。否则返回错误。 |
| Limit | 指定要返回的最大行数。零 (0) 返回所有行。 |
| Prune path to handle large files | 指定一个路径（类似于 Loop XPath），用于处理 XML 文件中的数据块。每个匹配值定义一个被读取和处理的数据块。使用 prune path 可加速大文件的处理。 |
| Include filename in output? | 允许指定字段名以在此 Transform 的输出中包含文件名（String）。 |
| Filename fieldname | 用于读取文件名值的字段。 |
| Rownum in output? | 允许指定字段名以在此 Transform 的输出中包含行号（Integer）。 |
| Rownum fieldname | 用于读取行号值的字段。 |
| Add files to result filename | 将读取的 XML 文件名添加到此 Pipeline 的结果中。内存中保留一个唯一列表，可在 Workflow 中的下一个 action 中使用，例如在另一个 Pipeline 中。 |

### Fields 选项卡

| Option | Description |
|---|---|
| Name | 输出字段的名称 |
| XPath | 要读取的元素节点或属性的路径 |
| Element | 要读取的元素类型：Node 或 Attribute |
| Result Type a | "Value of" 或 "Single node" |
| Type | 要转换为的数据类型 |
| Format | 数据类型转换中使用的格式或转换掩码 |
| Length | 输出数据值中的最大字符数 |
| Precision | 用于在输出数据中显示数字的小数位数 |
| Currency | 用于货币值的货币符号。 |
| Decimal | 用于浮点数的数字小数符号。 |
| Group | 用于在数据中分隔千位的数字分组符号 |
| Trim type | 如何从值中移除空白字符，可以是从左侧（裁剪前导空格）、右侧（裁剪尾随空格）、两侧（裁剪所有空白），或不裁剪（不执行裁剪） |
| Repeat | 如果列值为空（null），则重复前一行的列值 |
| Get fields | 点击以输入流中的字段填充表格。 |
| Select fields from snippet | 点击以与 Loop Xpath 和 XML 文档对应的字段填充表格，该 XML 文档需在弹出对话框中提供。 |

===附加输出字段选项卡

| Option | Description |
|---|---|
| Short filename field | 用于存储文件名的字段，不含路径或文件扩展名。 |
| Extension field | 用于存储文件扩展名的字段。 |
| Path field | 用于存储文件路径的字段。 |
| Size field | 用于存储文件大小的字段。 |
| Is hidden field | 用于指定文件是否为隐藏的字段。 |
| Last modification field | 用于存储文件最后修改日期的字段。 |
| Uri field | 用于存储 XML 文档源 URL 的字段。 |
| Root uri field | 用于存储 XML 文档命名空间 URL 的字段，取自根元素 |
