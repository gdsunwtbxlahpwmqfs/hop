# 等待 SQL

## 描述

`Wait for SQL` action 扫描数据库并检查数据库是否满足用户定义的条件。

## 选项

| 选项 | 描述 |
|---|---|
| Action name | Workflow action 的名称。 |
| Connection | 标识要使用的数据库连接。 |
| Target schema | 要评估的表 schema 名称。 |
| Target table name | 要评估的表名。 |
| Success when rows count a | 定义用于将行数与给定值进行比较的评估方法。选项为： |
| Row count value | 定义用于评估的行数值 |
| Maximum timeout | 超过此超时时间后，workflow action 默认以失败继续，如果勾选了 Success on timeout 选项则以成功继续。以秒为单位，0 表示无限。 |
| Check cycle time | 设置评估之间的时间间隔，以秒为单位。 |
| Success on timeout | 定义达到超时时 workflow action 的成功行为——勾选后，达到最大超时限制会导致 workflow action 成功。 |
| Custom SQL | 启用自定义 SQL 查询。 |
| Use variable substitution | 将 SQL 脚本中的环境变量替换为其实际值。 |
| Clear list of result rows before execution | 在运行此 workflow action 之前清除结果行列表。 |
| Add rows to result | 将返回的行包含到结果集中。 |
| SQL Script | 启用 `Custom SQL` 选项时要使用的自定义 SQL 脚本。 |
