# Apache Tika

Apache Tika 转换解析各种格式的文件，并提取文本内容以及可用的元数据。该转换使用 [Apache Tika](http://tika.apache.org) 库执行解析工作。

提取的元数据以 JSON 格式提供。如果需要从此元数据中获取特定信息，可以使用 JSON Input 转换进行提取。

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称（Transform name） | 转换的名称，在单个管道中必须唯一 |
| 文件选项卡（File tab） | 指定要读取和检查的文件 |

## 注意事项

- 在 Spark、Flink、Dataflow 引擎上可能受支持（Maybe Supported），Hop Engine 完全支持。
- 提取的元数据为 JSON 格式，可配合 JSON Input 转换做进一步处理。
