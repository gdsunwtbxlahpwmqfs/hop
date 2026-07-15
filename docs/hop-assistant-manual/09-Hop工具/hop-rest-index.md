# Hop REST Web 应用

## 描述

Hop REST Web 应用以 WAR 文件 `hop-rest-<version>.war` 部署。
你可以将此归档文件放入你喜欢的服务器的 `webapps` 文件夹中（例如 Apache Tomcat）。
我们建议将其重命名为 `hop.war` 或将其解压到 `hop` 文件夹中。

## 基础 URL

当部署在 `webapps/hop` 文件夹下时，你将在 `/hop/api/v1/` 的基础 URL 下获得下面详述的服务。

## 配置

当 Web 应用启动时，它会查找名为 `HOP_REST_CONFIG_FOLDER` 的环境变量，以确定在哪里查找名为 `hop-rest.properties` 的文件。
此文件可以包含以下属性来确定应用程序的配置：

| 属性 | 描述 |
|---|---|
| `logLevel` |  |
| 要使用的日志级别，可选值：`NOTHING`、`ERROR`、`MINIMAL`、 |  |
| `metadataExportFile` |  |
| Web 应用使用的 metadata，以单个 JSON "导出"文件的形式。使用 GUI 工具菜单或 `sh hop-conf.sh --export-metadata` 将 metadata 导出到单个文件。 |  |
| `environmentName` |  |
| 如果 `projects` plugin 可用，它将在启动应用程序之前启用此环境和底层项目（需在系统上配置，通过 `HOP_CONFIG_FOLDER` 指向配置）。 |  |
| `projectName` |  |
| 如果 `projects` plugin 可用，它将在启动应用程序之前启用此项目（需在系统上配置，通过 `HOP_CONFIG_FOLDER` 指向配置）。 |  |

## Metadata 服务

Metadata 服务部署在子路径 `metadata/` 下。

### 列出 metadata 类型键

| 类型 |
|---|
| `GET` |
| 路径 |
| `metadata/types` |
| 产出 |
| `application/json` |
| 示例调用 |
| `curl -X GET http://localhost:8080/hop/api/v1/metadata/types` |
| 示例输出 |

### 列出某个键的 metadata 元素

| 类型 |
|---|
| `GET` |
| 路径 |
| `metadata/list/\{key\}` |
| 参数 |
| `key` : metadata 类型的键 |
| 产出 |
| `application/json` |
| 示例调用 |
| `curl -X GET http://localhost:8080/hop/api/v1/metadata/list/pipeline-run-configuration/` |
| 示例输出 |

### 获取一个 metadata 元素

| 类型 |
|---|
| `GET` |
| 路径 |
| `metadata/\{key\}/\{name\}` |
| 参数 |
| `key` : metadata 类型的键，`\{name\}` : 要获取的 metadata 元素名称。 |
| 产出 |
| `application/json` |
| 示例调用 |
| `curl -X GET http://localhost:8080/hop/api/v1/metadata/pipeline-run-configuration/local/` |
| 示例输出 |

### 保存一个 metadata 元素

| 类型 |
|---|
| `POST` |
| 路径 |
| `metadata/\{key\}` |
| 参数 |
| `key` : 要保存的 metadata 类型的键 |
| 消费 |
| `application/json` |
| 产出 |
| `application/json` |
| 示例调用 |
| `curl -X POST http://localhost:8080/hop/api/v1/metadata/pipeline-run-configuration/ -d '{"engineRunConfiguration":{"Local":{"feedback_size":"50000","sample_size":"1000","sample_type_in_gui":"None","wait_time":"2","rowset_size":"10000","safe_mode":false,"show_feedback":false,"topo_sort":false,"gather_metrics":false,"transactional":false}},"defaultSelection":false,"configurationVariables":[],"name":"local","description":"Runs your pipelines locally with the standard local Hop pipeline engine","dataProfile":"first-last","executionInfoLocationName":"local"}'` |

### 删除一个 metadata 元素

| 类型 |
|---|
| `DELETE` |
| 路径 |
| `metadata/\{key\}/\{name\}` |
| 参数 |
| `key` : 要保存的 metadata 类型的键。`name` : 要删除的 metadata 元素名称。 |
| 产出 |
| `application/json` |
| 示例调用 |
| `curl -X DELETE http://localhost:8080/hop/api/v1/metadata/pipeline-run-configuration/Flink/` |
| 输出 |
| `Flink` |

## Plugin 服务

Plugin 服务部署在子路径 `plugins/` 下。

### 列出所有 plugin 类型类

| 类型 |
|---|
| `GET` |
| 路径 |
| `plugins/types` |
| 产出 |
| `application/json` |
| 示例调用 |
| `curl -X GET http://localhost:8080/hop/api/v1/plugins/types` |
| 示例输出 |

### 列出给定类型类的所有 plugin

| 类型 |
|---|
| `GET` |
| 路径 |
| `plugins/list/\{typeClassName\}/` |
| 参数 |
| `key` : plugin 类型的类名 |
| 产出 |
| `application/json` |
| 示例调用 |
| `curl -X GET http://localhost:8080/hop/api/v1/plugins/list/org.apache.hop.pipeline.engine.PipelineEnginePluginType` |
| 示例输出 |

## 执行服务

"执行"服务部署在子路径 `execution/` 下。

### 同步执行 pipeline

| 类型 |
|---|
| `POST` |
| 路径 |
| `execution/sync` |
| 产出 |
| `application/json` |
| 示例调用 |
| `curl -X POST  http://localhost:8080/hop/api/v1/execution/sync/  -H 'Content-Type: application/json'  -d '{ "service" : "test", "runConfig" : "local", "variables" : { "VAR1" : "value1", "VAR2" : "value2" }, "bodyContent" : "This is body content" }'` |
| 示例输出 |
| 执行的 pipeline 可能产生如下输出： |

要 POST 的正文可以包含以下选项（另见上面的示例）

- `service`: 要使用的 Web Service metadata 元素名称
- `runConfig`: 要使用的 pipeline 运行配置名称
- `variables`: 包含变量（或参数）及其名称和值的映射
- `bodyContent`: 这将使用 Web Service metadata 中的正文内容变量选项设置为变量

## 执行信息位置服务

执行信息位置服务部署在子路径 `location/` 下。

### 获取执行 ID

| 类型 |
|---|
| `POST` |
| 路径 |
| `location/executions/\{locationName\}` |
| 参数 |
| `locationName` : 要查询的执行信息位置名称。 |
| 消费 |
| `application/json` |
| 产出 |
| `application/json` |
| 示例调用 |
| `curl -X GET  http://localhost:8080/hop/api/v1/location/executions/local/  -H 'Content-Type: application/json'  -d '{ "includeChildren" : "true", "limit" : 100 }'` |
| 示例输出 |
| 执行 ID 列表如下所示： |

要 POST 的正文可以包含以下选项（另见上面的示例）

- `includeChildren`: 如果你想查看 workflow 和 pipeline 的子执行，请将其设置为 true
- `limit`: 要检索的最大 ID 数量，或 <=0 的值获取所有 ID

### 获取执行

| 类型 |
|---|
| `GET` |
| 路径 |
| `location/executions/\{locationName\}/\{executionId\}` |
| 参数 |
| `locationName`: 要查询的执行信息位置名称。`executionId`: 要检索的执行 ID。 |
| 产出 |
| `application/json` |
| 示例调用 |
| `curl -X GET  http://localhost:8080/hop/api/v1/location/executions/local/df84cbc2-0166-4dea-956f-72b73cf66d0d/` |
| 示例输出 |
| 执行详情如下所示： |

### 获取执行状态

| 类型 |
|---|
| `GET` |
| 路径 |
| `location/state/\{locationName\}/\{executionId\}` |
| 参数 |
| `locationName`: 要查询的执行信息位置名称。`executionId`: 要检索的执行状态 ID。 |
| 产出 |
| `application/json` |
| 示例调用 |
| `curl -X GET  http://localhost:8080/hop/api/v1/location/state/local/df84cbc2-0166-4dea-956f-72b73cf66d0d/` |
| 示例输出 |
| 执行状态如下所示： |
