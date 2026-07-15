# ![Pipeline Probe icon, role="image-doc-icon"](../../assets/images/icons/probe.svg) Pipeline Data Probe

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

Pipeline Data Probe Transform 除了 Transform 名称外不需要任何配置。

此 Transform 从 [Pipeline Probe](metadata-types/pipeline-probe.md) metadata 类型接收数据，并产生以下输出：

| sourcePipelineName | 将数据传递到此 Pipeline Data Probe Transform 的 Pipeline 名称。 |
|---|---|
| sourceTransformLogChannelId | 将数据传递到此 Pipeline Data Probe Transform 的日志通道 ID。 |
| sourceTransformName | 源 Pipeline 中将数据传递到此 Pipeline Data Probe Transform 的 Transform 名称。 |
| sourceTransformCopy | 源 Pipeline 中将数据传递到此 Pipeline Data Probe Transform 的 Transform 副本编号。 |
| rowNr | 源 Pipeline 中将数据传递到此 Pipeline Data Probe Transform 的行号。 |
| fieldName | 源 Pipeline 中将数据传递到此 Pipeline Data Probe Transform 的字段名称。 |
| fieldType | 源 Pipeline 中将数据传递到此 Pipeline Data Probe Transform 的字段类型。 |
| fieldFormat | 源 Pipeline 中将数据传递到此 Pipeline Data Probe Transform 的字段格式。 |
| fieldLength | 源 Pipeline 中将数据传递到此 Pipeline Data Probe Transform 的字段长度。 |
| fieldPrecision | 源 Pipeline 中将数据传递到此 Pipeline Data Probe Transform 的字段精度。 |
| value | 源 Pipeline 中 Transform 的值，该 Transform 将数据传递到此 Pipeline Data Probe Transform。 |
