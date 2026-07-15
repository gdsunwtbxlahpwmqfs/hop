# ![Copy rows to result transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/rowstoresult.svg) Copy rows to result

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Not Supported, 24](../../assets/images/cross.svg) |
| Flink | ![Not Supported, 24](../../assets/images/cross.svg) |
| Dataflow | ![Not Supported, 24](../../assets/images/cross.svg) |

## 用法
Copy Rows To Result Transform 可以与 Get Rows from Result Transform 配合使用，以在 Pipeline 中检索结果行集。在某些情况下，不需要 Get Rows from Result Transform 来检索结果集，例如 Workflow 中的 Pipeline Transform。数据行可以在 Workflow 中的 Pipeline 之间传递，但不能直接在 Workflow 中访问数据行。

该 Transform 可以被 Get Rows from Result Transform 以及一些允许处理内部结果行集的 Workflow action 使用。

> **📝 注意:** 该 Transform 主要由于历史原因而保留。Hop 中有更新更好的方式来创建循环，请查看 [最佳实践](../../index.md) 获取更多信息。

## 选项

| Option | Description |
|---|---|
| Transform name | Transform 的名称，该名称在单个 Pipeline 中必须唯一。 |
