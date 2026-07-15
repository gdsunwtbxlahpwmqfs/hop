# ![XML Output transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/XOU.svg) XML Output

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## Options

### File 选项卡

File 选项卡用于设置 XML 输出文件格式的一般属性。

| Option | Description |
|---|---|
| Transform name | Transform 的名称。 |
| Filename | 指定输出文本文件的文件名和位置。 |
| Do not create file at start | 启用以在没有行被处理时避免创建空文件。 |
| Extension | 在文件名末尾添加句点和扩展名（.xml）。 |
| Include transformnr in filename | 如果您以多个副本运行 Transform（另请参阅启动一个 Transform 的多个副本），副本编号将包含在文件名中，位于扩展名之前（_0）。 |
| Include date in filename | 在文件名中包含系统日期（_20041231）。 |
| Include time in filename | 在文件名中包含系统时间（_235959）。 |

### Content 选项卡

| Option | Description |
|---|---|
| Zipped | 如果您希望将 XML 文件存储在 ZIP 归档中，请勾选此项。 |
| Encoding | 要使用的编码。 |
| Parent XML element | XML 文档中根元素的名称。 |
| Row XML element | XML 文档中使用的行元素名称。 |
| Split every ... rows. | 放入单个 XML 文件中的最大数据行数，超过后创建新文件。 |

### Fields 选项卡

| Option | Description |
|---|---|
| Fieldname | 字段的名称。 |
| Elementname | XML 文件中使用的元素名称。 |
| Content Type | 该字段是属性还是元素 |
| Type | 字段的类型可以是 String、Date 或 Number。 |
| Format | 用于转换值的格式掩码 |
| Length a | Length 选项取决于字段类型，如下所示： |
| Precision a | Precision 选项取决于字段类型，如下所示： |
| Currency | 用于表示货币的符号，如 $10,000.00 或 E5.000,00 |
| Decimal | 小数点可以是 "."（10,000.00）或 ","（5.000,00） |
| Group | 分隔符可以是 ","（10,000.00）或 "."（5.000,00） |
| Null | 如果字段值为 null，将此字符串插入文本文件中 |
| Get fields | 点击从输入流中检索字段列表。 |
| Minimal width | 以使文本文件中行的结果宽度最小的方式调整 fields 选项卡中的选项；例如，不保存 0000001，而是写入 "1"，等等。 |
