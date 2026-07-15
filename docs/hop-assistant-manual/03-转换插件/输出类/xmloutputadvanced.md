# ![XML Output (Advanced) transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/AXO.svg) XML Output (Advanced)

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Not Supported, 24](../../assets/images/cross.svg) |
| Flink | ![Not Supported, 24](../../assets/images/cross.svg) |
| Dataflow | ![Not Supported, 24](../../assets/images/cross.svg) |

## Options

对话框分为三个选项卡：*File*、*Content* 和 *XML Tree*。

### File 选项卡

| Option | Description |
|---|---|
| Transform name | Transform 的名称。 |
| Output | XML 的发送目标：*Write to file*、*Output XML as field* 或 *Write to file and output XML as field*（两者兼有）。在 Pipeline XML 中存储为代码 `writetofile`、`outputvalue` 和 `both`。 |
| XML output field | 接收完整 XML 文档的字段名称（启用拆分时每个拆分一个值）。当 *Output* 为 *Output XML as field* 或 *both* 时使用。 |
| Include input fields in output | 当 *Output* 包含 XML 字段时：如果启用（默认），每行发出的数据包含所有输入字段加上 XML 字段；如果禁用，仅发出 XML 字段（适用于链式操作的窄流）。 |
| Filename | 输出 XML 文件的基本名称（不含扩展名）。支持 VFS URI。当 *Output* 写入文件时必需。 |
| Extension | 文件扩展名（不含前导点）。默认为 `xml`。 |
| Encoding | 输出文件的字符编码。默认为 `UTF-8`。 |
| Include transform copy number in filename | 将 Transform 副本编号追加到文件名中。 |
| Include date in filename | 将系统日期（`yyyyMMdd`）追加到文件名中。 |
| Include time in filename | 将系统时间（`HHmmss`）追加到文件名中。 |
| Specify custom date/time format | 使用自定义日期/时间模式代替上面的日期/时间切换开关。 |
| Date/time format | Java `SimpleDateFormat` 模式，在自定义格式切换开关打开时使用。 |
| Split every N rows | 每个文件的最大行数，超过后滚动到新拆分，或者当 *Output* 包含 XML 字段时每个完成的 XML 字段段。`0` = 不拆分。 |
| Zip output file | 将每个输出文件包装在 zip 归档中（每个文件一个条目）。生成的 XSD 写在归档旁边，而不是内部。 |
| Do not open new file at start | 将文件创建推迟到收到第一个输入行时。 |
| Do not create file if no rows | 如果从未写入任何行，则在运行结束时删除输出文件。 |
| Add filename to result | 将生成的文件添加到 Pipeline 的结果文件列表中（仅在写入至少一行后）。 |
| Show file name(s) ... | 弹出从当前设置构建的示例文件名列表。 |

### Content 选项卡

| Option | Description |
|---|---|
| Compact | 抑制元素之间的空格和 EOL；适用于对字节大小敏感的输出。 |
| Blank line after XML declaration | 在 `<?xml ?>` 声明之后添加空行。 |
| Emit empty elements | 为没有值且没有子元素的元素发出开/关标签对。 |
| Emit attribute when value is null | 即使源值为 `null`，也发出属性。 |
| Emit attribute when no field is mapped | 发出没有映射字段的属性，使用其默认值。 |
| Trim leading/trailing whitespace | 在发出文本值之前进行修剪。 |
| Default decimal separator | 数值的默认小数分隔符；每个节点的设置仍然优先。 |
| Default grouping separator | 数值的默认分组分隔符；每个节点的设置仍然优先。 |
| Generate sibling XSD file | 在每个输出文件（或每个拆分）旁边写入同级的 `.xsd` schema。Schema 从配置的 XML 树和上游行 metadata 派生。 |
| DOCTYPE root element / system / public identifier | 在 XML 声明和根元素之间发出 `<!DOCTYPE ...>` 声明。 |
| XSL stylesheet href / type | 发出 `<?xml-stylesheet ?>` 处理指令。为空时 Type 默认为 `text/xsl`。 |

### XML Tree 选项卡

XML Tree 选项卡是输出结构的可视化设计器。左窗格列出了从前一个 Transform 接收的输入字段；右窗格分为目标树（顶部）和当前选定节点的属性窗格（底部）。

#### 使用树

- 点击 *Get fields* 以（重新）从前一个 Transform 加载输入字段。
- 从左窗格拖动字段并放到树中的元素上。将创建一个新子元素，该字段名称已填充，`mappedField` 已预填。
- 使用树上方的工具栏（或右键菜单）：
** *`Element* / *`Attribute* / *+ Fragment*：在选定元素下添加所选类型的子节点。
** *Delete*：删除选定节点及其后代（根节点无法删除）。
** *Up* / *Down*：在同级节点中重新排序选定节点。
** *Loop*：切换循环标志。树中必须恰好有一个元素携带此标志；在不同的节点上切换循环会自动清除其他节点的标志。
** *Group-by*：在循环元素的祖先上切换 group-by 标志。
- 选择节点会填充树下方的 *Properties* 表单。编辑立即传播到模型。

#### 节点属性

| Property | Description |
|---|---|
| Name | 元素或属性的本地名称。 |
| Namespace URI | 可选的 XML 命名空间 URI。在根元素上设置时，它成为默认命名空间，并写入生成的 XSD 作为 `targetNamespace`。 |
| Kind | `Element`、`Attribute` 或 `DocumentFragment`。后者解析源字段的值并将其作为 XML 节点插入，而不是转义文本。 |
| Mapped field | 其值提供此节点内容的输入字段。对于属性和元素，它设置值；对于标记为 `Group-by` 的节点，它仅标识分组键。 |
| Default value | 当 `Mapped field` 为空（或其值为 `null`）时使用的静态文本。 |
| Format / Length / Precision / Currency / Decimal / Grouping | 将字段值转换为字符串时使用的每节点值 metadata 覆盖。每节点设置优先于全局 *Default decimal/grouping separator*。 |
| Loop | 将此元素标记为行循环元素。必须恰好有一个元素携带此标志。 |
| Group-by | 将此元素标记为循环的 group-by 祖先。具有相同 `Mapped field` 值的连续行共享一个出现。 |
| Force create | 即使值为 `null` 也输出此节点（设置时使用默认值）。 |
| Remove outer wrapper (duplicate parent tag) | 仅适用于 `DocumentFragment` 节点：当片段的根元素重复父元素名称时，剥离该外部包装，以便内部 XML 在没有重复包装的情况下插入（例如，当从上游 XML Output (Advanced) 的 XML 馈入子片段节点时）。 |

## 链式和输出到字段

当 *Output* 为 *Output XML as field* 或 *both* 时，Transform 会将配置的 *XML output field* 添加到每个完成的文档（或每个拆分）的流中。第二个 XML Output (Advanced) transform 可以使用 *DocumentFragment* 节点映射该字段。如果内部 XML 已有根标签会与目标树中的父元素重复，请在片段上使用 *Remove outer wrapper*。

## Group-by 行为

为了使 group-by 机制正确折叠，*输入行必须已按 group-by 键排序*。如有需要，请在上游使用 Sort Rows transform。当键值更改时，打开的组元素被关闭，并以新键打开一个新的组元素。

## XSD 生成

当启用 *Generate sibling XSD file* 时，Transform 会在每个输出文件（或拆分）旁边写入一个 `.xsd` schema。该 schema：

- 声明一个与配置树的根匹配的全局元素；
- 嵌套与具有子元素或属性的元素对应的复杂类型；
- 在循环元素和每个 group-by 祖先上设置 `maxOccurs="unbounded"`；
- 将属性渲染为 `xs:attribute` 声明（当源节点为 `Force create` 时使用 `use="required"`）；
- 将文档片段节点渲染为 `<xs:any processContents="skip"/>` 占位符；
- 按 Hop 值类型映射到 XSD 内置类型：integer → `xs:long`、number/big-number → `xs:decimal`、date/timestamp → `xs:dateTime`、boolean → `xs:boolean`、binary → `xs:base64Binary`，其他类型 → `xs:string`；
- 设置时使用根节点的命名空间作为 schema 的 `targetNamespace`（以及 `elementFormDefault="qualified"`）。

XSD 写在 zip 归档外部，当启用 *Add filename to result* 时会添加到 Pipeline 的结果文件列表中。

## 内存特性

Transform 使用 StAX 流式处理，仅缓冲当前打开的组元素路径的 XML 状态。因此，单个非常大的组在内存中是 O(最大组) 而非 O(文档)。

## 示例：带分组项目的订单

输入行（已按 `orderId` 排序）：

| orderId | itemName | price |
|---|---|---|
| 1 | foo | 1.50 |
| 1 | bar | 2.00 |
| 2 | baz | 3.25 |

树结构：

- `orders`（根，元素）
** `order`（元素，group-by，mapped field = `orderId`）
*** `id`（属性，mapped field = `orderId`）
*** `item`（元素，**loop**）
**** `name`（元素，mapped field = `itemName`）
**** `price`（元素，mapped field = `price`，format = `0.00`）

输出：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<orders>
  <order id="1">
    <item><name>foo</name><price>1.50</price></item>
    <item><name>bar</name><price>2.00</price></item>
  </order>
  <order id="2">
    <item><name>baz</name><price>3.25</price></item>
  </order>
</orders>
```
