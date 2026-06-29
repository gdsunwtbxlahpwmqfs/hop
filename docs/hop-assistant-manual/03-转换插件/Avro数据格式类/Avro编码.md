# Avro 编码（Avro Encode）

Avro Encode 转换允许您使用选定的 Hop 字段编码生成一个新的 Avro 记录字段。

Avro 模式将成为该 Avro 记录字段的值元数据的一部分。该转换用于将多个 Hop 字段按照指定的 Avro 模式组合编码为一个 Avro 记录，便于后续以 Avro 格式传输或存储。

## 主要选项

| 选项 | 说明 |
|------|------|
| Avro 模式 | 指定用于编码的 Avro 模式（Schema） |
| 输入字段 | 选择要编码到 Avro 记录中的 Hop 字段 |
| 输出字段 | 指定生成的 Avro 记录字段名称 |
| 字段映射 | 配置 Hop 字段到 Avro 模式字段的映射关系 |
