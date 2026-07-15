# ![Schema Mapping transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/schemamapping.svg) Schema Mapping

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

当您决定引用 [Schema Definition](metadata-types/static-schema-definition.md) 来映射文件的输出布局时，Schema Mapping 是一个有用的映射 transform，可以放在 [Text File Output](pipeline/transforms/textfileoutput.md) 或 [Excel Writer](pipeline/transforms/excelwriter.md) 等 transform 之前。通过在这些 transform 之前放置 Schema Mapping，传入的流将根据预期的输出布局定义进行预设。

## 选项

| 选项 | 描述 |
|---|---|
| Schema Definition | 我们要映射到传入流的 [Schema Definition](metadata-types/static-schema-definition.md) 名称。 |
| Schema Fields | 映射到流字段的 schema 字段集合。 |
| Stream Fields name | 映射到 schema 字段的流字段集合。 |

- 使用 _Get fields_ 按钮从传入流中填充可用字段。
- 使用 _Mapping_ 按钮显示映射对话框，以支持流字段和 schema 字段之间的映射过程。
