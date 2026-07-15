# ![Files from result transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/filesfromresult.svg) Files from result

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Not Supported, 24](../../assets/images/cross.svg) |
| Flink | ![Not Supported, 24](../../assets/images/cross.svg) |
| Dataflow | ![Not Supported, 24](../../assets/images/cross.svg) |

## 选项

| Option | Description |
|---|---|
| Transform name | Pipeline 中唯一的 Transform 名称 |

## 输出字段

| Field name | Type | Example |
|---|---|---|
| type | String | Normal, Log, Error, Error-line, 等 |
| filename | String | somefile.txt |
| path | String | C:\Foo\Bar\somefile.txt |
| parentorigin | String | Process files pipeline |
| origin | String | Text File Input |
| comment | String | Read by text file input |
| timestamp | Date | 2006-06-23 12:34:56 |
