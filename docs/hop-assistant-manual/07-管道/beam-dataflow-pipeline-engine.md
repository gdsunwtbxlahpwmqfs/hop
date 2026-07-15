# Apache Beam Google DataFlow Pipeline Engine

## Beam DataFlow

Google Cloud Dataflow 是一个完全托管的服务，用于在 Google Cloud Platform 生态系统中执行 Apache Beam pipeline。
作为 Google Cloud 托管服务，它会自动配置 worker 节点并提供开箱即用的优化。

Cloud Dataflow Runner 和服务适用于大规模连续作业，并提供以下功能：

- 完全托管的服务
- 在 Dataflow 作业生命周期内自动伸缩 worker 数量
- 动态工作负载再平衡

查看 [Google DataFlow 文档](https://cloud.google.com/dataflow/docs/guides/specifying-exec-params) 和 [Apache Beam DataFlow runner 文档](https://beam.apache.org/documentation/runners/dataflow/) 获取更多信息。

## Google Dataflow 配置

提示：此配置清单转载（复制）自 [Apache Beam 文档](https://beam.apache.org/documentation/runners/dataflow/)。

要使用 Google Cloud Dataflow 运行时配置，您必须完成所选语言的 [Cloud Dataflow 快速入门](https://cloud.google.com/dataflow/docs/quickstarts) 中"_开始之前_"部分的设置。

- 选择或创建一个 Google Cloud Platform Console 项目。
- 为您的项目启用计费。
- 启用所需的 Google Cloud API：Cloud Dataflow、Compute Engine、Stackdriver Logging、Cloud Storage、Cloud Storage JSON 和 Cloud Resource Manager。
如果您的 pipeline 代码中使用了其他 API（如 BigQuery、Cloud Pub/Sub 或 Cloud Datastore），您可能需要额外启用这些 API。
- 通过 Google Cloud Platform 身份验证。
- 安装 Google Cloud SDK。
- 创建一个 Cloud Storage 存储桶。

## 选项

| 选项 | 说明 |
|---|---|
| Project ID |  |
| 您的 Google Cloud 项目的项目 ID。 |  |
| Service Account |  |
| 使用此选项，您可以使用特定的服务账号而不是默认的 GCE 机器人来运行 pipeline dataflow 作业。默认为留空。 |  |
| Application name | 正在执行的 Dataflow 作业名称，显示在 Dataflow 作业列表和作业详情中。 |
| Staging location | 用于暂存本地文件的 Cloud Storage 路径。 |
| Initial number of workers | 执行 pipeline 时使用的初始 Google Compute Engine 实例数量。 |
| Maximum number of workers | 执行期间 pipeline 可使用的最大 Compute Engine 实例数量。 |
| Auto-scaling algorithm a | Dataflow 作业的自动伸缩模式。 |
| Worker machine type |  |
| Worker disk type | 要使用的持久化磁盘类型，以磁盘类型资源的完整 URL 指定。 |
| Disk size in GB | 每个远程 Compute Engine worker 实例上使用的磁盘大小（GB）。 |
| Region | 指定用于启动 worker 实例运行 pipeline 的 Compute Engine 区域。 |
| Zone | 指定用于启动 worker 实例运行 pipeline 的 Compute Engine 可用区。 |
| Network |  |
| 这是用于启动 worker 的 GCE 网络。 |  |
| Subnetwork |  |
| 这是用于启动 worker 的 GCE 子网络。 |  |
| Use public IPs? |  |
| 指定 worker 池是否应使用公共 IP 地址启动。 |  |
| Dataflow Service Options |  |
| 这是格式为 `option=value,option2=value2,...` 的逗号分隔选项列表。它作为 Dataflow 服务的通用选项。当 Beam 缺少某些与服务相关的选项时这很有用，如果将来添加了新选项，旧版 Beam 仍可通过这些通用选项使用它们。 |  |
| User agent | 按照 [RFC2616](https://tools.ietf.org/html/rfc2616) 格式的用户代理字符串，用于向外部服务描述 pipeline。 |
| Temp location | 用于临时文件的 Cloud Storage 路径。 |
| Plugins to stage (, delimited) | 逗号分隔的 plugin 列表。 |
| Transform plugin classes | Transform plugin 类列表。 |
| XP plugin classes | 扩展点 plugin 类列表。 |
| Streaming Hop transforms flush interval (ms) | 内部缓冲区通过网络完全发送并清空的时间间隔。 |
| Hop streaming transforms buffer size | 使用的内部缓冲区大小。 |
| Fat jar file location | Fat jar 位置。 |

**环境设置**

此环境变量需要在本地设置。

```bash
GOOGLE_APPLICATION_CREDENTIALS=/path/to/google-key.json
```

## 安全注意事项

为了允许加密（TLS）网络连接到例如 Kafka 和 Neo4j Aura 等服务，某些旧的安全算法在 [Dataflow 上被禁用](https://github.com/apache/hop/blob/master/plugins/engines/beam/src/main/java/org/apache/hop/beam/engines/dataflow/DataFlowJvmStart.java)。
这是通过将安全属性 `jdk.tls.disabledAlgorithms` 设置为值 `Lv3, RC4, DES, MD5withRSA, DH keySize < 1024, EC keySize < 224, 3DES_EDE_CBC, anon, NULL` 来实现的。

如果您需要使其可配置，请告诉我们，我们会寻找不硬编码此设置的方法。
只需创建一个 Github Issue 告知我们即可。
