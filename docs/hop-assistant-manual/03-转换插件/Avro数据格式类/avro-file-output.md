# ![Avro File Output Icon, role="image-doc-icon"](../../assets/images/transforms/icons/avro_output.svg) Avro File Output

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## Options

| Option | Description |
|---|---|
| Transform name |  |
| Transform 的名称。 |  |
| Output Type |  |
| 所需的输出类型，可以是二进制文件、二进制消息或 JSON 消息。选择消息时将发送到 Output Field。 |  |
| Filename |  |
| 保存 Avro 数据文件的路径。 |  |
| Output Field |  |
| 当不写入文件时，用于输出消息的字段名称。 |  |
| Automatically create schema |  |
| 可以选择现有 schema 或基于 fields 选项卡中的字段创建 schema。 |  |
| Write schema to file |  |
| 选择此项可将 schema 写入单独的文件。 |  |
| Avro namespace |  |
| Avro 文件中使用的命名空间。 |  |
| Avro record name |  |
| Avro record 使用的名称。 |  |
| Avro documentation |  |
| Schema filename |  |
| 写入或读取 schema 的文件路径。 |  |
| Create parent folder |  |
| 创建文件时，如果需要创建父文件夹，请选择此项。 |  |
| Compression codec |  |
| 写入文件时用于 Avro 消息的压缩编解码器。 |  |
| Include transform nr in filename |  |
| 将 Transform 实例编号添加到文件名中。 |  |
| Include partition nr in filename |  |
| 将 Partition 编号添加到文件名中。 |  |
| Include date in filename |  |
| 将文件创建时的日期包含在文件名中。 |  |
| Include time in filename |  |
| 将文件创建时的时间包含在文件名中。 |  |
| Specify date format |  |
| 指定要附加到文件名的自定义日期和格式。 |  |
| Date time format |  |
| 用于格式化的日期时间规范。 |  |
| Add filenames to result |  |
| 将创建的结果文件名添加到文件名结果中。 |  |

Fields 选项卡：

| Option | Description |
|---|---|
| Name |  |
| 数据流中的源字段。 |  |
| Avro Schema Path |  |
| 目标字段，当指定了现有 schema 时，将使用 schema 中的字段填充，以映射输入字段。 |  |
| Avro Type |  |
| Avro 目标文件中字段的 Avro 类型。 |  |
| Nullable |  |
| 该字段是否可为 null？ |  |
## Metadata Injection Support

要注入 Avro 字段类型，请使用以下代码

AVRO_TYPE_NONE = 0
AVRO_TYPE_BOOLEAN = 1
AVRO_TYPE_DOUBLE = 2
AVRO_TYPE_FLOAT = 3
AVRO_TYPE_INT = 4
AVRO_TYPE_LONG = 5
AVRO_TYPE_STRING = 6
AVRO_TYPE_ENUM = 7
