# ![Get files Row Count transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/getfilesrowcount.svg) Get files Row Count

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

### File 选项卡

| Option | Description |
|---|---|
| Transform name | Transform 的名称。这在 Pipeline 中必须唯一。 |
| Get filename from field | 文件名是否取自指定字段。 |
| Filename from field | 指定读取文件名的字段。 |
| File or directory | 指定要计算行数的文件或文件路径。 |
| Add | 点击将指定的文件、目录和正则表达式添加到 Selected files 列表。 |
| Browse | 点击选择文件或目录。 |
| Regular Expression | 如果要加载多个文件，可使用匹配所需文件的表达式。 |
| !GetFilesRowsDialog.ExcludeFileMask.Label! | 如果要排除目录中的文件，可使用匹配要排除文件的表达式。 |
| Selected files | 列出您选择用于此 Transform 的文件，包括每个文件的位置以及用于匹配或排除文件的正则表达式。 |
| Delete | 点击从列表中移除所选文件。 |
| Edit | 点击修改列表中选定的文件。 |
| Show Filenames | 点击查看将要使用的所有文件。 |

### Content 选项卡

| Option | Description |
|---|---|
| Rows Count fieldname | 用于存储行计数的字段名。 |
| Rows Separator type | 选择定义行尾的字符类型，可以是回车符、换行符、制表符或自定义字符。 |
| Row separator | 如果使用自定义字符，则为定义行尾的字符。 |
| Perform smart count | 如果不选择，计数返回文件中分隔符的数量。如果选择，则执行额外遍历以尝试返回实际行数。可在行分隔符与列值分隔符相同或文件包含空行的情况下使用。 |
| Include files count in output? | 输出中是否在字段中包含文件计数。 |
| Files Count fieldname | 用于存储输出文件计数的字段名。 |
| Add filename to result | 输出中是否将文件名作为字段包含。 |
