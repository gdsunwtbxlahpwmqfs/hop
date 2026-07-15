# Execution Information Location

## 描述

Execution Information Location 决定了 Qi Hop 可以将执行信息发送到哪里。

执行完成后，可以从 [Execution Information Perspective](../10-HopGUI/perspective-execution-information.md) 查看这些信息。

## 选项

| 选项 | 描述 | 默认值 |
|---|---|---|
| Name | 此 Execution Information Location 的名称 | - |
| Description | 此 Execution Information Location 的描述 | - |
| Data logging delay (ms) |  | 2000 |
| Data logging interval (ms) |  | 5000 |
| Data logging size (rows) |  | 1000 |
| Location type |  |  |
| 要写入的 location plugin 类型。根据类型不同，你可以输入更多特定于 plugin 的选项。 |  |  |

## Location type 特定选项

### 文件位置

使用文件位置可以将执行信息写入本地文件系统上某个路径中的文件。

选项：

- *Root folder*：写入执行信息的文件夹。

### 缓存文件位置

与文件位置类似，执行信息写入文件夹。区别在于信息是按 Pipeline 或 Workflow 分别写入的。所有需要的信息都聚合到这些顶级 JSON 文件中。
实现方式是将执行信息在内存缓存中保留一段可配置的时间。
好处是对文件系统的压力大大减少。
缺点是会使用一些额外的内存。
GUI 中的某些导航操作（例如查找相关执行）将需要读取更多数据。

选项：

- *Root folder*：以 JSON 文件存储执行信息的文件夹
- *Persistence delay*：将执行信息写入磁盘前等待的最长时间，以毫秒为单位。
- *Maximum cache age*：在清除之前，在内存中保留执行信息的最大时间，以毫秒为单位。建议将此值保持在低于预期 Workflow 或 Pipeline 持续时间的值。

### 远程位置

远程位置允许你将执行信息写入 Hop Server。

选项：

- *Hop Server*：写入执行信息的远程 Hop Server。
- *Execution Information Location*：在远程 Hop Server 上使用的 Execution Information Location。

### Neo4j 位置

使用此 location type，你可以将执行信息存储在 [Neo4j](../index.md) 图数据库中。有关此 plugin 的更多信息，请参阅 [Neo4j location type](neo4j-location-type.md) 文档。

### Elastic 位置

Elastic location type 将信息存储在 Elastic 索引中。请参阅 [Elastic location type](elastic-location-type.md)

### 清理

如果你想自动从图数据库中删除执行信息，可以使用 Pipeline 或 Workflow 来实现。
