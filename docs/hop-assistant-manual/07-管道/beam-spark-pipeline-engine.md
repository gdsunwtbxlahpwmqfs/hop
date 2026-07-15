# Apache Beam Spark Pipeline Engine

## Beam Spark

Apache Spark Runner 可用于通过 Apache Spark 版本 3.1 执行 Beam pipeline。

Spark Runner 在 Apache Spark 之上执行 Beam pipeline，提供以下功能：

- 批处理和流处理（及组合）pipeline。
- 与 RDD 和 DStream 提供的相同容错保证。
- 与 Spark 提供的相同安全特性。
- 使用 Spark 指标系统内置的指标报告，同时也报告 Beam Aggregator。
- 通过 Spark 的 Broadcast 变量原生支持 Beam 侧输入

查看 [Apache Beam Spark runner 文档](https://beam.apache.org/documentation/runners/spark/) 获取更多信息。

### 选项

| 选项 | 说明 | 默认值 |
|---|---|---|
| The Spark master | Spark Master 的 URL。 |  |
| Streaming: batch interval (ms) | StreamingContext 的 batchDuration —— 设置 Spark 的批处理间隔。 | 1000 |
| Streaming: checkpoint directory | 用于流处理弹性的检查点目录，在批处理中忽略。 |  |
| Streaming: checkpoint duration (ms) |  |  |
| Enable Metrics sink | 现有 Spark UI 中的一个 servlet，用于将指标数据以 JSON 数据形式提供。 |  |
| Streaming: maximum records per batch | 每个批处理间隔的最大记录数。 |  |
| Streaming: minimum read time (ms) | 最小读取耗时。 |  |
| Bundle size | 一个 bundle 中的最大元素数量。 |  |
| User agent | 按照 [RFC2616](https://tools.ietf.org/html/rfc2616) 格式的用户代理字符串，用于向外部服务描述 pipeline。 |  |
| Temp location | 临时文件的路径。 |  |
| Plugins to stage (, delimited) | 逗号分隔的 plugin 列表。 |  |
| Transform plugin classes | Transform plugin 类列表。 |  |
| XP plugin classes | 扩展点 plugin 类列表。 |  |
| Streaming Hop transforms flush interval (ms) | 内部缓冲区通过网络完全发送并清空的时间间隔。 |  |
| Hop streaming transforms buffer size | 使用的内部缓冲区大小。 |  |
| Fat jar file location | Fat jar 位置。 |  |

## 从 GUI 或 Hop Server 运行

当 Hop 直接在 master 节点上运行时，您可以启动 Apache Spark pipeline。这意味着当 localhost 上有可用集群时，您可以直接从 hop 运行 spark 作业。这也意味着您可以在 spark master 节点上部署 Hop Server，并将其用作跳板主机来启动 Apache Spark 集群上的 Spark 作业。

## 远程运行

由于 Pipeline 在 Spark 上执行只能从 Spark Master 进行，因此可以在 master 上启动 Hop server。
然后您可以从任何地方远程执行您选择的 Spark master。
确保您要使用的任何可引用工件（如 fat-jar）对 Hop server 可用，并且您已按照上一节的说明配置了安装。

## 使用 Spark Submit 运行

您也可以使用 'spark-submit' 工具来执行。
有一个主类可以使用：

```
org.apache.hop.beam.run.MainBeam
```
它接受 3 或 4 个参数：

| 参数 | 说明 |
|---|---|
| 1 |  |
| 要执行的 pipeline 的文件名。 |  |
| 2 |  |
| 要加载的 metadata 文件名（JSON）。 |  |
| 3 |  |
| 要使用的 pipeline 运行配置名称 |  |
| 4（可选） |  |
| 包含您要在 pipeline 中设置的变量的环境文件名称（JSON）。格式与 hop-config.json 或环境配置文件中使用的格式相同。 |  |

Spark-submit 还需要一个 fat jar。
这可以在 Hop GUI 的工具菜单下生成，或使用命令：

```bash
sh hop-conf.sh -fj /path/to/fat.jar
```

**重要提示**：项目配置、环境等内容在 Spark 运行时上下文中无效。
这是 Hop 社区需要思考如何最好地实现的一个 TODO。
欢迎您提供意见。
在此期间，可以通过以下选项将变量传递给 JVM：

```bash
--driver-java-options '-DPROJECT_HOME=/path/to/project-home'
```

通常情况下，在远程执行 pipeline 时指定文件名，最好不要使用像 `{openvar}Internal.Entry.Current.Folder{closevar}` 这样的相对路径。
通常更好的做法是选择几个根文件夹作为变量。
PROJECT_HOME 是一个不错的选择。

一个 spark-submit 命令示例可能如下所示：

```bash
spark-submit \
  --master spark://master-host:7077 \
  --class org.apache.hop.beam.run.MainBeam \
  --driver-java-options '-DPROJECT_HOME=/my/project/home' \
  hop-fat.jar \
  /my/project/home/pipeline.hpl \
  metadata-export.json \
  SparkRunConfig
```

## Spark 嵌入式

您可以指定 master 为 `local[4]` 来使用嵌入式 Spark 引擎运行。
主要用于本地测试。
示例中的数字 4 是执行时使用的所需线程数。
您也可以指定 `*` 让系统自动确定。

请注意，您可能会遇到如下错误：

```
Cannot assign requested address: Service 'sparkDriver' failed after 16 retries
```
在这种情况下，您可以将系统环境变量 `SPARK_LOCAL_IP` 设置为 `127.0.0.1`。

```bash
export SPARK_LOCAL_IP="127.0.0.1"
```

## 可能的错误

当您收到类似下面的堆栈跟踪时，通常意味着无法找到 spark master。

```
Caused by: java.lang.NullPointerException
  	at org.apache.spark.SparkContext.<init>(SparkContext.scala:640)
  	at org.apache.spark.api.java.JavaSparkContext.<init>(JavaSparkContext.scala:58)
  	at org.apache.beam.runners.spark.translation.SparkContextFactory.createSparkContext(SparkContextFactory.java:101)
  	at org.apache.beam.runners.spark.translation.SparkContextFactory.getSparkContext(SparkContextFactory.java:67)
  	at org.apache.beam.runners.spark.SparkRunner.run(SparkRunner.java:215)
  	at org.apache.hop.beam.engines.BeamPipelineEngine.executePipeline(BeamPipelineEngine.java:243)
```
