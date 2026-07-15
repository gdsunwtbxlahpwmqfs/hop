# Hop 核心概念

## 核心概念

在深入了解之前，让我们先花点时间熟悉一下 Hop 的术语。

**元数据（Metadata）** 是 Hop 中最重要的概念。
下面我们将介绍的每个项目都定义为 metadata。
Hop 与数据架构中其他组件之间的所有交互都是通过 metadata 完成的。
_metadata 是 Hop 中**一切**的核心_。

- **Pipeline** 是 **Transform** 的集合，通过 **hop** 连接。
Pipeline 中的所有 Transform 并行运行。

- **Workflow** 是 **Action** 的集合，通过 **hop** 连接。
Workflow 中的所有 Action 默认按顺序运行。

- **项目（Project）** 是 Hop 代码和配置的逻辑集合。
**环境（Environment）** 包含特定于环境的（如开发、测试、生产）metadata。

## 项目类型

Action::
<!-- include not found: action.adoc -->
Hop::
<!-- include not found: hop.adoc -->
Pipeline::
<!-- include not found: pipeline.adoc -->
![Pipeline](../assets/images/concepts/pipeline.png)

Transform::
<!-- include not found: transform.adoc -->
Workflow::
<!-- include not found: workflow.adoc -->
![Workflow](../assets/images/concepts/workflow.png)

## 项目和环境

Project::
<!-- include not found: project.adoc -->
Environment::
<!-- include not found: environment.adoc -->
![Environment Examples](../assets/images/concepts/environments.png)
