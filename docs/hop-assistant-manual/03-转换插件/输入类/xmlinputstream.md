# ![XML Input Stream (StAX) transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/xml_input_stream.svg) XML Input Stream (StAX)

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 用法

此 Transform 非常适合快速处理大型和复杂的数据结构。

与使用内存处理并可能需要清除部分文件的 [Get Data from XML](pipeline/transforms/getdatafromxml.md) transform 不同，XML Input Stream (StAX) transform 将处理逻辑移入 Pipeline 中。

该 Transform 本身提供原始 XML 数据流以及附加的处理信息。

当您在其他 Transform 中遇到限制或需要在以下情况下解析 XML 时，推荐使用此流式 Transform：

- 无论文件大小如何，您都需要独立于内存的快速数据加载。
- 您需要以不同方式灵活读取 XML 文件的不同部分，且不希望重复解析文件。

由于某些 XML 文件的处理逻辑可能很复杂，使用此 Transform 时应对现有的 Hop transforms 有较好的了解。

## Options

| Option | Description |
|---|---|
| Transform name | Transform 的名称。 |
| Filename | 输入 XML 文件的文件名。 |
| Source is from a previous transform | 从前一个 Transform 中的字段接收数据。 |
| Source field name | 从前一个 Transform 中选择一个字段作为 XML 数据。 |
| Add filename to result? | 通过将 XML 输入文件的文件名作为每行结果的值传递，将处理过的 XML 文件名添加到此 Pipeline 的结果中。 |
| Skip (Elements/Attributes) | 应跳过的元素或属性数量。 |
| Limit (Elements/Attributes) | 限制要处理的元素或属性数量。 |
| Default String Length | XML 数据名称和值字段的默认字符串长度。 |
| Encoding | 以指定编码对 XML 文件数据进行编码。 |
| Add Namespace information? | 将 XML 数据类型 NAMESPACE 添加到流中。 |
| Trim strings? | 修剪所有名称/值元素和属性。 |
| Include filename in output? |  |
| Row number in output? |  |
| XML data type (numeric) in output? |  |
| XML data type (description) in output? |  |
| XML location line in output? |  |
| XML location column in output? |  |
| XML element ID in output? |  |
| XML parent element ID in output? |  |
| XML element level in output? |  |
| XML path in output? |  |
| XML parent path in output? |  |
| XML data name in output? |  |
| XML data value in output? |  |
