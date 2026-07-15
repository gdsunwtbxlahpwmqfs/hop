# Workflow Log

当您的项目经过初始开发和测试后，了解运行时发生了什么变得很重要。

Hop 中的 Workflow Log 允许将 workflow 日志信息作为 JSON 对象传递给 pipeline 进行处理。接收的 pipeline 可以使用 Hop pipeline 提供的所有功能来处理这些日志信息，例如写入关系型或 NoSQL 数据库、Kafka topic 等。

Hop 会将您运行的每个 workflow 的日志信息发送到您指定的 Workflow Log pipeline。

在本文中，我们将看一个示例，了解如何配置和使用 Workflow Log metadata 将 workflow 日志信息写入关系型数据库。

## 步骤 1：创建 Workflow Log metadata 对象

要创建 **Workflow Log**，请点击 **New -> Workflow Log** 选项，或点击 **Metadata -> Workflow Log** 选项。

系统显示 New Workflow Log 视图，包含以下需要配置的字段。

![新建 workflow log, width="65%"](../assets/images/how-to-guides/logging-workflow-log/workflow-logging-new.jpg)

Workflow Log 可以按以下示例进行配置：

![配置 workflow log, width="65%"](../assets/images/how-to-guides/logging-workflow-log/workflow-logging-config.jpg)

- Name：metadata 对象的名称（workflows-logging）。
- Enabled：（已勾选）。
- Pipeline executed to capture logging：选择或创建用于处理此 Pipeline Log 日志信息的 pipeline ({openvar}PROJECT_HOME{closevar}/hop/logging/workflows-logging.hpl)。

> **💡 提示:** 您应该选择或创建用于记录活动日志的 pipeline。

- Execute at the start of the pipeline?：（已勾选）。
- Execute at the end of the pipeline?：（已勾选）。
- Execute periodically during execution?：（未勾选）。

最后，保存 workflow log 配置。

> **💡 提示:** workflow 日志将应用于您在当前项目中运行的任何 workflow。这可能不是必需的甚至不是期望的。如果您只想为选定的 workflow 处理日志信息，可以在配置选项下方的表格中（"Capture output of the following workflows"）添加 workflow 选择。下面的截图显示了默认 Qi Hop 示例项目中仅捕获单个 "generate-fake-books.hwf" workflow 日志的配置。

![workflow log 选择, width="65%"](../assets/images/how-to-guides/logging-workflow-log/workflow-log-selection.png)

## 步骤 2：创建带有 Workflow Logging transform 的新 pipeline

要创建 pipeline，您可以转到视图区域或点击 New Workflow Log 对话框中的 New 按钮。然后选择 pipeline 的文件夹和名称。

新 pipeline 会自动创建一个 [Workflow Logging](../03-转换插件/其他转换/workflow-logging.md) transform 并连接到 [Dummy](../04-动作插件/其他动作/dummy.md) transform（Save logging here）。

![workflow logging pipeline, width="45%"](../assets/images/how-to-guides/logging-workflow-log/workflow-logging.jpg)

现在可以配置 Workflow Logging transform 了。此配置非常简单，打开 transform 并按以下示例设置您的值：

![配置 workflow logging, width="45%"](../assets/images/how-to-guides/logging-workflow-log/config-workflow-logging.jpg)

- Transform name：为您的 transform 选择一个名称，只需记住 transform 的名称在 pipeline 中应该是唯一的（log）。
- Also log transform：默认选中。

## 步骤 3：添加和配置 Table output transform

Table Output transform 允许您将数据加载到数据库表中。Table Output 等同于 DML 操作符 INSERT。此 transform 提供了目标表的配置选项以及许多维护和/或性能相关的选项，如 Commit Size 和 Use batch update for inserts。

> **💡 提示:** 在此示例中，我们将使用关系型数据库连接来记录日志，但您也可以使用输出文件。如果您决定使用数据库连接，请检查安装和可用性作为前提条件。

通过点击 pipeline 画布上的任意位置添加 Table Output transform，然后搜索 'table output' -> Table Output。

![workflow logging table output, width="65%"](../assets/images/how-to-guides/logging-workflow-log/workflow-logging-pipeline.jpg)

现在可以配置 Table Output transform 了。打开 transform 并按以下示例设置您的值：

![workflow logging table output, width="45%"](../assets/images/how-to-guides/logging-workflow-log/workflow-logging-table-output.png)

- Transform name：为您的 transform 选择一个名称，只需记住 transform 的名称在 pipeline 中应该是唯一的（workflows logging）。
- Connection：数据将写入的数据库连接（logging-connection）。该连接通过包含以下变量的 logging-connection.json 环境文件进行配置：

![workflow log 数据库连接, width="65%"](../assets/images/how-to-guides/logging-workflow-log/workflow-logging-connection.png)

- Target table：数据将写入的表的名称（workflows-logging）。
- 点击 SQL 选项以自动生成创建输出表的 SQL

![创建日志表 DDL 语句, width="45%"](../assets/images/how-to-guides/logging-workflow-log/workflow-logging-sql.jpg)

- 执行 SQL 语句。在这个简单场景中，我们将直接执行 SQL。在实际项目中，请考虑在版本控制中通过 [Liquibase](https://www.liquibase.org/) 或 [Flyway](https://flywaydb.org/) 等工具管理您的 DDL。

![创建表执行, width="45%"](../assets/images/how-to-guides/logging-workflow-log/workflow-logging-execute-sql.jpg)

- 打开创建的表以查看所有日志字段：

![日志表字段, width="35%"](../assets/images/how-to-guides/logging-workflow-log/workflow-logging-table.jpg)

- 关闭并保存 transform。

## 步骤 4：运行 workflow 并检查日志

最后，通过点击 Run -> Launch 选项来运行 workflow。Workflow Log pipeline 将由您运行的任何 workflow 执行。

![运行 workflow, width="65%"](../assets/images/how-to-guides/logging-workflow-log/run-workflow.png)

执行的 pipeline（generate-rows.hpl）生成一个常量并将 1000 行写入 CSV 文件：

![generate rows pipeline, width="65%"](../assets/images/how-to-guides/logging-workflow-log/pipeline-generate-rows.jpg)

Workflow 执行的数据将记录在 workflows-logging 表中。

![运行 workflow 日志, width="65%"](../assets/images/how-to-guides/logging-workflow-log/run-workflow-logging.jpg)

![workflow 指标, width="65%"](../assets/images/how-to-guides/logging-workflow-log/run-workflow-metrics.jpg)

检查表中的数据。

![表中的 workflow 指标, width="90%"](../assets/images/how-to-guides/logging-workflow-log/workflow-log-table.jpg)

## 后续步骤

您现在知道了如何使用 workflow log metadata 类型来利用 Qi Hop 提供的一切功能来处理您的 workflow 日志信息。

请查看 [pipeline log](logging-pipeline-log.md) 的相关页面，了解如何设置类似的流程来处理 pipeline 日志。
