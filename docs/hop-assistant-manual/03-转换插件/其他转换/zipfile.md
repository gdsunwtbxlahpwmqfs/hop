# ![Zip file transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/zipfile.svg) Zip file

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称。 |
| Create target parent folder | 勾选此选项可在需要时创建目标父文件夹。 |
| Overwrite target file | 勾选此选项可在目标文件存在时覆盖它。 |
| Add zip filename to result | 勾选此选项可将 zip 文件名添加到内部结果集中。 |
| Source filename field | 定义包含源文件名的字段。这是被添加到 zip 归档中的文件。 |
| Target filename field | 定义包含目标 zip 文件名的字段。如果此文件不存在则创建，或在您允许的情况下覆盖。 |
| Keep source folders | 勾选此选项可在 zip 文件名中保留源文件夹。 |
| Base folder fieldname | 如果选择了 Keep source folders，定义一个包含源文件夹嵌套的任何附加文件夹的字段，在创建 zip 文件时将从文件名中移除这些文件夹。这仅保留文件所在文件夹的名称作为 zip 文件名的一部分，例如 'outputs/result.zip' |
| After zip a | 压缩后对源文件执行的操作。选择以下之一： |
| Move to folder fieldname | 定义包含压缩后源文件要移动到的目标文件夹的字段。 |
