# ![Avro Decode Icon, role="image-doc-icon"](../../assets/images/transforms/icons/avro_decode.svg) Avro Decode

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
| Source field |  |
| 选择要转换的字段名称（类型为：Avro） |  |
| Source fields |  |
| 可以指定要从 Avro 记录中选择的 Avro 字段名称。 |  |
| Get fields button |  |
| 可以使用 "Get fields" 按钮从指定的 Avro Record 源字段的 metadata 中包含的 schema 检索字段。如果该字段中没有 metadata（例如从 Kafka consumer 获取数据时），可以选择从 Avro 文件中读取。 |  |
