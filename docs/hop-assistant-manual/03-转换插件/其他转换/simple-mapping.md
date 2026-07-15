# ![Simple Mapping transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/MAP.svg) Simple Mapping

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

将其想象为编程中的可重用函数——您将一些数据传入函数，它返回数据。
在 Simple Mapping transform 中，您可以指定一个 [Mapping Input](pipeline/transforms/mapping-input.md) transform 来传递数据，以及一个 [Mapping Output](pipeline/transforms/mapping-output.md) transform 来从中检索数据行，还可以指定要传递给映射的参数。

如果您发现自己在多个不同的 Pipeline 中重复相同的逻辑，请使用此 transform。

## 选项

这些选项相当直观：

- Transform name：Pipeline 中的唯一名称
- Pipeline：要嵌入的映射（子）Pipeline 名称。
此 Pipeline 独立执行，被视为一个独立实体。
它始终使用标准的本地 Hop Pipeline 运行配置执行，因为这是目前唯一能够向 transform 流式传输数据进出的配置。
- Parameters 选项卡：指定要传递给映射 Pipeline 的参数
- Input 选项卡：指定要传递给映射中 [Mapping Input](pipeline/transforms/mapping-input.md) transform 的字段。
您可以映射（因此得名）字段名称。
- Output 选项卡：指定如何重命名从 [Mapping Output](pipeline/transforms/mapping-output.md) transform 检索的输出字段。

## 示例

在 samples 项目中，您可以找到 Simple Mapping transform 的两个示例：transforms/files/simple-mapping-child.hpl 和 simple-mapping-parent.hpl Pipeline。

在上面讨论的示例文件中，父 Pipeline 将名字和姓氏字段发送给子 Pipeline，执行计算后，一个字段被填充计算结果并返回。
