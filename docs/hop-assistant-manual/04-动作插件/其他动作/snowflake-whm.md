# Snowflake 仓库管理器

`Snowflake warehouse manager` action 提供创建、删除、恢复、暂停和修改 Snowflake 仓库的功能。

这使得 Qi Hop workflow 可以在加载数据前恢复仓库，并在完成后立即暂停仓库，还可以为可能需要更多处理能力的加载部分调整仓库大小。

## 选项

- **Connection**：要使用的 Snowflake 数据库连接
- **Warehouse name**：仓库名称
- **Action**：（创建、删除、恢复、暂停、修改）您要对仓库执行的操作。

### 操作：创建仓库

此操作使用您提供的设置创建一个新仓库。

| 选项 | 描述 |
|---|---|
| Replace? | 如果仓库已存在，则用新设置替换现有仓库。 |
| Fail if warehouse exists? | 如果仓库已存在，则 action 将失败。如果不勾选且 Replace? 也不勾选，则当仓库已存在时 action 不执行任何操作。 |
| Warehouse Size | （X-Small、Small、Medium、Large、X-Large、2X-Large、3X-Large）要创建的仓库大小。 |
| Warehouse Type | （Standard、Enterprise）要创建的仓库类型。Enterprise 仓库比 Standard 仓库拥有更多可用内存。 |
| Max cluster size | 集群仓库的最大集群大小。 |
| Min cluster size | 集群仓库的最小集群大小。 |
| Auto suspend | 仓库在不活动多少分钟后自动暂停。 |
| Auto resume? | 当对仓库运行查询时是否应自动恢复？ |
| Initially suspended? | 仓库是否应以暂停状态创建。 |
| Resource monitor | 用于跟踪 Snowflake 使用量和计费的资源监视器。 |
| Comment | 包含的有关仓库的注释。 |

### 操作：删除仓库

此操作从 Snowflake 中删除仓库。

| 选项 | 描述 |
|---|---|
| Fail if warehouse does not exist? | 如果仓库不存在，则 action 将失败。 |

### 操作：恢复仓库

此操作恢复 Snowflake 中的仓库。在此操作之前，仓库必须处于暂停状态，否则 action 将失败。

| 选项 | 描述 |
|---|---|
| Fail if warehouse does not exist? | 如果仓库不存在，则 action 将失败。 |
### 操作：暂停仓库

此操作暂停 Snowflake 中的仓库。在此操作之前，仓库必须处于启动状态，否则 action 将失败。

| 选项 | 描述 |
|---|---|
| Fail if warehouse does not exist? | 如果仓库不存在，则 action 将失败。 |

### 操作：修改仓库

此操作修改仓库，使用户能够重新调整大小、更改仓库类型、更改自动暂停设置等。

| 选项 | 描述 |
|---|---|
| Fail if warehouse does not exist? | 如果仓库不存在，则 action 将失败。 |
| Warehouse Size | （X-Small、Small、Medium、Large、X-Large、2X-Large、3X-Large）要创建的仓库大小。 |
| Warehouse Type | （Standard、Enterprise）要创建的仓库类型。Enterprise 仓库比 Standard 仓库拥有更多可用内存。 |
| Max cluster size | 集群仓库的最大集群大小。 |
| Min cluster size | 集群仓库的最小集群大小。 |
| Auto suspend | 仓库在不活动多少分钟后自动暂停。 |
| Auto resume? | 当对仓库运行查询时是否应自动恢复？ |
| Resource monitor | 用于跟踪 Snowflake 使用量和计费的资源监视器。 |
| Comment | 包含的有关仓库的注释。 |
