# Salesforce 输入

Salesforce 输入（Salesforce Input）转换使用 Salesforce Web Service 直接从 Salesforce 中读取数据。

你还可以使用以下转换以不同方式修改 Salesforce 数据库：

- **Salesforce 插入（Salesforce Insert）**：将记录直接插入 Salesforce 数据库
- **Salesforce 更新（Salesforce Update）**：直接更新 Salesforce 数据库中的记录
- **Salesforce 插入或更新（Salesforce Upsert）**：更新现有记录并向 Salesforce 数据库插入新记录
- **Salesforce 删除（Salesforce Delete）**：直接从 Salesforce 数据库删除记录

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称 | 转换的名称 |
| Salesforce 连接 | 到 Salesforce 的连接配置 |
| 模块/对象 | 要查询的 Salesforce 对象 |
| 查询 | SOQL 查询语句 |
| 字段 | 输出字段定义 |

## 注意事项

- 需要配置 Salesforce 连接（用户名、密码、安全令牌等）。
- 查询使用 Salesforce 对象查询语言（SOQL）。
- 该转换仅支持 Hop Engine。
