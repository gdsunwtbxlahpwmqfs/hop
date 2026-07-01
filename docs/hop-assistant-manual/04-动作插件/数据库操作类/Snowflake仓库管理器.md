# Snowflake仓库管理器（Snowflake warehouse manager）

`Snowflake warehouse manager` 动作提供创建、删除、恢复、暂停和更改仓库的功能。

这允许 hfxt data process 工作流在加载之前恢复仓库，并在完成后立即暂停仓库，同时可以为可能需要更多处理能力的加载部分调整仓库大小。

## 主要选项

- **连接（Connection）**：要使用的 Snowflake 数据库连接。
- **仓库名称（Warehouse name）**：仓库的名称。
- **动作（Action）**：（创建、删除、恢复、暂停、更改）您要对仓库执行的操作。

### 动作：创建仓库

此动作使用您提供的设置创建新仓库。

| 选项 | 说明 |
|------|------|
| 替换？（Replace?） | 如果仓库已存在，用您的新设置替换现有仓库。 |
