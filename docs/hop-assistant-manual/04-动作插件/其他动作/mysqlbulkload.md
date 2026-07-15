# MySQL 批量加载

## 描述

`Bulk load into MySQL` action 将文本文件中的数据加载到 MySQL 表中。

此 action 使用 MySQL 的 [`LOAD DATA`](https://dev.mysql.com/doc/refman/8.0/en/load-data) 命令。

## 选项

| 选项 | 描述 |
|---|---|
| Action name | Workflow action 的名称。 |
| Connection | 要使用的 MySQL 数据库连接。 |
| Target schema | 要加载的目标 schema。 |
| Target table name | 要加载的目标表名。 |
| Source file name | 要加载的文件名。 |
| Local | 如果文件在本地，请勾选此项。 |
| Priority a | 加载文件的优先级。可用选项为： |
| Fields terminated by | 使用的字段分隔符。 |
| Fields enclosed by | 使用的字段封闭符。 |
| Fields escaped by | 使用的转义字符。 |
| Lines started by | 使用的行起始字符串。 |
| Lines terminated by | 使用的行终止字符串。 |
| Fields | 要加载的字段，以逗号（,）分隔。 |
| Replace data | 如果要替换目标表中的数据，请勾选此选项。 |
| Ignore the first ... lines | 忽略文本文件中的前 ... 行。 |
| Add files to result | 如果要在下一个 workflow action 中重复使用该文本文件的文件名，请勾选此选项。 |
