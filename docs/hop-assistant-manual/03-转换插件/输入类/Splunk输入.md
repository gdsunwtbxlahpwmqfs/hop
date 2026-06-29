# Splunk 输入

Splunk 输入（Splunk Input）转换运行 Splunk 搜索查询，并将结果作为一组 Hop 字段返回。

有关创建 Splunk 连接的更多信息，请参见 Splunk Connection 文档。

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称 | 转换的名称 |
| Splunk 连接 | 到 Splunk 服务器的连接配置 |
| 搜索查询 | Splunk 搜索查询语句 |
| 字段 | 输出字段定义 |

## 注意事项

- 需要先配置 Splunk 连接元数据。
- 查询使用 Splunk 的搜索处理语言（SPL）。
- 适用于从 Splunk 日志分析平台检索数据。
