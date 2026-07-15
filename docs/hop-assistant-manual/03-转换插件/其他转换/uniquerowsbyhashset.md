# ![Unique Rows (HashSet) transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/uniquerowsbyhashset.svg) Unique Rows (HashSet)

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Not Supported, 24](../../assets/images/cross.svg) |
| Flink | ![Not Supported, 24](../../assets/images/cross.svg) |
| Dataflow | ![Not Supported, 24](../../assets/images/cross.svg) |

> **⚠️ 警告:** 在较大的数据集上使用此 transform 时，除非启用"Compare using stored row values"，否则存在哈希冲突的风险。

## 选项

| 选项 | 描述 |
|---|---|
| Transform Name | Transform 的名称，此名称在单个 Pipeline 中必须唯一。 |
| Compare using stored row values | 选择此选项可为每条记录在内存中存储所选字段的值。 |
| Redirect duplicate row | 选择此选项可将重复行作为错误处理，并将其重定向到 transform 的错误流。 |
| Error description | 指定 transform 检测到重复行时显示的错误处理描述。 |
| Fields to compare table |  |
