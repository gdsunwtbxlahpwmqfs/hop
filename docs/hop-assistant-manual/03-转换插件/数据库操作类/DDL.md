# DDL

DDL 转换生成 SQL 数据定义语言。

该转换使用从输入行获取的列元数据生成 [DDL](https://en.wikipedia.org/wiki/Data_definition_language)。

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称 | 转换的名称 |
| 目标表 | 要生成 DDL 的目标表名 |
| 字段 | 列元数据定义（名称、类型、长度等） |
| 输出 | 生成的 DDL SQL 语句 |

## 注意事项

- 该转换支持 Hop Engine、Spark、Flink、Dataflow 等多种引擎。
- DDL 语句基于输入行的列元数据动态生成。
- 适用于动态创建或修改数据库表结构。
