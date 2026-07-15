# ![Change file encoding transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/changefileencoding.svg) Change file encoding

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

- Transform name：Transform 的名称，在 Pipeline 中唯一
- Source file
** Filename field：输入字段的名称，运行时将携带文件名
** Source encoding：文本文件的编码
** Add source filename to the result filenames?

** Create parent folder：勾选此项将自动创建目标文件名字段中指定的输出文件夹。
** Target encoding：你可以在此选择目标编码
** Add target filename to the result filenames?
：如果你想自动将目标文件添加到 Pipeline 结果的文件列表中，请勾选此项。
