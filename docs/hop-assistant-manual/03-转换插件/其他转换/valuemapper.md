# ![Value Mapper transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/valuemapper.svg) Value Mapper

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

以下属性用于定义映射：

| 选项 | 描述 |
|---|---|
| Transform Name | Transform 的名称，此名称在单个 Pipeline 中必须唯一。 |
| Fieldname to use | 用作映射源的字段。 |
| Target field name | 写入映射值的字段。 |
| Keep original value on non-match | 启用后，与字段值表中任何行都不匹配的源值将保留**原始源值**（原地覆盖时）或将其复制到**新的目标字段**中。 |
| Default upon non-matching | 定义当源值不为空但与任何映射行都不匹配时的默认值。 |
| Target field type | 指定映射（目标）值的数据类型。 |
| Field values table | 映射表，每行一个映射：**Source value**、**Target value** 和 **Empty string equals null**（每行 Yes/No）。 |

### Keep original value on non-match

当未匹配的值应保持为传入数据而不是变为 null 或默认值时使用此选项。

- 覆盖模式（无目标字段）：对于不匹配的行，源字段保持不变（除适用的正常存储类型处理外）。
- 新字段模式：新列接收转换为目标字段类型的源值。

当此选项开启时，**Default upon non-matching** 不会被使用。

### Empty string equals null（字段值）

每个映射行都有一个 **Empty string equals null** 列（Yes/No）。

- **Yes**（新行的默认值）：在变量解析后，如果该行的**目标**值为空，transform 将存储 **null** 作为映射结果，而不是将空字符串转换为目标类型。
- **No**：空目标值将像任何其他值一样被转换（例如，对于字符串类型的目标，转换为空字符串）。

这既适用于正常的源到目标行，也适用于在配置了映射 null 和空源值时用于映射 null/空源值的特殊行。

### 映射 NULL 值
如果定义了空的源值，则 NULL 和空字符串将被映射到相应的目标值。只允许一个空映射。

如果输入流包含 NULL 值，且未定义 NULL 的映射，则返回 NULL（而不是默认目标值）。
