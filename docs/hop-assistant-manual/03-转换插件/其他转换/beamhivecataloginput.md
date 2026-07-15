# ![Beam Input Icon, role="image-doc-icon"](../../assets/images/transforms/icons/beam-input.svg) (实验性) Beam Hive Catalog Input

| Hop Engine | ![Not Supported, 24](../../assets/images/cross.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

| Option | Description |
|---|---|
| Transform name | Transform 的名称，该名称在单个 Pipeline 中必须唯一。 |
| Hive Metastore URI | Thrift metastore URI 需要符合 `thrift://hivemetastore:9083` 格式 |
| Database name | metastore 中使用的数据库名称。 |
| Table name | metastore 中使用的表名 |

## 限制

目前该 Transform 仍处于实验阶段。
它尚不能检测和转换字段类型，所有数据以单个字符串返回，以 `;` 字符分隔。使用 [split fields](pipeline/transforms/splitfields.md) Transform 将数据拆分为字段。
