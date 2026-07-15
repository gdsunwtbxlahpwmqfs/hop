# ![Data Validator transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/validator.svg) Data Validator

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Not Supported, 24](../../assets/images/cross.svg) |
| Flink | ![Not Supported, 24](../../assets/images/cross.svg) |
| Dataflow | ![Not Supported, 24](../../assets/images/cross.svg) |

## 选项

您可以使用"New Validation"按钮添加新的验证，使用"Remove validation"移除选中的验证。
在对话框左侧选择一个验证后，您可以为每个数据验证指定以下属性：

| 选项 | 描述 |
|---|---|
| Transform Name |  |
| 此 transform 的名称在单个 Pipeline 中必须唯一。 |  |
| Report all errors, not only the first |  |
| 启用此选项将为每个输入行报告 0、1 或多个错误。 |  |
| Output one row, concatenate errors with a separator |  |
| 如果启用此选项，错误代码和描述将使用指定的分隔符字符串拼接。 |  |
| Validation description |  |
| 您可以在此处选择性地描述您要测试的内容。 |  |
| Name of the field to validate |  |
| 选择要验证的输入字段名称 |  |
| Error code |  |
| 定义一个唯一的错误代码，以便在错误处理输出中识别问题所在。 |  |
| Error description |  |
| 指定错误描述，以便在错误处理输出中识别问题所在。 |  |
| Verify data type |  |
| 如果您想进行数据类型验证，请启用此项 |  |
| Data type |  |
| 输入字段需要具有的数据类型 |  |
| Conversion mask |  |
| 预期的转换掩码 |  |
| Decimal symbol |  |
| 预期的小数符号 |  |
| Grouping symbol |  |
| 预期的小数符号 |  |
| Null allows? |  |
| 如果不允许 null 值，请禁用此项 |  |
| Only null values allows? |  |
| 如果您只期望 null 值，请启用此项 |  |
| Only numeric data expected |  |
| 如果您需要输入字段为数字（Integer、Number、BigNumber）或仅包含数字（0-9）的字符串，请启用此项 |  |
| Max string length |  |
| 转换为字符串后输入字段的最大长度 |  |
| Min string length |  |
| 转换为字符串后输入字段的最小长度 |  |
| Maximum value |  |
| 输入允许的最大值 |  |
| Minimum value |  |
| 输入允许的最小值 |  |
| Expected start string |  |
| 转换为字符串后，输入字段预期以此值开头 |  |
| Expected end string |  |
| 转换为字符串后，输入字段预期以此值结尾 |  |
| Not allowed start string |  |
| 转换为字符串后，输入字段不应以此值开头 |  |
| Not allowed end string |  |
| 转换为字符串后，输入字段不应以此值结尾 |  |
| Regular expression expected to match |  |
| 您可以指定输入字段值预期匹配的正则表达式 |  |
| Regular expression not allowed to match |  |
| 您可以指定输入字段值不允许匹配的正则表达式 |  |
| Allowed values |  |
| 您可以指定此输入字段允许的可能值列表。 |  |
| Read allowed values from another transform |  |
| 如果您想从另一个 transform 读取输入数据，请启用此选项。 |  |
| The transform to read from |  |
| 要从中读取此字段允许值的 transform 名称。 |  |
| The field to read from |  |
| 要从中读取此字段允许值的字段名称。 |  |
