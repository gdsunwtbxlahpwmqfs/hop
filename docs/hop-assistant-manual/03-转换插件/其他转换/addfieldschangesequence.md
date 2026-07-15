# ![Add value fields changing sequence Icon, role="image-doc-icon"](../../assets/images/transforms/icons/fieldschangesequence.svg) Add value fields changing sequence

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Not Supported, 24](../../assets/images/cross.svg) |
| Flink | ![Not Supported, 24](../../assets/images/cross.svg) |
| Dataflow | ![Not Supported, 24](../../assets/images/cross.svg) |

## 选项

以下是该 Transform 的选项：

- transform name：Transform 的名称，在 Pipeline 中需要唯一
- Result field：输出字段的名称，即序列值
- Start at value：每次开始的数值
- Increment by：同一组中每行递增的值
- Init sequence if value of following fields change：你可以在此指定一组字段列表。如果这些字段中的一个或多个的值与前一行相比发生了变化，序列将被重置为起始值。
