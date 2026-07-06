# MySQL批量加载（Bulk load into MySQL）

## 功能概述


`Bulk load into MySQL` 动作将文本文件中的数据加载到 MySQL 表中。
此动作使用 MySQL 的 [`LOAD DATA`](https://dev.mysql.com/doc/refman/8.0/en/load-data.html) 命令。

## 主要选项

| 选项 | 说明 |
|------|------|
| 动作名称（Action name） | 工作流动作的名称。 |
| 连接（Connection） | 要使用的 MySQL 数据库连接。 |
| 目标架构（Target schema） | 要加载的表架构。 |
| 目标表名（Target table name） | 要加载到的表名称。 |
| 源文件名（Source file name） | 要加载的文件名称。 |
| 本地（Local） | 如果文件是本地的，请勾选此项。 |
| 优先级（Priority） | 加载文件的优先级。 |
