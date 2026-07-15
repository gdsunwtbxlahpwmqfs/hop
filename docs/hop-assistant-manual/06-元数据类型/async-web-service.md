# 异步 Web Service

## 描述

此 Web Service 变体用于执行长时间运行的 Workflow。与通过 Web Service 调用从 Pipeline 获取即时结果不同，调用后仅返回正在执行的 Workflow 的唯一 ID。使用该唯一 ID，你可以查询 Workflow 的状态。你可以指定额外的变量，以便在查询异步运行 Workflow 的状态时报告回来。

## 选项

| 选项 | 描述 |
|---|---|
| Name | 此异步 Web Service 的名称 |
| Enabled | 布尔标志，指示此 Web Service 是否启用 |
| Filename | 用于此 Web Service 的 Workflow，可以选择打开选定的 Workflow（'Open'）、创建新的 Workflow（'New'）或浏览选择已有的 Workflow（'Browse'） |
| Status variables (, separated) | 列出在查询异步状态服务时报告回来的变量 |
| Content variable | 包含服务调用内容体的变量名称 |

## 更多信息

有关异步 Web Service 和使用示例的更多详情，请查看 [hop-server/async-web-service.adoc](hop-server/async-web-service.md) 页面和 [hop-server/index.adoc](hop-server/index.md) 文档。
