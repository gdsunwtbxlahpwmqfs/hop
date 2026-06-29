# REST 客户端（REST Client）

REST 客户端转换使您能够使用 RESTful 服务。表述性状态转移（REST）是一种关键的设计理念，它采用无状态的客户端-服务器架构，其中 Web 服务被视为资源，并可通过其 URL 进行标识。

REST 客户端转换可以使用预定义的 REST 连接（REST connection），也可以直接使用完整 URL。使用 REST 连接时，URL（硬编码或从字段接受）将被视为相对于 REST 连接中定义的基础 URL 的路径。在转换的"标头"选项卡中指定的标头值将覆盖 REST 连接中定义的同名标头。不使用 REST 连接时，需要指定完整 URL。

**示例：** REST 客户端转换返回一个"result"字段（可更改名称），该字段通常在下一个转换中使用。例如，可由 JSON 输入转换读取，以提取"字段"选项卡上指定的字段。

## 支持的引擎

| 引擎 | 是否支持 |
|------|---------|
| Hop Engine | ✅ 支持 |
| Spark | ❓ 可能支持 |
| Flink | ❓ 可能支持 |
| Dataflow | ❓ 可能支持 |

## 主要选项

### 常规（General）选项卡

常规选项卡用于输入访问资源的基本连接信息。

| 选项 | 说明 |
|------|------|
| 转换名称（Transform name） | 此转换在管道工作区中显示的名称。 |
| REST 连接（REST Connection） | （可选）用于基础 URL 和身份验证/授权标头名称及值的 REST 连接。 |
| URL | 指向资源的路径。 |
| 从字段接受 URL（Accept URL from field） | 指定资源的路径由某个字段定义。 |
| URL 字段名（URL field name） | 指定从哪个字段定义资源的路径。 |
| HTTP 方法（HTTP method） | 指示转换与资源交互的方式——选项包括 GET、PUT、DELETE、POST、HEAD 或 OPTIONS。 |
