# Apache Beam Flink Pipeline Engine

## Beam Flink

此 runner 允许您在 [Apache Flink](https://flink.apache.org) 版本 1.13 上运行 Hop pipeline。

Flink runner 支持两种模式：Local Direct Flink Runner 和 Flink Runner。

Flink Runner 和 Flink 适用于大规模、连续作业，并提供以下功能：

- 流处理优先的运行时，同时支持批处理和数据流处理程序
- 同时支持极高吞吐量和低事件延迟的运行时
- 具有精确一次处理保证的容错能力
- 流处理程序中的自然背压
- 自定义内存管理，在内存处理和核外数据处理算法之间高效、稳健地切换
- 与 YARN 及 Apache Hadoop 生态系统其他组件的集成

查看 [Apache Beam Flink runner 文档](https://beam.apache.org/documentation/runners/flink/) 获取更多信息。

### 选项

| 选项 | 说明 | 默认值 |
|---|---|---|
| The Flink master | 执行 Pipeline 的 Flink Master 地址。 |  |
| Parallelism | Pipeline 范围内使用的最大并行度。 |  |
| Checkpointing interval | 触发运行中 pipeline 检查点的时间间隔（毫秒）。 | No checkpointing, -1 |
| Checkpointing timeout (ms) | 检查点在被丢弃前可用的最长时间（毫秒）。 | -1 |
| Minimum pause between checkpoints | 触发下一个检查点前的最小暂停时间（毫秒） | -1 |
| Fail on checkpointing errors | 设置任务在检查点过程中遇到错误时的预期行为。 |  |
| Number of execution retries | 设置失败任务重新执行的次数。 |  |
| Execution retry delay (ms) | 设置两次执行之间的延迟（毫秒）。 |  |
| Object re-use | 设置对象重用的行为。 |  |
| Disable metrics | 在 Flink Runner 中禁用 Beam 指标 | -1 |
| Retain externalized checkpoints on cancellation | 设置取消时外部化检查点的行为。 | false |
| Maximum bundle size | 一个 bundle 中的最大元素数量。 | 1000 |
| Maximum bundle time (ms) | 完成一个 bundle 前等待的最长时间（毫秒）。 | 1000 |
| Shutdown sources on final watermark | 关闭已空闲配置时间（毫秒）的 source。 |  |
| Latency tracking interval | 从 source 向 sink 发送延迟跟踪标记的时间间隔（毫秒）。 |  |
| Auto watermark interval | 自动发出 watermark 的时间间隔（毫秒）。 |  |
| Batch execution mode | 批处理 pipeline 的 Flink 数据交换模式。 |  |
| User agent | 按照 [RFC2616](https://tools.ietf.org/html/rfc2616) 格式的用户代理字符串，用于向外部服务描述 pipeline。 |  |
| Temp location | 临时文件的路径。 |  |
| Plugins to stage (, delimited) | 逗号分隔的 plugin 列表。 |  |
| Transform plugin classes | Transform plugin 类列表。 |  |
| XP plugin classes | 扩展点 plugin 类列表。 |  |
| Streaming Hop transforms flush interval (ms) | 内部缓冲区通过网络完全发送并清空的时间间隔。 |  |
| Hop streaming transforms buffer size | 使用的内部缓冲区大小。 |  |
| Fat jar file location | Fat jar 位置。 |  |

## 使用 Flink Run 运行

您也可以使用 'bin/flink run' 命令来执行。
有一个主类可以与 run 命令的 `--class` 选项一起使用：

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

Flink run 命令还需要一个 fat jar 作为参数。
这可以在 Hop GUI 的工具菜单下生成，或使用命令：

```bash
sh hop-conf.sh -fj /path/to/fat.jar
```

**重要提示**：项目配置、环境等内容在 Flink 运行时上下文中无效。
这是 Hop 社区需要思考如何最好地实现的一个 TODO。
欢迎您提供意见。
在此期间，可以通过在 conf/flink-conf.yml 文件中添加一行来将变量传递给 JVM：

```yaml
env.java.opts: -DPROJECT_HOME=/path/to/project-home
```

通常情况下，在远程执行 pipeline 时指定文件名，最好不要使用像 `{openvar}Internal.Entry.Current.Folder{closevar}` 这样的相对路径。
通常更好的做法是选择几个根文件夹作为变量。
PROJECT_HOME 是一个不错的选择。

一个 Flink run 命令示例可能如下所示：

```bash
bin/flink run \
  --class org.apache.hop.beam.run.MainBeam \
  --parallelism 2 \
  -D PROJECT_HOME=/my/project/home \
  /path/to/apache-hop-fat.jar \
  /my/project/home/pipeline.hpl \
  metadata-export.json \
  FlinkRunConfig
```

## Flink 嵌入式

您可以指定 master 为 `[local]` 来使用嵌入式 Flink 引擎运行。
主要用于本地测试。
