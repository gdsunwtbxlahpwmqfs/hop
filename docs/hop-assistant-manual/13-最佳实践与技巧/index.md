# 最佳实践

## 简介

Qi Hop 在决定如何完成您想做的事情方面给予了极大的自由度。这种自由意味着您可以创造性地、高效地实现目标。因此，请将本页给出的建议视为提示或免费的建议，您可以根据具体情况选择采纳或拒绝。只有您才能判断这些建议的价值。

## 命名

### 命名规范

随着项目的增长，保持组织性的重要性也在增加。一个组织清晰的项目可以更容易地找到 workflow、pipeline 和其他项目文件，并使整个项目更易于维护。

您的命名规范不仅应涵盖项目的所有方面。对于 Qi Hop 来说，这意味着 workflow、pipeline、transform、action 和 metadata 项的命名规范。您的项目不仅仅只有 Qi Hop，项目的其他方面也不例外。如果命名清晰、干净且一致，输入和输出文件、数据库表等的管理将变得容易得多。

对于较大的项目，正式的命名规范文档有助于集中管理命名规范，并避免不同团队成员各自使用不同的命名规范而产生混淆。

命名规范应该被维护、更新、执行和定期验证。可以考虑使用自动化的命名规范检查（例如通过 commit hook 中的脚本）来自动验证您的命名规范。

### Transform 和 action 名称

清晰命名的 transform 和 action 可以使您的 pipeline 和 workflow 更易于理解。

默认的 action 和 transform 名称使用 action 或 transform 的类型名。这使得理解 transform 做什么变得容易，但不会告诉您它在您的 workflow 或 pipeline 中的用途。

`Filter rows`（或者更糟糕的是，`Filter rows 2 2` 或复制/粘贴 transform 后得到的类似名称）不能告诉您任何信息。一个简短而精准的 transform 名称，如 `start_date < today`，可以准确告诉您在过滤器 transform 中发生了什么。

![展示为 transform 给出描述性名称的差异, width="45%"](../assets/images/best-practices-naming.png)

例如，对于输入和输出文件，您可以使用正在读取或写入的文件名。

> **💡 提示:** 您可以在 transform 或 action 的名称中使用（复制/粘贴）任何 Unicode 字符，甚至允许使用换行符。

### Metadata

Metadata 项名称（如关系型数据库连接）应该能直接告诉您它们包含什么数据或它们的用途是什么。

Metadata 项名称不应包含技术或环境细节。

例如，如果您的 CRM 系统运行在 Postgresql 数据库中，`CRM` 作为名称就很好。您的连接已配置为 Oracle 数据库，因此无需在名称中重复。环境信息应该在您的项目生命周期环境中配置，因此无需在连接名称中包含 `dev`、`test` 或 `prod`。

### 项目文件夹和子文件夹

将项目组织在清晰命名的文件夹和子文件夹中，可以使一切更容易查找、组织和维护。避免在单个文件夹中保留数十或数百个 workflow 文件。

## 规模很重要

请合理控制 workflow 中的 action 数量和 pipeline 中的 transform 数量。

- 更大的 pipeline 或 workflow 会变得更难调试和开发。
- 每添加一个 transform 到 pipeline 中，运行时至少会启动一个新线程。仅仅因为数百个 transform 对应数百个线程，就可能显著降低速度。

如果您发现需要拆分 pipeline，可以使用 [Serialize to file](../03-转换插件/其他转换/serialize-to-file.md) transform 将中间数据写入临时文件。然后 workflow 中的下一个 pipeline 可以使用 [De-serialize from file](../03-转换插件/其他转换/serialize-de-from-file.md) transform 再次读取数据。虽然显然您也可以使用数据库或其他文件类型来完成同样的操作，但这些 transform 的执行速度最快。

## 变量

将一切参数化！[变量](variables.md)提供了一种简单的方法来避免在您的系统、环境或项目中硬编码各种内容。

- 将环境特定设置放在一个或多个[环境](../12-变量与项目管理/projects-environments.md)配置文件中。这允许您在不更改项目的情况下将项目部署到另一个环境（dev/uat/prod），您只需要配置另一组配置文件。
- 引用文件位置时，优先使用 `{openvar}PROJECT_HOME{closevar}` 而不是 `{openvar}Internal.Entry.Current.Directory{closevar}` 或 `{openvar}Internal.Pipeline.Filename.Directory{closevar}` 等表达式
- 使用变量配置 transform 副本，以便在不同规模的环境之间轻松过渡。
- 使用[环境变量](variables.md#_environment_variables.md)将您的项目和环境、审计信息等保留在 Qi Hop 安装目录之外。

## 日志

花一些时间来捕获您的 workflow 和 pipeline 的日志记录，以便您可以轻松找到已运行任务的追踪记录。

事情往往在最意想不到的时候出错，到那时您会希望能够看到发生了什么。

请参阅[日志基础](../15-日志/logging-basics.md)、[日志反射](../15-日志/logging-reflection.md)，或考虑将日志记录到 [Neo4j](../index.md) 图数据库中。最后这种方式允许您在 Neo4j 视图中浏览日志结果。

其他选项包括 [Pipeline Log](../06-元数据类型/pipeline-log.md)、[Pipeline Probe](../06-元数据类型/pipeline-probe.md)、[Workflow Log](../06-元数据类型/workflow-log.md) 和 [Execution Information Location](../06-元数据类型/execution-information-location.md)。查看可用选项，选择适合您项目和团队的日志策略。

## 可重用代码

组织您的项目，以便在多个位置需要相同操作时可以重用 pipeline 或 workflow。

例如，可以将其组织为实用工具文件夹，通过使用参数或变量来增加 pipeline 或 workflow 行为的灵活性。

### Simple Mapping

如果不同的 pipeline 中有重复的逻辑，请考虑使用 [Simple Mapping](../03-转换插件/其他转换/simple-mapping.md) 来避免在 pipeline 中一遍又一遍地重复相同的逻辑。

Simple Mapping 是一个从 [Mapping Input](../03-转换插件/映射与子管道类/mapping-input.md) 读取数据并写入 [Mapping Output](../03-转换插件/映射与子管道类/mapping-output.md) transform 的 pipeline。

您可以使用 [Simple Mapping](../03-转换插件/其他转换/simple-mapping.md) transform 在其他 pipeline 中重用这些工作。

### Metadata Injection

如果您需要多次创建"几乎"相同的 pipeline，请考虑使用 [Metadata Injection](../03-转换插件/其他转换/metainject.md) 来创建可重用的模板 pipeline。

- 避免手动填充对话框
- 当您需要动态 ETL 时
- 支持数据流式传输

示例用例：使用一个 pipeline 模板从 50 种不同的文件格式加载数据到数据库。这有助于您自动标准化和加载属性集。

## 性能基础

以下是查看 pipeline 性能时需要考虑的几个方面：

- Pipeline 是网络：网络的速度受其中最慢的 transform 的限制。
- 在 Hop GUI 中运行时会标识出慢速 transform。您会在慢速 transform 周围看到虚线边框。
- 添加更多副本和增加并行度并不总是有益的，但有时确实有帮助。绝对不要过度使用。在 pipeline 中以多个副本运行所有 transform 肯定**不会**有帮助。测试、测量并迭代以改善性能。
- 优化性能需要测量：注意执行时间，看看是否应该增加或减少并行度来提升性能。

## 循环

循环遍历一组值、行、文件等最简单的方法是使用 Executor transform。

- [Pipeline Executor](../03-转换插件/其他转换/pipeline-executor.md)：为每个输入行运行一个 pipeline
- [Workflow Executor](../03-转换插件/其他转换/workflow-executor.md)：为每个输入行运行一个 workflow
- [Repeat](../04-动作插件/工作流控制类/repeat.md)：从 workflow action 运行 workflow 或 pipeline，直到设置了一个变量（值）。
- [End Repeat](../04-动作插件/其他动作/repeat-end.md)：跳出由 Repeat action 启动的循环。

这些选项都允许您将字段值映射到子 pipeline 或 workflow 的参数，使循环变得轻而易举。

> **💡 提示:** 避免通过 [Copy rows to result](../03-转换插件/其他转换/copyrowstoresult.md) transform 在 workflow 中使用"旧"的循环方式。这主要是出于历史原因而保留的。它使得很难看到循环内部发生了什么，而且这种循环方式不会在 Qi Hop 中永远存在。

[循环操作指南](../16-HowTo指南/loops-in-apache-hop.md)提供了有关该主题的更多详细信息。

## 治理

以下内容将使您的 Qi Hop 项目更易于管理、监控和维护。

- 对您的项目文件夹进行版本控制。
- 在提交中引用相关案例（如 JIRA 或 GitHub issue 工单）
- 确保拥有备份和恢复策略，并进行测试。
- 运行持续集成
- 设置生命周期环境（开发、测试、验收、生产）
- 使用[单元测试](../07-管道/pipeline-unit-testing.md)测试您的 pipeline。定期运行所有单元测试，验证结果并在需要时采取行动。

## 安全

在考虑安全性时，始终值得检查 Hop 代码库本身可能存在的任何严重漏洞。请访问 https://hop.apache.org/security/ 了解更多信息。

此外，我们的用户可以做一些事情来限制一般的安全风险，如下所列。

### 简介

Hop 作为项目的目标是为您（数据工程师）提供实用的工具。作为一个项目，我们为您提供了许多强大的工具，可以根据您的需求完成尽可能多的工作。在这方面，它非常像编程语言或脚本工具，您可以随心所欲地创建、修改和删除内容。请理解，这是一个功能特性，而不是安全风险。

### 脚本

如果您知道如何使用，Hop 中的脚本 action 和 transform 几乎可以做任何事情。"做任何事情"并不是最佳实践，事实上，最佳实践是尽可能少地使用这些 transform，以使您的数据集成解决方案的维护负担尽可能轻。如果您无法避免使用脚本，请制定一些流程来对脚本进行代码审查，并保持脚本的简单和透明。

### 保护您的工作

在处理可能需要与数据库、远程文件夹、网站等交互的敏感信息时，请确保正确保护正在使用的机密信息。您可以采取以下措施：

#### 不要存储在 metadata 中！

请永远不要将您的机密信息存储在 metadata 中。始终使用[变量](../12-变量与项目管理/variables.md)来间接引用它们。这允许您灵活地选择所需的安全级别。对于您自己笔记本电脑上的本地数据库，简单地混淆密码甚至保留明文都可以。您可以在单独的[环境](../12-变量与项目管理/projects-environments.md)配置文件中定义这些设置。对于共享的开发、测试或生产系统，您需要让变量指向一个混淆或加密的值，或者存储在密钥保管库中的值。

#### 混淆

这是您至少需要做的事情。确保至少使用标准的 `hop encrypt` 命令来混淆您的机密信息。请理解，虽然从生成的混淆代码中获取原始值很难但并非不可能。因此，将混淆后的机密信息存储在系统上的安全位置非常重要。

#### 加密

考虑设置非对称加密来隐藏您的机密信息，使用 [AES 双向密码编码器](../18-安全与密码/aespasswords.md)。请理解，在这种情况下您需要保护存储在 `HOP_AES_ENCODER_KEY` 变量中的值。同样，将其保存在安全的位置，如 k8s 密钥保管库或您系统上受良好保护的文件中。

#### 使用密钥保管库

最后，您可以将您的机密信息存储在所谓的密钥保管库中。有关支持的密钥保管库列表以及使用变量解析器直接访问机密信息的方法，请参阅：[Variable Resolvers](../index.md)。同样，仅使用密钥保管库是不够的：确保保护连接到您所使用的密钥保管库的机密信息（密钥、用户、密码）。

### 移除未使用的 plugin

每个组织都有自己典型的已部署技术组合。作为最佳实践，请移除任何支持您不使用技术的 plugin。浏览 `plugins/` 文件夹中的 plugin 列表，移除任何您不需要的内容。特别关注可能包含长时间未更新的驱动的较老技术。您可以安全地移除 `plugins/` 文件夹中的任何文件夹。

### 保护您的 Hop Server

如果您在 Kubernetes 或虚拟机上运行 Hop Server，您可以通过这种方式保护客户端之间的网络访问。否则，请考虑设置 SSL 通信来防止任何人窃听网络流量。请参阅此处的 [SSL 配置](../index.md#_ssl_configuration.md)文档。
