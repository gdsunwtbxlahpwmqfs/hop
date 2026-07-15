# Workflow 运行配置

## 描述

![](../assets/images/icons/workflow_run_config.svg)

描述如何运行 Workflow。

Workflow 可以在本地或远程执行。
查看 [Workflow 运行配置](workflow/workflow-run-configurations/workflow-run-configurations.md)了解更多详情。

## 相关 plugin

无/全部 action

## 选项

| 选项 | 描述 |
|---|---|
| Name | 此 Workflow 运行配置的名称 |
| Description | 此 Workflow 运行配置的描述 |
| Execution information location | 与此 Workflow 运行配置一起使用的 [metadata-types/execution-information-location.adoc](metadata-types/execution-information-location.md)。 |
| Workflow engine type | Hop 本地 Workflow 引擎或 Hop 远程 Workflow 引擎 |
| Safe mode | （默认：false）在安全模式下，Hop 在开始 Workflow 执行之前会执行一些数据类型和其他验证。 |

## 示例

你的 Hop 安装附带了一个默认的 'local' Workflow 运行配置。
