# ![Standardize Phone Number transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/standardizephonenumber.svg) Standardize Phone Number

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称。 |
| Input field | 包含输入数据中电话号码的字段。 |
| Output field | 如果您想更新流中的输入字段，请将此字段留空。否则，一个新的字段（string）将被添加到输出数据中。 |
| Country field | 包含输入数据中国家代码的字段。国家代码必须使用 ISO alpha-2 格式。 |
| DefaultCountry | 当 country 字段的输入数据为空或无效时使用的默认国家代码。 |
| Format | 用于标准化电话号码的格式。可用选项有： |
| Number type | 结果字段，指示已处理电话号码的类型，例如固定电话、移动电话等。 |
| Is valid | 结果字段，指示电话号码是否有效。 |

> **💡 提示:** 如果原始电话号码已包含国际区号，即使该国际区号与国家输入的国际区号不同，此 transform 也不会将其规范化为另一个国家。
