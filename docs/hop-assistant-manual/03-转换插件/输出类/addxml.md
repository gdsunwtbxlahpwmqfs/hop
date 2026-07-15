# ![Add XML Icon, role="image-doc-icon"](../../assets/images/transforms/icons/add_xml.svg) Add XML

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

### Content 标签页

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称。 |
| Encoding | 要使用的编码；此编码指定在 XML 文件的头部。 |
| Output Value | 包含 XML 的新字段名称。 |
| Root XML element | 生成的元素中根元素的名称。 |
| Omit XML header | 启用此项可在输出中不包含 XML 头部。 |
| Omit null values from the XML result | 不添加值为 null 的元素或属性。 |

### Fields 标签页

Fields 标签页用于配置输出字段及其格式。
下表描述了字段的每个可用属性。

| 选项 | 描述 |
|---|---|
| Fieldname | 字段名称。 |
| Element name | XML 文件中使用的元素名称。 |
| Type | 字段类型可以是 String、Date 或 Number。 |
| Format | 选择要应用于字段中值的日期或数字格式。 |
| Length | 如果指定，输出字符串将填充到此长度。 |
| Precision | 用于显示浮点数的小数位数。 |
| Currency | 用于表示货币的符号，如 $10,000.00 或 E5.000,00。 |
| Decimal | 用于表示浮点数中小数点的符号，通常为 "."（10.75）或 ","（5,25）。 |
| Grouping | 用于表示千位分隔符的符号，通常为 ","（10,000.00）或 "."（5.000,00）。 |
| Null | 字段值为 null 时使用的字符串。 |
| Attribute | 如果为 Y，将此字段设为属性。否则设为元素。 |
| Attribute parent name | 如果前一个参数 attribute 设置为 Y，可以指定要将属性添加到的父元素名称。 |

## 用例

以下是各种类别的数据，需要以 XML 格式存储到数据库中。
您想将原始数据转换为以下数据库布局。

### 原始数据

| Shape | Colour | Id | X | Y | Radius |
|---|---|---|---|---|---|
| circle | blue | 1 | 3 | 5 | 5 |
| circle | red | 2 | 1 | 3 | 5 |
| circle | blue | 5 | 5 | 9 | 5 |
| circle | blue | 6 | 8 | 2 | 5 |
| circle | red | 7 | 9 | 7 | 5 |

| Shape | Colour | Id | X | Y | Length | Width |
|---|---|---|---|---|---|---|
| rectangle | blue | 3 | 3 | 1 | 6 | 4 |
| rectangle | red | 6 | 2 | 4 | 6 | 4 |
| rectangle | blue | 10 | 8 | 2 | 6 | 4 |
| rectangle | red | 12 | 7 | 8 | 6 | 4 |
| rectangle | blue | 14 | 5 | 2 | 6 | 4 |

### 输出示例

| Id | X | Y | Class data |
|---|---|---|---|
| 3 | 4 | 7 | <SHAPE type="circle"><COLOUR>blue</COLOUR><RADIUS> 5</RADIUS></SHAPE> |
| 1 | 6 | 3 | <SHAPE type="rectangle"><COLOUR>blue</COLOUR><WIDTH> 4</WIDTH><LENGTH> 6</LENGTH></SHAPE> |
| 2 | 8 | 8 | <SHAPE type="rectangle"><COLOUR>blue</COLOUR><WIDTH> 4</WIDTH><LENGTH>6</LENGTH></SHAPE> |
| 5 | 5 | 2 | <SHAPE type="circle"><COLOUR>blue</COLOUR><RADIUS> 5</RADIUS></SHAPE> |
