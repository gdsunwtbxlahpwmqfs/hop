# ![Blocking transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/blockingtransform.svg) Blocking transform

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称；此名称在单个 pipeline 中必须唯一。 |
| Pass all rows? | 决定是传递一行还是所有行。 |
| Spool directory | 需要时存储临时文件的目录；默认为系统的标准临时目录。 |
| Spool-file prefix | 选择一个易于识别的前缀，以便在临时目录中识别这些文件。 |
| Cache size | 内存中可存储的行数越多，transform 运行速度越快。 |
| Compress spool files? | 需要时压缩临时文件。 |
