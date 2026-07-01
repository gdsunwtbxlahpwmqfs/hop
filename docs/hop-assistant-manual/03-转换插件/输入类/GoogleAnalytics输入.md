# Google Analytics 输入

Google Analytics 输入（Google Analytics Input）转换使用 Google Analytics Data API 从 Google Analytics 4 账户中读取数据。

[GA4 Query Explorer](https://ga-dev-tools.google/ga4/query-explorer/) 提供了一个专门的网站，用于在 hfxt data process 之外使用 Google Analytics API 开发和测试查询。

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称 | 转换的名称 |
| Google Analytics 连接 | 用于认证的连接配置 |
| 属性 ID | Google Analytics 4 属性 ID |
| 维度 | 要检索的维度 |
| 指标 | 要检索的指标 |
| 日期范围 | 查询的日期范围 |

## 注意事项

- 需要配置 Google Analytics 连接和适当的 OAuth 凭据。
- 建议先使用 GA4 Query Explorer 工具测试查询，再在 Hop 中实现。
