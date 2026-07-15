# ![Java Filter transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/javafilter.svg) Java Filter

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

| Option | Description |
|---|---|
| Transform name | Transform 名称，此名称在单个 Pipeline 中必须唯一。 |
| Destination transform for matching rows (optional) | 条件评估为 true 的行将发送到此 Transform。 |
| Destination transform for non-matching rows (optional) | 条件评估为 false 的行将发送到此 Transform。 |
| Condition (Java Expression) | 定义用于过滤数据的 Java 条件。 |

### 示例

以下代码示例适用于 Condition (Java Expression) 字段。

过滤包含空格的字符串

```java
field.contains(" ");
```

过滤与常量字符串完全相同的字符串

```java
field.equals("Positive");
```

过滤布尔值

```java
field == Boolean.TRUE
```
