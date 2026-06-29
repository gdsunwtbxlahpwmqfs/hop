# Avro 解码（Avro Decode）

Avro Decode 转换允许您解码一个 Avro 字段并将其转换为 Hop 字段。

该转换是 Avro Encode 的逆操作，用于将 Avro 记录字段按照其内嵌的 Avro 模式拆解为独立的 Hop 字段，便于后续对数据进行处理和分析。

## 主要选项

| 选项 | 说明 |
|------|------|
| Avro 字段 | 指定要解码的 Avro 记录输入字段 |
| Avro 模式 | 指定用于解码的 Avro 模式（Schema），通常作为字段元数据的一部分 |
| 输出字段 | 配置解码后生成的 Hop 字段及其类型 |
