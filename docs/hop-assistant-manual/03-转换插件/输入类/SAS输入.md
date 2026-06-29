# SAS 输入

SAS 输入（SAS Input）转换读取由 SAS 软件（SAS Institute, Inc.）创建的 sas7bdat 格式文件。

该功能由 https://github.com/epam/parso[Parso Java 库] 提供支持。

## 注意事项

- 该转换支持 Hop Engine、Spark、Flink、Dataflow 等多种引擎。
- 专门用于读取 SAS 软件生成的 sas7bdat 数据文件。
- 底层使用 Parso 库解析 SAS 文件格式。
