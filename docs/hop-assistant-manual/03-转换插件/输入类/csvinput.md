# ![CSV File Input transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/textfileinput.svg) CSV File Input

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Not Supported, 24](../../assets/images/cross.svg) |
| Flink | ![Not Supported, 24](../../assets/images/cross.svg) |
| Dataflow | ![Not Supported, 24](../../assets/images/cross.svg) |

## 选项

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称。 |
| Filename *or* the filename field (data from previous transforms) | 指定要读取的 CSV 文件名。*或者* 选择包含要读取的文件名的字段。 |
| Delimiter | 指定目标文件中使用的分隔符。 |
| Enclosure | 指定目标文件中使用的文本限定符。 |
| NIO buffer size | 读取缓冲区的大小。 |
| Lazy conversion | 延迟转换算法会尽量避免不必要的数据类型转换，在条件允许时可显著提升性能。 |
| Header row present? | 如果目标文件的第一行包含列名标题行，请启用此选项。如果错误地对没有列名的文件设置了这个标志，Hop 会将该列在特定位置的值设为列名。如果该位置的列值为空，Hop 会将列名设为 EmptyField_<n>，其中 n 是列在列集中的位置。*注意：* 文件分析完成后，请务必检查 Hop 推测的数据类型和列说明符，因为 Hop 在分析样本数据时可能做出错误的假设。 |
| Add filename to result | 将读取的 CSV 文件名添加到此 pipeline 的结果中。 |
| The row number field name (optional) | Integer 类型字段的名称，该字段将包含此 transform 输出中的行号。 |
| Running in parallel? | 如果将有多个此 transform 的实例（transform 副本）并行运行，并且希望每个实例读取 CSV 文件的独立部分，请勾选此项。 |
| File Encoding | 指定所读取文件的编码方式。 |
| Schema Definition | 要引用的 [Schema Definition](metadata-types/static-schema-definition.md) 名称。 |
| Fields Table | 此表包含要从目标文件中读取的字段的有序列表。 |
| Preview button | 点击可预览来自目标文件的数据。 |
| Get Fields button | 点击可基于当前设置（如分隔符、文本限定符等）从目标文件返回字段列表。 |
