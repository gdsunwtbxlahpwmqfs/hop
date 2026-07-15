# 表是否存在

## 描述

`Table exists` action 验证数据库中是否存在指定的表。

您必须提供连接和表名。

Hop 根据表是否存在返回 True 或 False 值。

假设您有一个外部系统创建汇总表或昨天的数据提取。

外部系统可能尚未执行该操作，因此您设置一个轮询来等待暂存数据到达数据库。

在数据可用之前处理 workflow 没有意义，因此您可以使用此 workflow action 作为信号量来轮询数据库以确定表是否存在。

## 选项

| 选项 | 描述 |
|---|---|
| Action name | Workflow action 的名称。 |
| Connection | 要使用的连接 |
| Schema name | 如果适用于您的数据库，则为 schema 名称 |
| Table name | 要检查的数据库表名 |
