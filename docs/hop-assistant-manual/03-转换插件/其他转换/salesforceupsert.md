# ![Salesforce Upsert transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/SFU.svg) Salesforce Upsert

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 常规

在 transform 名称字段中输入以下信息：

- Transform name：指定画布上 Salesforce Upsert transform 的唯一名称。
您可以自定义名称或保留默认值。

### 连接

Salesforce transform 可以使用预定义的 [Salesforce Connection](salesforce-connection.md)，或直接使用内联连接设置。
当使用 Salesforce Connection 时，将使用在 metadata 中定义的连接设置（URL、用户名、密码、OAuth 令牌）。
当不使用 Salesforce Connection 时，需要在 transform 中直接指定连接详情。

| 选项 | 描述 |
|---|---|
| Salesforce Connection | （可选）要使用的 [Salesforce Connection](salesforce-connection.md)。选择后，将使用来自 metadata 的连接设置，内联连接字段将被禁用。 |
| Salesforce Webservice URL a | 指定 Salesforce Webservice 的 URL。 |
| Username | 指定用于 Salesforce 认证的用户名 |
| Password | 指定用于 Salesforce 认证的密码。 |
| Test Connection | 点击以验证是否可以连接到您指定的 Salesforce Webservice URL。 |

### 设置

在以下 transform 选项中输入插入流程设置：

| 选项 | 描述 |
|---|---|
| Time out | 指定 transform 超时前的超时间隔（毫秒）。 |
| Use compression | 选择以在 Hop 和 Salesforce 之间连接时压缩（.gzip）数据。 |
| Rollback all changes on | 除非所有记录都成功处理，否则回滚更改。 |
| Batch Size | 指示提交插入前要收集的最大记录数。 |
| Module a | 选择要插入记录的模块（表）。 |

| Upsert Comparison Field | 指定用作比较基础的字段的 Salesforce ID，以确定您是更新现有记录还是插入新记录。 |
|---|---|

### 输出字段

在以下 transform 选项中输入输出信息：

Salesforce ID fieldname：指定包含您更新和插入的记录集（批次）中第一条记录的 Salesforce ID 的字段。

### 字段

您可以通过 Fields 表指定要插入 Salesforce 数据库的字段。
点击 Get fields 以从 Hop 数据流中填充该表。

该表包含以下列：

| 选项 | 描述 |
|---|---|
| Module field | Module 内的字段名称 |
| Stream field | Hop 数据流中的字段名称 |
| Use External id? a | 指示该字段是否链接到外部 ID 的标志。 |

或者，点击 Edit mapping 以指定自定义映射。
例如，您可以将单个 Hop 字段映射到多个 Salesforce 字段。
