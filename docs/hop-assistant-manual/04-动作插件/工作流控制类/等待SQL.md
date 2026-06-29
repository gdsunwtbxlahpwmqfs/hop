# 等待SQL（Wait for SQL）

`Wait for SQL` 动作会扫描数据库并检查数据库是否满足用户定义的条件。

## 主要选项

| 选项 | 说明 |
|------|------|
| 动作名称（Action name） | 工作流动作的名称。 |
| 连接（Connection） | 标识要使用的数据库连接。 |
| 目标架构（Target schema） | 要评估的表架构名称。 |
| 目标表名（Target table name） | 要评估的表名称。 |
| 成功条件 - 行数比较方式（Success when rows count） | 定义用于比较行数与给定值的评估方法。选项包括：等于（Equal to）、不等于（Different from）等。 |
