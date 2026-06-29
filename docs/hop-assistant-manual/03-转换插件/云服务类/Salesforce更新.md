# Salesforce 更新（Salesforce Update）

Salesforce 更新转换使用 Salesforce Web 服务直接更新您的 Salesforce 数据库中的记录。

您还可以使用以下其他转换来修改您的 Salesforce 数据库：
- **Salesforce 输入（Salesforce Input）**：直接从 Salesforce 数据库读取记录。
- **Salesforce 插入（Salesforce Insert）**：直接将记录插入到 Salesforce 数据库。
- **Salesforce 更新插入（Salesforce Upsert）**：更新 Salesforce 数据库中的现有记录并插入新记录。
- **Salesforce 删除（Salesforce Delete）**：直接从 Salesforce 数据库删除记录。

## 支持的引擎

| 引擎 | 是否支持 |
|------|---------|
| Hop Engine | ✅ 支持 |
| Spark | ❓ 可能支持 |
| Flink | ❓ 可能支持 |
| Dataflow | ❓ 可能支持 |

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称（Transform name） | 指定画布上 Salesforce 更新转换的唯一名称。可自定义或保留默认值。 |

此外还需配置 Salesforce 连接（包括用户名、密码、认证 URL、API 版本等）以及设置（模块、操作等）。
