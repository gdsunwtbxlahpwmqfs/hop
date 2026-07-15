# ![Check if file is locked transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/filelocked.svg) Check if file is locked

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

- transform name：Transform 名称，在 Pipeline 中唯一
- Filename field：输入字段的名称，在执行期间将包含文件名。
- Result fieldname：布尔值输出字段的名称，根据文件是否被锁定，包含 true 或 false。
- Add filename to result：如果你想将已检查的文件名添加到 Pipeline 结果的文件列表中，请勾选此项。
