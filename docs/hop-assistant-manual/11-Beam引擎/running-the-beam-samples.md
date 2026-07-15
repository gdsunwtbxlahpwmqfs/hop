# 运行 Apache Beam 示例

## 前提条件

本页面以及 Spark、Flink 和 Google Dataflow 的详细页面中的步骤将设置运行 Hop 示例项目中 pipeline 所需的一切。

### Java

您应该已经安装了 Java 来运行 Qi Hop。Qi Hop 和 Beam 都需要 Java 21 环境。

使用 `java -version` 命令检查您的 Java 版本。您的输出应该类似于下面的样子。

```
openjdk version "21.0.10" 2026-01-20
OpenJDK Runtime Environment Homebrew (build 21.0.10)
OpenJDK 64-Bit Server VM Homebrew (build 21.0.10, mixed mode, sharing)
```
### 示例项目

Hop 示例项目附带了一些 Apache Beam 示例 pipeline。您的默认 Hop 安装默认包含示例项目。如果您的 Hop 安装不包含此项目，请创建一个新项目并将其 Home 文件夹指向 `<HOP>/config/projects/samples`。

示例项目包含以下 pipeline 运行配置

- local：原生本地运行配置。在本指南的上下文中我们将忽略此运行配置。
- Dataflow：Google Cloud Dataflow 的 Apache Beam 运行配置。
- Direct：Direct runner 的 Apache Beam 运行配置。[Direct Runner](https://beam.apache.org/documentation/runners/direct/) 在您的机器上执行 pipeline，旨在尽可能验证 pipeline 是否遵循 Apache Beam 模型。Direct Runner 不关注高效的 pipeline 执行，而是执行额外的检查以确保用户不依赖模型不保证的语义。
- F[Qi Hop 中的 Apache Beam 运行配置, width="45%"](Apache Flink 的 Apache Beam 运行配置。
- Spark：Apache Spark 的 Apache Beam 运行配置。

!)(../assets/images/beam/beam-run-configurations.png)

### 构建 Hop Fat Jar

Apache Beam 需要一个所谓的 `fat jar`，将所有必需的 Java 类及其依赖项打包到一个 jar 文件中。

通过 `Tools -> Generate a Hop fat jar` 为您的 Hop 安装构建此 jar。

将此文件保存在方便的位置和文件名。将其存储在项目文件夹之外或添加到您的 `.gitignore` 中。您不想意外地将数百 MB 添加到您的 git 仓库中。

### Flink 和 Spark：导出项目 metadata

您需要将项目的 metadata 传递给 JSON，以将其传递给 `spark-submit` 或 `flink run`。

通过 `Tools -> Export metadata to JSON` 导出您的项目 metadata。

将此文件保存在方便的位置和文件名。将其存储在项目文件夹之外或添加到您的 `.gitignore` 中。您的项目 metadata 文件夹应该已经在版本控制中，您不想再次添加此完整的 metadata 时间点导出。

## 运行 Direct runner、Flink 和 Spark 示例

- [Direct Runner](pipeline/beam/beam-samples-direct-runner.md)
- [Apache Flink](pipeline/beam/beam-samples-flink.md)
- [Apache Spark](pipeline/beam/beam-samples-spark.md)
- [Google Cloud Dataflow](pipeline/beam/beam-samples-dataflow.md)
