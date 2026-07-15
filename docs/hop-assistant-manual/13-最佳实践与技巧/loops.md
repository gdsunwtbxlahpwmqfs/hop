循环遍历一组值、行、文件等最简单的方法是使用 Executor transform。

- [Pipeline Executor](../03-转换插件/其他转换/pipeline-executor.md)：为每个输入行运行一个 pipeline
- [Workflow Executor](../03-转换插件/其他转换/workflow-executor.md)：为每个输入行运行一个 workflow
- [Repeat](../04-动作插件/工作流控制类/repeat.md)：从 workflow action 运行 workflow 或 pipeline，直到设置了一个变量（值）。
- [End Repeat](../04-动作插件/其他动作/repeat-end.md)：跳出由 Repeat action 启动的循环。

这些选项都允许您将字段值映射到子 pipeline 或 workflow 的参数，使循环变得轻而易举。

> **💡 提示:** 避免通过 [Copy rows to result](../03-转换插件/其他转换/copyrowstoresult.md) transform 在 workflow 中使用"旧"的循环方式。这主要是出于历史原因而保留的。它使得很难看到循环内部发生了什么，而且这种循环方式不会在 Qi Hop 中永远存在。

[循环操作指南](../16-HowTo指南/loops-in-apache-hop.md)提供了有关该主题的更多详细信息。
