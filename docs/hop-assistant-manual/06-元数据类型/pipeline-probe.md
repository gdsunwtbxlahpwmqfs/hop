# Pipeline 探针

## 描述

![](../assets/images/icons/probe.svg)

Pipeline 探针元数据类型允许将 Pipeline 的输出行流式传输到另一个 Pipeline。

然后，接收 Pipeline 可以处理这些数据，例如用于数据质量、数据概况、数据血缘等。

Pipeline 探针元数据类型的工作方式是指定一个接收 Pipeline（`Pipeline executed to capture logging`）。此接收 Pipeline 是一个"普通 Pipeline"，以 [Pipeline Data Probe](../03-转换插件/其他转换/pipeline-data-probe.md) 作为输入 transform。

接收 Pipeline 随后可以利用 Qi Hop Pipeline 提供的所有功能来处理探针数据。

## 相关 plugin

- Pipeline Probe

## 选项

| 选项 | 默认值 | 描述 |
|---|---|---|
| Name |  | 此 Pipeline 探针的名称 |
| Enabled | true |  |
| Pipeline executed to capture logging |  | 用于处理此 Pipeline 探针数据的 Pipeline |
| Capture output of the following transforms |  | 要捕获日志的 Pipeline 和 transform 列表 |

## 示例

示例项目附带了一个预配置的数据探针元数据项、一个探针（接收）Pipeline 和一个源 Pipeline。

- pipeline probe：metadata perspective --> pipeline probes --> pipeline-probe-example
- 探针（接收）Pipeline：`{openvar}PROJECT_HOME{closevar}/pipeline-probe/pipeline-probe-example.hpl`
- 源 Pipeline：`{openvar}PROJECT_HOME{closevar}/pipeline-probe/pipeline-probe-generate-fake-books.hpl`

要运行此示例并查看 Pipeline 探针的运作，只需运行源 Pipeline `{openvar}PROJECT_HOME{closevar}/pipeline-probe/pipeline-probe-generate-fake-books.hpl`。

此 Pipeline 将生成 10,000 行假书籍数据。Pipeline 探针将读取 Pipeline 中的最后一个 transform（`dummy`），并将流经此 transform 的数据传递给接收（探针）transform。

接收（探针）Pipeline（`{openvar}PROJECT_HOME{closevar}/pipeline-probe/pipeline-probe-example.hpl`）以 [Pipeline Data Probe](../03-转换插件/其他转换/pipeline-data-probe.md) 作为输入。此 Pipeline 随后将对收到的数据进行反规范化，计算每个类别的书籍数量，对结果进行排序，并将最终数据写入文件（`{openvar}PROJECT_HOME{closevar}/books-per-genre/probe-data.csv`）。

`pipeline-probe-generate-fake-books.hpl` 完成后，请检查示例项目中的 `pipeline-probe/output` 文件夹，找到包含这些结果的 csv 文件。你将看到在 `{openvar}PROJECT_HOME{closevar}/pipeline-probe/pipeline-probe-generate-fake-books.hpl` 中生成的数据，按书籍类别聚合。
