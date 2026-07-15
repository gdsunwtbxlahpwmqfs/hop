# ![Text File Input transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/textfileinput.svg) Text File Input

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## Options

### General

| Option | Description |
|---|---|
| Transform Name | Transform 的名称。此名称在单个 Pipeline 中必须唯一。 |

### File 选项卡

下表提供了 File 选项卡上可用功能的详细描述：

| Option | Description |
|---|---|
| File or directory | 此字段指定输入文本文件的位置和/或名称。 |
| Add | 点击将文件和通配符添加到 Selected files 列表。 |
| Browse | 点击从文件浏览器中选择文件。 |
| Regular expression | 指定要用于在前面选项指定的目录中选择文件的正则表达式。 |
| Exclude Regular Expression | 指定要用于从指定目录中排除文件的正则表达式。 |
| Selected Files | 此表格包含选定文件（或通配符选择）的列表，以及指定文件是否为必需的属性。 |
| Delete | 点击从列表中删除选定文件。 |
| Edit | 点击修改列表中的选定文件。 |
| Show filenames(s)... | 显示基于当前选定文件定义将加载的所有文件列表。 |
| Show file content | 显示选定文件的内容。 |
| Show content from first data line | 仅显示选定文件从第一行数据开始的内容。 |

**使用正则表达式选择文件**

Text File Input transform 可以通过正则表达式形式的通配符搜索文件。
正则表达式比使用 '*' 和 '?' 通配符更强大。
以下是几个正则表达式示例：

| Filename | Regular Expression | Files selected |
|---|---|---|
| /dirA/ | .userdata.\.txt | 查找 /dirA/ 中名称包含 userdata 且以 .txt 结尾的所有文件 |
| /dirB/ | AAA.* | 查找 /dirB/ 中名称以 AAA 开头的所有文件 |
| /dirC/ | [ENG:A-Z][ENG:0-9].* | 查找 /dirC/ 中名称以大写字母开头后跟一个数字的所有文件（A0-Z9） |

**从前一个 Transform 接受文件名**

此选项允许您在另一个 Transform（如 "Get Filenames"）中构建文件名，然后将其传递给此 Transform，可以作为输入流中的字段或直接从其他 Transform 的输出读取。
这使您能够使用来自任何来源的文件名，包括文本文件和数据库表。

| Option | Description |
|---|---|
| Accept filenames from previous transforms | 启用从前一个 Transform 获取文件名的选项。 |
| Pass through fields from previous transform | 启用此选项可将进入 Transform 的所有先前字段添加到 Transform 输出中。 |
| Transform to read filenames from | 要从中读取文件名的 Transform 名称。 |
| Field in the input to use as filename | 指定输入流中包含文件名的字段。 |

### Content 选项卡

Content 选项卡允许您指定正在读取的文本文件的格式。
以下是与此选项卡相关的选项列表：

| Option | Description |
|---|---|
| File type | 文件中包含的文本类型。可以是 CSV 或 Fixed length。选择 CSV 并不要求数据以逗号分隔，它表示文件有一个必须定义的分隔符。 |
| Separator | 分隔单行文本中各字段的一个或多个字符。 |
| Insert TAB | 点击在 Separator 框中插入制表符。 |
| Enclosure | 可选地，一对用于封装字段值的字符。期望包裹字符出现在字符串的开头和结尾。但是，如果相同字符出现在字符串中间，该值将被拆分为两个字段值。为防止这种情况并确保值保持完整，您可以在字符串中间将包裹字符加倍，此时它充当转义字符，值不会被拆分。 |
| Allow breaks in enclosed fields? | 如果启用，包裹字段中的换行不会导致字段值结束。如果禁用，或换行出现在非包裹字段中，字段值在换行处结束，这可能影响 Transform 的输出。如果您使用 Get Fields 按钮，并且某个字段因换行而结束，该字段是 Get fields 功能识别的最后一个字段。 |
| Escape | 指定一个或多个字符，用于标识在字段值中应原样出现的字符，即使它们可能被解释为控制字符或格式字符。 |
| Prepend filename to headers | 启用以将文件名添加到文件中的每个字段。这在连接数据时可以帮助明确每个字段来自哪个文件。 |
| Header | 如果文本文件有标题行（文件中的前几行），请启用。这些通常确定 Transform 中使用的列名。如果在不含标题的文件上设置此项，第一行的数据值将被解释为标题，这可能会产生意外结果。在这种情况下，如果字段为空，将被命名为 EmptyField_n，其中 n 是列在行中的位置。请始终检查字段集以确保列名和数据类型已被正确解释。 |
| Number of header lines | 指定文件中包含标题的行数。 |
| Footer | 如果文本文件有页脚行（文件中的最后几行），请启用。这些通常提供有关文件的附加数据，如创建者或创建日期。页脚通常不被处理。 |
| Number of footer lines | 指定包含页脚的行数。 |
| Wrapped lines? | 如果您处理超过特定页面限制而换行的数据行，请启用。标题和页脚永远不会被视为换行。 |
| Number of times wrapped | 指定值可以换行的行数。超过此值的行被视为单独的数据值。 |
| Paged layout (printout)? | 如果此文本文件将被打印，请启用。 |
| Number of lines per page | 指定每页将打印的最大行数。最后一页可能少于此数量。 |
| Document header lines | 指定标题中要跳过打印的行数。 |
| Compression | 指定用于压缩文件数据的压缩算法，或选择 None 以保持数据不压缩。 |
| No empty rows | 如果启用，空行不会发送到后续 Transform。 |
| Include filename in output | 如果您希望将文件名保存到输出流中的字段，请启用。 |
| Filename field name | 包含文件名的字段名称 |
| Rownum in output? | 如果您希望行号成为输出的一部分，请启用 |
| Row number field name | 包含行号的字段名称 |
| Rownum by file? | 允许按文件重置行号。否则行号字段包含所有文件中所有行的计数。 |
| Format | 可以是 DOS、UNIX 或 mixed。 |
| Encoding | 指定要使用的文本文件编码；留空以使用系统上的默认编码。 |
| Limit | 设置从文件中读取的行数；0 表示读取所有行。 |
| Be lenient when parsing dates? | 如果您要严格解析日期字段，请禁用；如果启用宽松解析，如 Jan 32nd 这样的日期将变为 Feb 1st。 |
| The date format Locale | 此区域设置用于解析完整书写的日期，如 "February 2nd, 2006"；在法语（fr_FR）区域设置的系统上解析此日期将不起作用，因为 February 在该区域设置中称为 Février。 |
| Add filenames to result | 将文件名添加到内部文件名结果集中。 |

### Error Handling 选项卡

Error handling 选项卡允许您指定 Transform 在发生错误时的反应方式。
下表描述了 Error handling 的可用选项：

| Option | Description |
|---|---|
| Ignore errors? | 如果您要在解析期间忽略错误，请启用。这将激活此选项卡上的其余选项。 |
| Skip error files? | 启用以在文件无法加载或包含错误时移至下一个文件。您可以在输出中报告这些跳过的文件。 |
| Error file field name | 向输出流添加一个字段，包含处理失败的文件名称。 |
| File error message field name | 向输出流添加一个字段，包含为文件返回的错误消息。 |
| Skip error lines? | 如果您要跳过包含错误的任何行，请启用。 |
| Error count field name | 向输出流添加一个字段，包含该行上的错误数量 |
| Error fields field name | 向输出流添加一个字段，包含发生错误的字段名称 |
| Error text field name | 向输出流添加一个字段，包含已发生的解析错误的描述 |
| Warnings file directory | 当生成警告时，它们被写入此目录中的文件。 |
| Extension | 指定警告文件的文件扩展名。 |
| Browse | 点击导航到警告目录或选择用于保存警告的文件（如果已存在）。 |
| Error files directory | 当发生与非存在或不可访问文件相关的错误时，它们被写入此目录中的文件。 |
| Extension | 指定错误文件的文件扩展名。 |
| Browse | 点击导航到错误目录或选择用于保存错误的文件（如果已存在）。 |
| Failing line numbers files directory | 当某行发生解析错误时，行号被写入此目录中的文件。 |
| Extension | 指定失败行文件的文件扩展名。 |
| Browse | 点击导航到失败行目录或选择用于保存失败行的文件（如果已存在）。 |

### Filters 选项卡

Filters 选项卡允许您指定要在文本文件中跳过的行。
下表描述了用于定义过滤器的可用选项：

| Option | Description |
|---|---|
| Filter string | 要在数据值中搜索的字符串。每个数据值被视为一行文本，无论其有多长。 |
| Filter position | 行中搜索过滤字符串的位置。 |
| Stop on filter | 如果遇到过滤字符串时要停止处理当前文本文件，请在此处指定 Y。 |
| Positive match | 如果您要处理匹配过滤器的行，请在此处指定 Y；如果要忽略这些行，请指定 N。 |

### Fields 选项卡

Fields 选项卡允许您指定从文本文件读取的字段的名称和格式信息。您可以选择使用 [Schema Definition](metadata-types/static-schema-definition.md) 或手动定义所需字段的布局。

`ignore manual fields` 会忽略在 Transform 字段布局中手动定义的所有字段，仅使用 [Schema Definition](metadata-types/static-schema-definition.md) 中指定的布局。

可用选项包括：

| Option | Description |
|---|---|
| Schema Definition | 要引用的 [Schema Definition](metadata-types/static-schema-definition.md) 名称。 |
| Name | 要包含在输出中的字段名称。 |
| Type | 字段的数据类型。可以是 String、Date 或 Number |
| Format | 有关格式符号的完整描述，请参见 Number Formats。 |
| Position | 处理 'Fixed' 文件类型时需要。 |
| Length | 对于 Number：数字中有效数字的总位数；对于 String：字符串的总长度；对于 Date：字符串打印输出的长度（例如 4 仅返回年份）。 |
| Precision | 对于 Number：显示的浮点数字位数 |
| Currency | 用于解释货币值，如 $10,000.00 或 E5.000,00 |
| Decimal | 用作数字小数点的字符。通常是 "."（10,000.00）或 ","（5.000,00） |
| Grouping | 用作数字千位分隔符的字符。通常是 ","（10,000.00）或 "."（5.000,00） |
| Null if | 指定一个值，在输出中将替换为 null 值。 |
| Default | 当文本文件中的字段未指定（为空）时的默认值 |
| Trim type | 指定如何修剪列值中的空白。可以是 left（删除前导空格）、right（删除尾随空格）、both 或 none。 |
| Repeat | 如果此行中的相应值为空，则重复上一行的非空值。 |

**Number Formats**

以下关于 Number 格式的信息取自 Sun Java API 文档，位于 http://java.sun.com/j2se/1.4.2/docs/api/java/text/DecimalFormat.html。
有关此 Transform 中使用的有效数字格式的更多信息，请查看 Number Formatting Table。

| Symbol | Location | Localized | Meaning |
|---|---|---|---|
| 0 | Number | Yes | 数字 |
| # | Number | Yes | 数字，零显示为不存在 |
| . | Number | Yes | 小数分隔符或货币小数分隔符 |
| - | Number | Yes | 减号 |
| , | Number | Yes | 分组分隔符 |
| E | Number | Yes | 科学计数法中分隔尾数和指数；前缀或后缀中无需引号 |
| ; | Sub pattern boundary | Yes | 分隔正负子模式 |
| % | Prefix or suffix | Yes | 乘以 100 并显示为百分比 |
| \u2030 | Prefix or suffix | Yes | 乘以 1000 并显示为千分比 |
| € (\u00A4) | Prefix or suffix | No | 货币符号，被货币符号替换。 |
| ' | Prefix or suffix | No | 用于在前缀或后缀中引用特殊字符，例如，"'#'#" 将 123 格式化为 "#123"。 |

**科学计数法**

在模式中，指数字符紧跟一个或多个数字字符表示科学计数法（例如，"0.###E0" 将数字 1234 格式化为 "1.234E3"。

**日期格式**

关于日期格式的信息取自 Sun Java API 文档，位于：

http://java.sun.com/j2se/1.4.2/docs/api/java/text/SimpleDateFormat.html。
有关此 Transform 中使用的有效日期格式的更多信息，请查看 Date Formatting Table。

| Letter | Date or Time Component | Presentation | Examples |
|---|---|---|---|
| M | Month in year | Month | July; Jul; 07 |
| w | Week in year | Number | 27 |
| W | Week in month | Number | 2 |
| D | Day in year | Number | 189 |
| d | Day in month | Number | 10 |
| F | Day of week in month | Number | 2 |
| E | Day in week | Text | Tuesday; Tue |
| a | Am/pm marker | Text | PM |
| H | Hour in day (0-23) | Number 0 |  |
| k | Hour in day (1-24) | Number 24 |  |
| K | Hour in am/pm (0-11) | Number 0 |  |
| h | Hour in am/pm (1-12) | Number 12 |  |
| m | Minute in hour | Number 30 |  |
| s | Second in minute | Number 55 |  |
| S | Millisecond | Number 978 |  |
| z | Time zone | General time zone | Pacific Standard Time; PST; GMT-08:00 |
| Z | Time zone | RFC 822 time zone | -0800 |

## Additional Output Fields 选项卡

| Option | Description |
|---|---|
| Short filename field | 包含不带路径信息但带扩展名的文件名的字段名称。 |
| Extension field | 包含文件名扩展名的字段名称。 |
| Path field | 包含操作系统格式文件路径的字段名称。 |
| Size field | 包含文件大小的字段名称。 |
| Is hidden field | 包含文件是否隐藏（Boolean）的字段名称。 |
| Uri field | 包含文件 URL 的字段名称。 |
| Root uri field | 仅包含 URI 根部分的字段名称。 |

## 按钮

| Function/Button | Description |
|---|---|
| Show filenames | 显示所有选定文件的列表。 |
| Show file content | 显示文本文件的前几行。 |
| Show content from first data line | 帮助您定位具有多个标题行等的复杂文本文件中的数据行。 |
| Get fields | 按 File 和 Content 选项卡中的设置检索字段。 |
| Preview rows | 预览此 Transform 生成的行，应用过滤器和字段格式化。 |
