# Workflow 日志

## 描述

![](../assets/images/icons/workflow-log.svg)

允许用 Pipeline 记录 Workflow 的活动。

Workflow 日志将正在运行的 Workflow 的日志信息流式传输到一个 Pipeline。

接收 Pipeline 的唯一要求是它以 [Workflow Logging](pipeline/transforms/workflow-logging.md) transform 开始。除此之外，日志 Pipeline 就是"另一个普通 Pipeline"。
在此日志 Pipeline 中，你可以处理日志信息，例如写入关系型或 NoSQL 数据库、Kafka topic 等。

## 示例

示例项目附带了一个 Workflow 日志示例。

在 metadata perspective 中查看 Workflow 日志 `workflow-log-example`。此 Workflow 日志配置为将 Workflow `{openvar}PROJECT_HOME{closevar}/reflection/generate-fake-books.hwf` 的日志信息发送到日志 Pipeline `{openvar}PROJECT_HOME{closevar}/reflection/workflow-log-example.hpl`。

![Workflow Log, width="75%"](../assets/images/metadata-types/workflow-log.png)

## 相关 plugin

- [Workflow Logging](pipeline/transforms/workflow-logging.md)

## 选项

| 选项 | 默认值 | 描述 |
|---|---|---|
| Name | 此 Workflow 日志的名称 |  |
| Enabled? | true |  |
| Logging parent workflow only | false | 如果启用此项，仅记录由 Hop Run、GUI、Server 或 API 执行的父 Workflow。禁用时，每次 Workflow 执行都会被记录。 |
| Pipeline executed to capture logging |  | 用于处理此 Workflow 日志日志信息的 Pipeline |
| Execute at the start of the workflow? | true | 是否在 Workflow 运行开始时执行此 Workflow 日志 |
| Execute at the end of the workflow? | false | 是否在 Workflow 运行结束时执行此 Workflow 日志 |
| Execute periodically during execution? | true | 是否在 Workflow 运行期间定期执行此 Workflow 日志 |
| Interval in seconds | 30 | 如果定期执行，指示 Workflow 日志执行的间隔 |
