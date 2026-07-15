# ![Merge rows (diff) transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/mergerows.svg) Merge rows (diff)

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 用法

此 Transform 用于比较在两个不同时间收集的数据。

例如，数据仓库的源系统可能不包含上次数据更新的时间戳。

您可以使用此 Transform 比较两个数据流，并合并行中的日期和时间戳。

基于 key 字段和比较字段，此 Transform 将参考行与比较行合并，创建合并后的输出行。

每行中的标志指示值是如何比较和合并的。
可能的标志值为：

- **identical**：在两行中都找到了 key，且比较的值相同。

- **changed**：在两行中都找到了 key，但一个或多个比较值不同。

- **new**：在参考行中未找到 key。

- **deleted**：在比较行中未找到 key。

如果行的标志为 **identical** 或 **deleted**，则合并后的输出行基于参考行。

如果标志为 **new** 或 **changed**，则合并后的输出行基于比较行。

您还可以将合并并标记的行发送到 Pipeline 中的后续 Transform，例如 [Switch-Case](../流程控制类/switchcase.md) 或 [Synchronize after merge](synchronizeaftermerge.md)。
在后续 Transform 中，您可以使用 **Merge rows (diff)** 生成的标志字段来控制目标表的更新/插入/删除操作。

## 选项

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称。 |
| Reference rows origin | 指定生成参考行的 Transform。这是一个包含原始行（您希望将新行与之比较的行）的流。 |
| Compare rows origin | 指定生成比较行的 Transform。这是一个包含新行的流。 |
| Flag field name | 指定输出流中标志字段的名称。 |
| Difference field name | 指定在状态为 **changed** 时包含差异的字段名称。 |
| Keys to match | 指定包含要匹配 key 的字段。点击 "Get key fields" 插入参考行中的所有字段。 |
| Values to compare | 指定包含要比较值的字段。点击 "Get value fields" 插入比较行中的所有字段。 |

## 传递字段

可以从参考或比较数据流中传递字段。
为此，选择 "Extra" 选项卡并指定需要从任一源 Transform 保留的字段。您可以使用 `[Get fields to pass through]` 按钮添加所有可能的源字段。然后您可以在表格视图中选择要保留的行，并使用 "Keep" 工具栏按钮 (CTRL-K)。

> **📝 注意:** 对于 `new` 或 `deleted` 行，显然只有参考值或比较值可用。另一个值始终为 `null`。

## 差异 JSON 示例

如果为 JSON 中的差异指定字段名，当检测到更改时您将看到变化：

```json
{
  "changes" : [
    { "field1" : { "from" : "aaa", "to" : "bbb" }},
    { "field2" : { "from" : 111, "to" : 222 }}
    ...
  ]
}
```
