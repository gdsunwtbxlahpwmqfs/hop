# Cassandra 连接（Cassandra Connection）

## 核心功能

可以通过点击图标（点击它进行编辑）以及下拉菜单（箭头向下）来创建和编辑连接，您可以在各种 Cassandra 动作或转换对话框中每行连接的右侧找到它们。

它们也可以通过 Hop GUI 中的元数据视图进行管理。

Cassandra 连接以 JSON 格式序列化存储在 Hop 元数据文件夹下：

`metadata/cassandra-connection/`

## 主要参数

| 参数 | 说明 |
| --- | --- |
| Hostname（主机名） | 指定连接到 Cassandra 服务器的主机名 |
| Port（端口） | 指定连接到 Cassandra 服务器的端口号 |
| Username（用户名） | 指定目标密钥空间和/或表的认证详细信息的用户名 |
| Password（密码） | 指定目标密钥空间和/或表的认证详细信息的密码 |
| Socket Timeout（套接字超时） | 设置可选的连接超时时间，以毫秒为单位 |
| Keyspace（密钥空间） | 指定密钥空间（数据库）名称。您可以使用"选择密钥空间"按钮来选择密钥空间，使用"执行 CQL"按钮来创建一个。例如：`CREATE KEYSPACE IF NOT EXISTS hop WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 3} ;` |
| Schema hostname（模式主机名） | 仅用于写入：指定连接到 Cassandra 模式的主机名（如果不与主机名不同则留空） |
| Schema port（模式端口） | 仅用于写入：指定连接到 Cassandra 模式的端口（如果不与端口不同则留空） |
| Use compression（是否使用压缩） | 选择是否要在将每条 BATCH INSERT 语句传输到节点之前对其进行压缩（使用 GZIP） |
