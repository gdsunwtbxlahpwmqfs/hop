# Neo4j 日志 Schema

Qi Hop 可以将执行信息写入 Neo4j，这样您就可以以图的形式检查 pipeline 和 workflow metadata、执行结果、日志文本和血缘。

通过将 `NEO4J_LOGGING_CONNECTION` 变量设置为 [Neo4j 连接](metadata-types/neo4j/neo4j-connection.md)的名称来启用 Neo4j 日志图。
将变量设置为 `-` 可显式禁用 Neo4j 日志。

## Pipeline 日志

Pipeline 日志记录写入 pipeline 定义、transform、hop 和运行时执行结果。

| 节点标签 | 描述 | 常用属性 |
|---|---|---|
| `Pipeline` |  |  |
| 一个 pipeline 定义。 |  |  |
| `name`, `filename`, `description` |  |  |
| `Transform` |  |  |
| pipeline 定义中的一个 transform。 |  |  |
| `pipelineName`, `name`, `description`, `pluginId`, `copies`, `locationX`, `locationY` |  |  |
| `Execution` |  |  |
| 一次 pipeline 执行或 transform 副本执行。 |  |  |
| `name`, `type`, `id`, `containerId`, `executionStart`, `executionEnd`, `durationMs`, `status`, `errors`, `linesInput`, `linesOutput`, `linesRead`, `linesWritten`, `linesRejected`, `loggingText` |  |  |
| `Usage` |  |  |
| transform 使用的文件、数据库或其他资源。 |  |  |
| `usage`, `label` |  |  |

| 关系 | 从 | 到 | 描述 |
|---|---|---|---|
| `TRANSFORM_OF_PIPELINE` |  |  |  |
| `Transform` |  |  |  |
| `Pipeline` |  |  |  |
| 将 transform 定义连接到其所属的 pipeline。 |  |  |  |
| `PRECEDES` |  |  |  |
| `Transform` |  |  |  |
| `Transform` |  |  |  |
| 表示 transform 之间的 pipeline hop。 |  |  |  |
| `EXECUTION_OF_PIPELINE` |  |  |  |
| `Execution` |  |  |  |
| `Pipeline` |  |  |  |
| 将 pipeline 执行连接到其 pipeline 定义。 |  |  |  |
| `EXECUTION_OF_TRANSFORM` |  |  |  |
| `Execution` |  |  |  |
| `Transform` |  |  |  |
| 将 transform 副本执行连接到其 transform 定义。 |  |  |  |
| `PERFORMS_<usage>` |  |  |  |
| `Execution` |  |  |  |
| `Usage` |  |  |  |
| 将 transform 执行连接到资源使用类型。 |  |  |  |

## Workflow 日志

Workflow 日志记录遵循相同的模式，记录 workflow 定义、action、hop 和运行时执行结果。

| 节点标签 | 描述 | 常用属性 |
|---|---|---|
| `Workflow` |  |  |
| 一个 workflow 定义。 |  |  |
| `name`, `filename`, `description` |  |  |
| `Action` |  |  |
| workflow 定义中的一个 action。 |  |  |
| `workflowName`, `name`, `description`, `pluginId`, `evaluation`, `launchingParallel`, `start`, `unconditional`, `locationX`, `locationY` |  |  |
| `Execution` |  |  |
| 一次 workflow 执行或 action 执行。 |  |  |
| `name`, `type`, `id`, `containerId`, `executionStart`, `executionEnd`, `durationMs`, `errors`, `linesInput`, `linesOutput`, `linesRead`, `linesWritten`, `linesRejected`, `loggingText`, `result`, `nrResultRows`, `nrResultFiles` |  |  |

| 关系 | 从 | 到 | 描述 |
|---|---|---|---|
| `ACTION_OF_WORKFLOW` |  |  |  |
| `Action` |  |  |  |
| `Workflow` |  |  |  |
| 将 action 定义连接到其所属的 workflow。 |  |  |  |
| `PRECEDES` |  |  |  |
| `Action` |  |  |  |
| `Action` |  |  |  |
| 表示 action 之间的 workflow hop。 |  |  |  |
| `EXECUTION_OF_WORKFLOW` |  |  |  |
| `Execution` |  |  |  |
| `Workflow` |  |  |  |
| 将 workflow 执行连接到其 workflow 定义。 |  |  |  |
| `EXECUTION_OF_ACTION` |  |  |  |
| `Execution` |  |  |  |
| `Action` |  |  |  |
| 将 action 执行连接到其 action 定义。 |  |  |  |

## 执行层次结构

Qi Hop 还记录已执行工作的日志通道层次结构。
每个记录的对象都表示为一个 `Execution` 节点，具有 `name`、`type`、`id`、`copy`、`containerId`、`logLevel`、`registrationDate` 和 `root` 等属性。
父子日志通道对象通过 `EXECUTES` 关系链接。

## 查询已记录的执行

[Neo4j 获取日志信息](pipeline/transforms/neo4j-getloginfo.md) transform 从 `Execution` 节点读取数据，以查找之前的 pipeline 和 workflow 执行日期。
它可以通过匹配执行的 `name`、`type`、`status`、`errors` 和 `executionStart` 属性来返回之前执行或之前成功执行的日期。

## Neo4j 执行信息位置

当 Neo4j 被配置为[执行信息位置](metadata-types/execution-information-location.md)时，Hop 会存储更丰富的执行图。
该图使用通过 `EXECUTES` 链接的 `Execution` 节点，以及 `ExecutionMetric`、`ExecutionData`、`ExecutionDataSetMeta`、`ExecutionDataSet` 和 `ExecutionDataSetRow` 等辅助标签。
这些节点通过 `HAS_METRIC`、`HAS_DATA`、`HAS_METADATA`、`HAS_DATASET` 和 `HAS_ROW` 等关系连接。
