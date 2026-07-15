# Pipeline 运行配置

## 说明

运行配置将 Hop pipeline 开发的设计阶段和执行阶段解耦。
Pipeline 是数据_如何_处理的定义，运行配置定义了 pipeline _在哪里_执行。
Hop 支持多种不同的运行时引擎，每种引擎将在本节中更详细地描述。
每种运行配置都有自己的参数和配置选项，所有这些都作为 Hop Metadata 存储。

## 选择运行配置

启动新 pipeline 时，点击 'Pipeline run configuration' 旁边的 **New**。
所有运行配置都有一个名称、描述和引擎类型，每种引擎类型都有自己的配置选项。

创建后，运行配置可从 'Pipeline run configuration' 列表中获取，随时可用。

![Hop Configuration Selection,width=45%,align="left"](../assets/images/run-configuration/configuration-selection.png)

## 选项

### 主标签页

主标签页包含名称、描述和可用引擎类型的下拉列表。

| 选项 | 说明 |
|---|---|
| Name | 您要用于此 pipeline 运行配置的名称。 |
| Description | 您要用于此 pipeline 运行配置的描述（可选）。 |
| Execution information location | 用于此 pipeline 运行配置的 [metadata-types/execution-information-location.adoc](metadata-types/execution-information-location.md)。 |
| Execution data profile | 用于此 pipeline 运行配置的 [metadata-types/execution-data-profile.adoc](metadata-types/execution-data-profile.md)。 |
| Engine type a |  |

> **💡 提示:** 查看 [Beam Capability Matrix](https://beam.apache.org/documentation/runners/capability-matrix/) 以帮助您决定哪种 Beam 引擎最适合您的 pipeline。

### 变量标签页

此标签页允许 Hop 开发者指定一组在运行配置中使用的变量。

| 选项 | 说明 |
|---|---|
| Variable name | 变量名称。 |
| Value | 变量值。 |
| Description | 变量的描述。 |
