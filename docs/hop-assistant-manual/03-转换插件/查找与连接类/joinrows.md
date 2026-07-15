# ![Join Rows transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/joinrows.svg) Join Rows

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## Options

| Option | Description |
|---|---|
| Transform name | Transform 的名称，此名称在单个 Pipeline 中必须唯一。 |
| Temp directory | 指定系统存储临时文件的目录名称，以备您需要合并超过缓存行数的数据。 |
| TMP-file prefix | 这是将生成的临时文件的前缀。 |
| Max. cache size | 在系统从临时文件读取数据之前要缓存的行数；当需要合并不适合放入内存的大型行集时需要。 |
| Main transform to read from | 指定从中读取大部分数据的 Transform；来自其他 Transform 的数据被缓存或转储到磁盘，而来自此 Transform 的数据则不会。 |
| The Condition(s) | 您可以输入复杂条件来限制输出行的数量。 |
