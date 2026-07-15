# ![Write data to properties file transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/propertyoutput.svg) Write data to properties file

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

### 常规 选项卡

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称。此名称在单个 Pipeline 中必须唯一。 |
| Key field | 包含要写入 properties 文件的 key:value 对中 key 部分的输入字段名。 |
| Value field | 包含要写入 properties 文件的 key:value 对中 value 部分的输入字段名。 |
| Comment a | 复制到 properties 文件顶部的简短注释。 |

### 内容

| 选项 | 描述 |
|---|---|
| Filename | 要创建的 properties 文件名，不含文件扩展名。 |
| Append | 勾选此选项以更新现有的 property 文件。 |
| Create parent folder | 如果要自动创建父文件夹，请勾选此选项。 |
| Accept file name from field? | 如果文件名在输入流字段中指定，请勾选此选项。 |
| File name field | 指定包含要写入的文件名的字段。 |
| Extension | 指定文件扩展名。 |
| Include transform number in filename | 在输出文件名中包含 transform 编号（当运行多个副本时）。 |
| Include date in filename | 在输出文件名中包含日期，格式为 yyyyMMdd（20081231）。 |
| Include time in filename | 在输出文件名中包含时间，格式为 HHmmss（235959）。 |
| Show filename(s)... | 显示所有要写入的文件路径和名称。 |
| Add File to result | 将生成的文件名添加到此 Pipeline 的结果中。 |
