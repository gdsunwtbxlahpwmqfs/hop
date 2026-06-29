# Kafka 生产者（Kafka Producer）

Kafka 生产者转换允许您在工作节点之间近实时地发布消息，多个已订阅的成员都可以访问这些消息。一个 Kafka 生产者转换会将记录流发布到一个 Kafka 主题。

## 支持的引擎

| 引擎 | 是否支持 |
|------|---------|
| Hop Engine | ✅ 支持 |
| Spark | ❓ 可能支持 |
| Flink | ❓ 可能支持 |
| Dataflow | ❓ 可能支持 |

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称（Transform name） | 此转换的名称。 |
| 引导服务器（Bootstrap servers） | Kafka 集群中以逗号分隔的引导服务器列表。 |
| 客户端 ID（Client ID） | 唯一的客户端标识符，用于识别并建立到服务器的持久连接路径以发出请求，并区分不同的客户端。 |
| 主题（Topic） | 记录发布到的类别。 |
| 键字段（Key Field） | 在 Kafka 中，所有消息都可以设置键，允许消息根据其键按默认路由方案分发到分区。如果不存在键，消息将被随机分发到分区。 |
| 消息字段（Message Field） | 主题中包含的单条记录。 |

### 选项（Options）选项卡

使用此选项卡配置 Kafka 消费者代理源的属性格式。为方便使用，已包含一些最常见的属性格式。您可以输入任何所需的 Kafka 属性。有关这些输入名称的更多信息，请参阅 Apache Kafka 文档站点：https://kafka.apache.org/documentation/。
