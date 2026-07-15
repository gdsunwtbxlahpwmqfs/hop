# ![Parquet File Input transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/parquet_input.svg) Parquet File Input

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## Options

注意事项：

- 为了支持通过 Apache VFS 从任意位置读取，每个文件都会加载到内存中（一次一个）。
请确保分配足够的内存。
- Long 值如果为 EPOC（自 `1970-01-01 00:00:00.000` 以来的毫秒数），可以反序列化为 Date。
- Parquet Binary 字段被视为 Hop String，但您也可以将其作为 Hop Binary 读取。
- 所有输入值都传递到输出
- INT96 被转换为 Hop Binary 数据类型。

| Option | Description |
|---|---|
| Transform name |  |
| Transform 的名称，此名称在单个 Pipeline 中必须唯一。 |  |
| Filename field |  |
| 指定输入字段。 |  |
| Metadata filename |  |
| 如果在此处指定文件名，可以将 fields 部分留空，Hop 会自动确定 |  |
| Output null row when empty |  |
| 如果要提取文件 metadata 且 Parquet 文件中没有找到行，您将收到一个空行。 |  |
| Fields |  |
| 在此表格中，您可以指定要从 Parquet 文件获取的所有字段及其所需的 Hop 输出类型。 |  |
| Get fields button |  |
| 使用此按钮可以选择一个 Parquet 文件，从中读取 schema 来填充 Fields 表格。 |  |
