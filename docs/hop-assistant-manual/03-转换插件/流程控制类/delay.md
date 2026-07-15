# ![Delay Row transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/delay.svg) Delay row

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称。此名称在单个 pipeline 中必须唯一。 |
| Timeout | 延迟时长。可以输入静态值、使用变量或从输入流中选择数值字段。值将与所选的时间单位一起解释。如果所选字段为 null 或为空，transform 将其视为零（无延迟）。 |
| Time scale | 选择超时时间单位为毫秒、秒、分钟还是小时。 |
| Get time scale from field | 启用此选项可从输入流的字段中获取时间单位。 |
| Time scale field | 包含时间单位值的字段。可接受的值（不区分大小写）为：`ms`、`millisecond(s)`、`s`、`second(s)`、`m`、`minute(s)`、`h`、`hour(s)`。空值默认为秒。 |
