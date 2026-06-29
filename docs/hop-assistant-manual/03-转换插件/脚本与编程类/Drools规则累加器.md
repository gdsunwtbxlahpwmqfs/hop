# Drools规则累加器（Rules Accumulator）

Drools规则累加器转换使用 [Drools](https://www.drools.org/) 规则引擎，收集传入的行并与规则集（rule set）进行匹配执行。

这对于确定某个问题的答案或以其他方式分析数据集非常有用。可参考 Drools 的[规则语言](https://docs.drools.org/7.68.0.Final/drools-docs/html_single/index.html#_droolslanguagereferencechapter)和 [Drools 文档](https://docs.drools.org/7.68.0.Final/drools-docs/html_single/index.html#_welcome)来使用此转换。

与规则执行器不同，规则累加器会收集所有传入行后再统一执行规则集，适用于需要对整个数据集进行汇总分析的场景。

## 主要选项

| 选项 | 说明 |
|------|------|
| 规则定义文件（Rule definition） | Drools 规则集的定义文件，可指定元数据或文件路径 |
| 规则文件名（Rule file） | 包含 Drools 规则的文件 |
| 输入字段（Input fields） | 传入规则引擎的字段 |
| 输出字段（Output fields） | 规则执行后产生的输出字段 |

## 注意事项

- 使用 Drools 作为规则引擎实现。
- 与规则执行器不同，累加器会收集所有行后再统一执行规则集。
- 支持 Hop Engine 引擎；Spark、Flink、Dataflow 引擎支持情况视具体实现而定。
