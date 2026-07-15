# 异步 Web Service

## 描述

此 Web 服务变体用于执行长时间运行的 workflow。
与通过 [Web Service](web-service.md) 调用从 pipeline 获取即时结果不同，调用后唯一返回的是正在执行的 workflow 的唯一 ID。
使用该唯一 ID，你可以查询 workflow 的状态。
你可以指定额外的变量，以便在查询异步运行的 workflow 状态时返回。

有关如何配置异步 Web 服务的更多信息，请查看 [Asynchronous Web Service](async-web-service.md) metadata 类型。

以下是生命周期：

### 执行 Workflow

执行异步 Web 服务通过调用方法 `hop/asyncRun` 完成，主要参数是异步 Web 服务的名称。（`service=<metadata 对象名称>`）响应将是一个包含 workflow ID 的 JSON 文档。
任何其他参数将被视为 workflow 的变量或参数，并设置在底层 workflow 中。

你可以使用 `runConfig` 参数选择用于执行的工作流运行配置。请注意，更高级的功能（如跟踪正在运行的 pipeline 的进度）仅在 Local workflow engine 上可用。

你还可以向 Web 服务 POST 内容，该内容可以作为变量设置在正在执行的 workflow 中。
请参见下面的"Content variable"选项。
在以下示例中，我们执行服务 `dataload`，参数 `MAX` 设置为 50M。
JSON 文件 `document.json` 的内容被 POST 到服务。
调用的结果存储在文件 `async-run.json` 中：

```bash
curl -v \
  --user cluster:cluster \
  --request POST \
  -o async-run.json \
  -H "Content-Type: application/json" \
  --data-binary '@document.json' \
  'http://localhost:8282/hop/asyncRun/?service=dataload&MAX=50000000'
```

### 查询状态

你可以调用方法 `hop/asyncStatus`，参数为服务名称和正在执行的 workflow 的 ID：`hop/asyncStatus?service=<name>&id=<id>`。

在 Web 服务中指定的变量将包含在服务的 JSON 输出中。

你还可以包含一个或多个 pipeline 的执行状态。
你可以使用以下 Action 标记 pipeline Action：`Enable Async Logging`。
系统会询问你要报告到的 Web 服务名称。

使用上一个命令的输出，你可以执行以下命令从命令行查询 workflow 的状态：

```bash
ID=$(cat async-run.json | sed 's/^.*"id":"//g' | sed 's/"}$//g') && \
curl --user cluster:cluster \
     --request GET \
     -o - \
     'http://localhost:8282/hop/asyncStatus/?service=dataload&id='$ID
```

正在执行的异步 Web 服务的 ID 与正在执行的 workflow 的"容器 ID"相同。这意味着你也可以使用 `hop/getWorkflowStatus` 服务查询 workflow 本身。该服务的输出可以是 HTML（默认）、XML（参数 `&xml=Y`）或 JSON（参数 `&json=Y`）。
Web 服务还将包含 workflow 的日志通道 ID，以便你可以将其与你选择的[执行信息位置](../06-元数据类型/execution-information-location.md)中列出的执行 ID 进行匹配。

另请参见：[Get Workflow Status](rest-api.md#_getworkflowstatus.md)。

## 选项

| 选项 | 描述 |
|---|---|
| Name |  |
| 异步 Web 服务的名称。 |  |
| Enabled |  |
| 启用或禁用此 Asynchronous Web Service |  |
| Filename on the server |  |
| 服务器上的文件名。 |  |
| Status variables |  |
| 列出查询异步状态服务时报告返回的变量 |  |
| Content variable |  |
| 将包含服务调用正文内容的变量名称 |  |
