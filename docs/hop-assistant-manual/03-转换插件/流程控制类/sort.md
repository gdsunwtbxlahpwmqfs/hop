# ![Sort Rows transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/sortrows.svg) Sort Rows

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Not Supported, 24](../../assets/images/cross.svg) |
| Flink | ![Not Supported, 24](../../assets/images/cross.svg) |
| Dataflow | ![Not Supported, 24](../../assets/images/cross.svg) |

## 选项

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称，此名称在单个 pipeline 中必须唯一。 |
| Sort directory | 需要时存储临时文件的目录；默认为系统的标准临时目录。 |
| TMP-file prefix | 选择一个易于识别的前缀，以便在临时目录中识别这些文件。 |
| Sort size | 内存中存储的行数越多，排序过程越快，因为需要使用的临时文件更少，I/O 开销也更小。 |
| Free memory threshold (in %) | 如果排序算法发现可用空闲内存低于指定值，将开始将数据写入磁盘。 |
| Compress TMP Files | 需要时压缩临时文件以完成排序。 |
| Only pass unique rows? | 如果只希望将唯一行传递到输出流，请启用此项。 |
| Fields table | 指定要排序的字段及排序方向（升序/降序）。 |
| Get Fields | 点击可检索输入流中所有字段的列表。 |
