# ![File exists transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/fileexists.svg) File exists

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## Options

| Option | Description |
|---|---|
| Transform name | Transform 的名称；此名称在单个 Pipeline 中必须唯一 |
| Filename field | 运行时包含文件名的输入字段 |
| Result fieldname | 包含布尔标志的字段名称。 |
| Add filename to result | 如果要将文件名添加到可在下一个 Workflow action 中使用的文件名列表中，请启用此选项。 |
| Include file type | 在字段中包含文件类型。 |
| File type field | 包含文件类型的字段名称，以 String 形式表示："file"、"folder"、"imaginary" |
