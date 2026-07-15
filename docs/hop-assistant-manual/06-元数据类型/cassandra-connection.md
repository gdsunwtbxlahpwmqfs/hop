# Cassandra 连接

## 描述

连接可以通过 logo 创建和编辑（点击 logo 进行编辑），也可以通过下拉菜单（向下箭头）创建，该菜单位于各种 Cassandra action 或 transform 对话框中每行连接的右侧。

也可以在 Hop GUI 的 metadata perspective 中管理。
最后，请注意 Cassandra 连接以 JSON 格式序列化存储在 Hop 元数据文件夹中：

`metadata/cassandra-connection/`

## 选项

| 选项 | 描述 |
|---|---|
| Hostname |  |
| 指定连接到 Cassandra 服务器的主机名 |  |
| Port |  |
| 指定连接到 Cassandra 服务器的端口号 |  |
| Username |  |
| 指定目标 keyspace 和/或表的认证详细信息的用户名 |  |
| Password |  |
| 指定目标 keyspace 和/或表的认证详细信息的密码 |  |
| Socket Timeout |  |
| 设置可选的连接超时时间，以毫秒为单位。 |  |
| Keyspace |  |
| 指定 keyspace（数据库）名称。 |  |
| Schema hostname |  |
| 仅用于写入：指定连接到 Cassandra schema 的主机名（如果不与 hostname 不同则留空） |  |
| Schema port |  |
| 仅用于写入：指定连接到 Cassandra schema 的端口（如果不与 port 不同则留空） |  |
| Use compression |  |
| 选择是否要在将每个 BATCH INSERT 语句的文本传输到节点之前进行压缩（使用 GZIP）。 |  |
