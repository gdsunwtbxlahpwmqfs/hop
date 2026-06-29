# Avro 文件输出（Avro File Output）

Avro File Output 转换可以将 Apache Avro 消息写入文件或字段。

该转换用于将 Hop 数据流中的数据以 Avro 格式持久化输出，适用于将处理后的数据以紧凑、高效的二进制格式存储或传递的场景。

## 主要选项

| 选项 | 说明 |
|------|------|
| 文件名 | 指定输出 Avro 文件的路径 |
| Avro 模式 | 指定输出数据的 Avro 模式（Schema） |
| 字段映射 | 配置 Hop 输入字段到 Avro 记录字段的映射 |
| 输出目标 | 选择将 Avro 消息写入文件还是字段 |
