# 雪花ID（Snowflake ID Generator）

雪花ID生成器转换基于 [Snowflake 算法](https://en.wikipedia.org/wiki/Snowflake_ID) 生成全局唯一标识符（ID）。每个生成的 ID 是一个 64 位长整型数，其中编码了时间戳、数据中心 ID、机器 ID 和序列号，从而确保在分布式系统中的唯一性。

使用此转换可以在分布式 ETL 管道中为数据行生成唯一 ID。生成的 ID 是按时间排序的，可用作主键、消息标识符或数据处理系统中的其他全局唯一键。

典型用例：

- 在将记录插入数据库之前生成唯一键。
- 在流式数据管道中确保全局唯一的事件 ID。
- 在分布式环境中替代数据库自增 ID。

## 主要选项

| 选项 | 说明 |
|------|------|
| ID 字段名（ID field name） | 生成的雪花 ID 输出字段名称 |
| 数据中心 ID（Datacenter ID） | 数据中心标识符，用于分布式系统中的唯一性 |
| 机器 ID（Machine ID） | 机器标识符，用于分布式系统中的唯一性 |

## 注意事项

- 支持 Hop Engine、Spark、Flink、Dataflow 引擎。
