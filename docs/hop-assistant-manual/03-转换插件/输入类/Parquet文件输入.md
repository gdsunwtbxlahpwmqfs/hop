# Parquet 文件输入

Parquet 文件输入转换用于从 Apache Parquet 文件中读取（原始）值。

更多关于 Parquet 的信息，请参见：http://parquet.apache.org/[Apache Parquet]。

## 注意事项

- 该转换支持 Hop Engine、Spark、Flink、Dataflow 等多种引擎。
- Parquet 是一种列式存储格式，适合大数据分析场景，能够提供高效的压缩和编码。
- 该转换主要读取原始（primitive）值类型。
