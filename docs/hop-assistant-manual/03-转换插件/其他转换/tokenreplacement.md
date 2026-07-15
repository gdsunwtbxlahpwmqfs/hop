# ![Token Replacement transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/token.svg) Token Replacement

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

### 输入 选项卡

- Input Type - 从何处读取字段进行 token 替换。
可以是文本、字段或文件。
- Input Type Text
** Input Text - 要进行 token 替换的文本
- Input Type Field
** Input Field - 要进行 token 替换的输入字段
- Input Type File
** Input filename - 要进行 token 替换的文件名称
** Filename is in field?
- 要进行 token 替换的文件名是否在某个字段中？
** Input filename field - 文件名所在的字段。
** Add Input filename to result?
- 将输入文件名添加到结果文件列表。

### 输出 选项卡

- Output Type - 将 token 替换后的字符串放到哪里。
可以是字段或文件。
- Output Type Field
** Output field name - 用于存放 token 替换后字符串的字段名称。
- Output Type File
** Output filename - 要写入的文件名称。
** Filename is in field?
- 输出文件名是否在某个字段中？
** Output filename field - 输出文件名所在的字段名称。
** Append output file?
- 如果输出文件已存在，是否应追加内容。
如果未勾选，Qi Hop 将在文件存在时覆盖它。
** Create parent folder?
- Qi Hop 是否应创建父文件夹？
** Output format - 输出文件的换行符格式。
** Output encoding - 写入文件时使用的字符编码。
** Split every - 每 n 行将输出文件拆分为一个新文件。
** Include copynr in filename?
- transform 的副本编号是否应包含在输出文件名中？
** Include partition nr in filename?
- 分区编号是否应包含在输出文件名中？
** Include date in filename?
- 当前日期是否应包含在输出文件名中？
** Include time in filename?
- 当前时间是否应包含在输出文件名中？
** Specify date format?
- 您是否想指定包含在输出文件名中的日期格式？
** Date time format - 包含在输出文件名中的日期/时间格式。
** Add output filenames to result?
- 将输出文件名添加到结果文件列表。

### Tokens 选项卡

- Token start string - 指示 token 开始的字符串。
- Token end string - 指示 token 结束的字符串。
token start string 和 token end string 之间的所有内容即为 token 名称。
- Stream name - 流上包含要替换 token 的值的字段名称。
- Token name - 要替换的 token 名称。
- Get Fields 按钮 - 获取输入字段列表，并尝试通过精确名称匹配将它们映射到相应字段。
