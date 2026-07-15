# ![Process files transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/processfiles.svg) Process files

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称。 |
| Operation | 定义操作类型：复制、移动或删除 |
| Create target parent folder | 勾选此选项后，将创建目标父文件夹。 |
| Overwrite target file | 勾选此选项后，如果目标文件已存在，将被覆盖。 |
| Add target filename to result | 勾选此选项后，将已复制、移动或删除的文件添加到结果文件列表中。 |
| Set simulation mode | 用于测试目的：最终不会执行任何操作，也不会复制、移动或删除任何文件。 |
| Source filename field | 定义包含源文件完整路径（用于复制或移动）或要删除文件完整路径的字段。 |
| Target filename field | 定义包含目标文件完整路径的字段。 |
