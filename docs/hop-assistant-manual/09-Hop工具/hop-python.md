# Hop Python

## 简介

使用 [Py4J](https://www.py4j.org) 库，我们可以桥接 Java 和 Python 的世界。它是流行的 [PySpark](https://spark.apache.org/docs/latest/api/python/index) 项目背后的关键组件之一。

## 目标

目标是将 Python 脚本引入构建 Hop metadata 的艺术中，允许实现远超[元数据注入](pipeline/metadata-injection.md)等技术的动态场景。

## 运行网关

Hop Python Gateway 是一个与你的 Python 脚本通信的小型服务器。

### 运行 Hop Python

要使用 Hop Python 集成，运行以下 `hop` 命令：

```bash
$ sh hop python
2026/04/03 21:03:48 - HopPython - The Hop Python Gateway server was started on 127.0.0.1:25333
```

### 用法

```bash
$ sh hop python --help
Usage: hop python [-hV] [-e=<environmentOption>]
                  [--gateway-ip-address=<gatewayAddress>]
                  [--gateway-port=<gatewayPort>]
                  [--gateway-stop-password=<stopPassword>]
                  [--gateway-token=<gatewayToken>] [-j=<projectOption>]
Run the Hop Python gateway (py4j)
  -e, --environment=<environmentOption>
                  The name of the lifecycle environment to use
      --gateway-ip-address=<gatewayAddress>
                  The server on which to run the Hop Python (py4j) gateway
                    service.  The default is 127.0.0.1 (localhost).  Use
                    0.0.0.0 to make the service widely available.
      --gateway-port=<gatewayPort>
                  The port on which to run the Hop Python (py4j) gateway
                    service.  The default port is 25333.
      --gateway-stop-password=<stopPassword>
                  If you specify this password it can be used when halting the
                    server in a Python script.  Without a password the server
                    can not be stopped this way.
      --gateway-token=<gatewayToken>
                  Only allow connections to the Hop Python (py4j) gateway that
                    provide this token
  -h, --help      Show this help message and exit.
  -j, --project=<projectOption>
                  The name of the project to use
  -V, --version   Print version information and exit.
```

### 端口和地址

要在不同端口上运行网关或使其在不同 IP 地址上可用，你可以使用以下选项：

| 选项 | 描述 |
|---|---|
| `--gateway-port` |  |
| 运行 Hop Python (py4j) 网关服务的端口。默认端口为 25333。 |  |
| `--gateway-ip-address` |  |
| 运行 Hop Python (py4j) 网关服务的 IP 地址。默认为 `127.0.0.1`（localhost）。使用 `0.0.0.0` 使服务广泛可用。 |  |
| `--gateway-token` |  |
| 仅允许提供此 token 的连接访问 Hop Python (py4j) 网关。 |  |
| `--gateway-stop-password` |  |
| 如果你指定此密码，它可用于在 Python 脚本中停止服务器。没有密码则无法以这种方式停止服务器。 |  |

### 项目和环境

你也可以在 Python Gateway 服务器中启用项目或环境：

```bash
$ sh hop python -j demo
2026/04/04 16:39:48 - HopPython - Enabling project 'demo'
2026/04/04 16:39:48 - HopPython - The Hop Python Gateway server was started on 127.0.0.1:25333
```

这会设置 `PROJECT_HOME` 等变量，启用环境配置，使 metadata 对象和数据集在你的 Python 脚本中可用。

> **📝 注意:** 这将持续运行直到你按 CTRL-C 停止网关服务器。

## 设置 Python3

### 安装

安装 Python3 的虚拟环境支持：

```bash
sudo apt install virtualenv python3-virtualenv -y
```

创建一个名为 `project1` 的新虚拟环境作为新项目：

```bash
$ virtualenv -p /usr/bin/python3 project1
```

激活 `project1` 环境：

```bash
$ source project1/bin/activate
```

在此 `project1` 环境中安装 Py4j：

```bash
$ (project1) $ pip3 install py4j
```

### PyHop 入门

首先我们导入 Py4J Java 网关包并获取 `hop` 变量中的 `PyHop` 对象：

```python
from py4j.java_gateway import JavaGateway
gateway = JavaGateway()
hop = gateway.entry_point.getPyHop()
```

### 连接到其他主机或端口

如果你偏离了默认端口（25333）或主机名（127.0.0.1），你可以创建网关参数：

```python
from py4j.java_gateway import JavaGateway, GatewayParameters
gateway_params = GatewayParameters(address='192.168.1.50', port=25333)
gateway = JavaGateway(gateway_parameters=gateway_params)
```

### 使用 token 连接

如果你使用 token 启动了 Hop Python 服务器，则需要在脚本中指定它：

```python
from py4j.java_gateway import JavaGateway, GatewayParameters
params = GatewayParameters(auth_token="YourToken")
gateway = JavaGateway(gateway_parameters=params)
```

## 基础知识

### 构建 pipeline

创建新的 Hop pipeline：

```python
p = hop.newPipelineMeta()
p.setName("pipeline1")
```

创建 2 个新 Transform 和它们之间的 hop：

```python
t1=hop.newTransformMeta("t1", "CSVInput")
p.addTransform(t1)

t2 = hop.newTransformMeta("t2", "Dummy")
p.addTransform(t2)

h12 = hop.newPipelineHopMeta(t1, t2)
p.addPipelineHop(h12)
```

如你所见，在请求新 Transform 时需要选择 plugin ID 作为第二个参数。你可以在 Plugin 视图中的 `Transform` plugin 类型下浏览 ID，或使用 `print(hop.describeAvailableTransformPlugins())`

配置 CSV Input Transform：

```python
csv = t1.getTransform()
csv.setFilename('myfile.csv')
csv.setHeaderPresent(True)
csv.setEnclosure('"')
csv.setDelimiter(',')
csv.setEncoding('UTF-8')
csv.setSchemaDefinition('myfile-schema')
```

在没有 schema 的情况下创建新字段：

```python
f1 = csv.newInputField()
f1.setName("id")
f1.setTypeWithString("Integer")
f1.setFormat("00#")
f1.setLength(7)

csv.getInputFields().add(f1)
```

## Pipeline API

以下是用于处理 pipeline 的方法：

| 方法 | 描述 | 返回值 |
|---|---|---|
| `loadPipelineMeta(String filename)` |  |  |
| 加载给定文件名的 pipeline。文件名中的变量会被解析。 |  |  |
| `PipelineMeta` |  |  |
| `newPipelineMeta()` |  |  |
| 创建新的 pipeline metadata 对象 |  |  |
| `PipelineMeta` |  |  |
| `describeAvailableTransformPlugins()` |  |  |
| 描述可用的 Transform plugin |  |  |
| `String` |  |  |
| `newTransformMeta(String name, String pluginId)` |  |  |
| 使用给定名称和 plugin ID 创建新的 Transform metadata 对象 |  |  |
| `TransformMeta` |  |  |
| `newPipelineHopMeta(TransformMeta from, TransformMeta to)` |  |  |
| 在两个 Transform 之间创建新的 pipeline hop |  |  |
| `PipelineHopMeta` |  |  |
| `newPipelineEngine( |  |  |
| 创建新的 pipeline engine。你可以用它执行 pipeline。 |  |  |
| `IPipelineEngine` |  |  |

## Workflow API

构建、加载和执行 workflow 与 pipeline 的方式类似：

| 方法 | 描述 | 返回值 |
|---|---|---|
| `loadWorkflowMeta(String filename)` |  |  |
| 从文件加载 workflow metadata。文件名可以包含变量表达式。 |  |  |
| `WorkflowMeta` |  |  |
| `newWorkflowMeta()` |  |  |
| 创建新的 workflow metadata 对象。它不包含 START Action。 |  |  |
| `WorkflowMeta` |  |  |
| `describeAvailableActionPlugins()` |  |  |
| 描述可用的 Action plugin |  |  |
| `String` |  |  |
| `newActionMeta(String name, String pluginId)` |  |  |
| 创建新的 Action metadata 对象 |  |  |
| `ActionMeta` |  |  |
| `newWorkflowHopMeta(ActionMeta from, ActionMeta to)` |  |  |
| 在两个 Action 之间创建新的 workflow hop。 |  |  |
| `WorkflowHopMeta` |  |  |
| `newWorkflowEngine( |  |  |
| 创建新的 workflow engine。你可以用它执行 workflow。 |  |  |
| `IWorkflowEngine` |  |  |

启动 workflow 执行的命令是：

```
result = workflowEngine.startExecution()
```
## Metadata

你可以通过几个简单的方法访问项目中的 metadata：

### 描述可用的 metadata plugin

要获取所有可用 metadata plugin 的列表，你可以使用 Hop GUI 中的 Plugin 视图（选择 `Metadata` plugin 类型）。或者你可以使用：

```
print(hop.describeAvailableMetadataPlugins())
```
### 获取 metadata 序列化器

你可以使用以下命令获取一个序列化器，通过它对元素执行 CRUD 操作：

```
dbSerializer = hop.getMetadataSerializer("rdbms")
dwh = dbSerializer.load("DWH")
```
如果你想更高级地处理所有 metadata plugin 键，可以使用 `listMetadataKeys()` 方法，它会返回一个字符串数组。同样，你可以使用 `listMetadataElements("rdbms")` 获取数据库元素名称的数组。

### 创建新的 metadata 元素

我们为你提供了一个创建新 metadata 元素的方法：

```
newDb = hop.newMetadataElement("rdbms")
```
### 加载一个 metadata 元素

```
dwh = hop.loadMetadataElement("rdbms", "dwh")
```
### 保存或更新一个 metadata 元素

```
hop.saveMetadataElement(dwh)
```
## 高级

### 在 plugin classpath 中创建对象

有时你想创建在已安装的 plugin classpath 中定义和所属的对象。Hop Python Gateway 服务器不会自动访问 Hop 项目中的所有类。

这会导致以下代码不工作：

```python
field = gateway.jvm.org.apache.hop.pipeline.transforms.csvinput.CsvInputField()
```

这不工作是因为 plugin 有自己的独立类加载器，与 Hop 的其余部分和其他 plugin 隔离。

要在 plugin 的 classpath 中创建新对象，你可以使用以下方法，使用 `CsvInputMeta` 类为 CSV Input Transform 创建新的输入字段：

```python
# Recap from the example above:
#
from py4j.java_gateway import JavaGateway
hop = JavaGateway().entry_point.getPyHop()
t=hop.newTransform("t1", "CSVInput")
csv = t.getTransform()

# Create a new CSV Input Field:
#
field = hop.newTransformObject(csv, "org.apache.hop.pipeline.transforms.csvinput.CsvInputField")
```

### 停止服务器

如果你在 Python 中编写测试且不再需要服务器，可以使用以下方式停止它：

```python
hop.stopServer("your-stop-password")
```

停止服务器密码可以在服务器启动时作为选项指定。

## 示例

### 从头创建 pipeline 并运行

下面的示例创建一个新 pipeline 并向其中添加 2 个 Transform：

- 一个行生成器，将生成 100M 行
- 一个 Dummy 用于消费行

然后示例执行 pipeline，打印日志并评估结果。

.The example

====
```python
import sys
import xml.dom.minidom

from py4j.java_gateway import JavaGateway

gateway = JavaGateway()
hop = gateway.entry_point.getPyHop()
vars = hop.getVariables()

# Build the pipeline metadata.
# We simply want to generate 100M empty rows and send them to a dummy transform.
#
pipelineMeta = hop.newPipelineMeta()
pipelineMeta.setName("generate-rows-test")

generate = hop.newTransformMeta("generate", "RowGenerator")
generateTransform = generate.getTransform()
generateTransform.setRowLimit("100000000")

dummy = hop.newTransformMeta("dummy", "Dummy")

pipelineMeta.addTransform(generate)
pipelineMeta.addTransform(dummy)
pipelineMeta.addPipelineHop(hop.newPipelineHopMeta(generate, dummy))

# Now we can execute this pipeline.
# The "local" pipeline run configuration is picked up from the project metadata.
# This project is specified with the "--project" option when you run "hop python".
#
pipeline = hop.newPipelineEngine(pipelineMeta, "local", "Basic")

# Execute this pipeline
#
pipeline.execute()
print("Execution of the pipeline has started.")

# Get the status of the engine
#
print("Status: "+pipeline.getStatusDescription())

# Wait until it's finished
#
pipeline.waitUntilFinished()

# Evaluate the result of the pip# Get the logging from this execution
#
pipelineLog = hop.getLogging(pipeline.getLogChannelId())
print("The logging of the pipeline:")
print("----------------------------")
print(pipelineLog)

# Get the logging of a specific transform copy.
#
generateLog = hop.getLogging( pipeline.getTransform("generate", 0).getLogChannelId() )
print("The logging of transform 'generate':")
print("-------------------------------------")
print(generateLog)

# Evaluate the result of the pipeline.
# This object contains result rows, result files, metrics, and so on.
#
result = pipeline.getResult()

# Were there errors?
#
if result.getNrErrors() != 0:
  print("Pipeline had errors!")
```
====

### 从数据流中读取行

如果你有一个生成数据的 pipeline，并且想在 Python 中从中读取数据，你可以使用数据流进行进程间通信（IPC）。在此示例中，我们使用 [Apache Arrow file stream](metadata-types/data-stream/arrow-file-stream.md) data stream 类型将数据从 Hop 传递到 Python。

> **📝 注意:** 你会在脚本中注意到我们等待 pipeline 完成。可以从流文件中读取数据批次，只是不能如示例中所述读取整个文件。另一种选择是使用 [Apache Arrow Flight](metadata-types/data-stream/arrow-file-stream.md) data stream 类型。

.The example

====
```python
import os
import sys
import time
import xml.dom.minidom
import pyarrow as pa
import pyarrow.ipc as ipc

from py4j.java_gateway import JavaGateway
gateway = JavaGateway()
hop = gateway.entry_point.getPyHop()
vars = hop.getVariables()

# Load the pipeline metadata to stream rows to a file
#
pipelineMeta = hop.loadPipelineMeta('${PROJECT_HOME}/data-stream-output.hpl')

# Execute this pipeline
#
pipeline = hop.newPipelineEngine(pipelineMeta, "local", "Basic")

pipeline.execute()

print("Pipeline started: "+pipelineMeta.getName())

# While the data streaming is done, the pipeline could be doing other things.
# Because of that we wait until the pipeline is done.

pipeline.waitUntilFinished()

print("Pipeline finished: "+pipelineMeta.getName())

# We use the same variable in the Hop environment as here:
#
file_path = vars.resolve("${STREAM_FILENAME}")

print("Reading from stream file: "+file_path);

# Open the data stream and read from it
#
with pa.memory_map(file_path, 'r') as source:
    reader = ipc.open_stream(source)

    schema = reader.schema
    print("Schema:", schema)

    # Read everything into one Table (simple & efficient for most cases)
    table = pa.Table.from_batches(reader)
    print(f"Total rows: {len(table)}")

    # Convert to pandas (or Polars) if needed
    df = table.to_pandas()

    df = df.reset_index()

    print(df.to_csv())
```
====
