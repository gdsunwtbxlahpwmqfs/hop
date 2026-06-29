# MySQL导出到文件（Bulk load from MySQL into file）

`Bulk load from MySQL into file` 动作将 MySQL 表中的数据批量加载到文件中。

## 主要选项

| 选项 | 说明 |
|------|------|
| 动作名称（Action name） | 工作流动作的名称。 |
| 连接（Connection） | 数据库连接。 |
| 目标架构（Target schema） | 目标架构。 |
| 目标文件名（Target File name） | 目标文件。 |
| 高优先级（High Priority） | 加载文件的优先级。 |
| 类型（Type） | OUTFILE、DUMPFILE。 |
| 字段分隔符（Field separator） | 要使用的字段分隔符。 |
| 字段封闭符（Fields enclosed by） | 要使用的字段封闭符。 |
