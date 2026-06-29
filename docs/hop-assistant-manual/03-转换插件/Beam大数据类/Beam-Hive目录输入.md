# Beam Hive 目录输入（Beam Hive Catalog Input）（实验性）

Beam Hive 目录输入转换用于从 Apache Hive 元存储（metastore）中读取数据。

> ⚠️ **警告：** 此转换目前处于实验性状态，不保证各版本之间的向后兼容性。

## 支持的引擎

| 引擎 | 是否支持 |
|------|---------|
| Hop Engine | ❌ 不支持 |
| Spark | ✅ 支持 |
| Flink | ❓ 可能支持 |
| Dataflow | ❓ 可能支持 |
