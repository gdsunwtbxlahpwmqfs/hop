# ![Salesforce Delete transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/SFD.svg) Salesforce Delete

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 用法

您可以通过指定起始点（Fieldname id）和要删除的记录数量（Batch Size）来删除一批记录。

您还可以使用以下其他 transform 来修改您的 Salesforce 数据库：

- [Salesforce Input](../输入类/salesforceinput.md)：直接从 Salesforce 数据库读取记录。
- [Salesforce Insert](salesforceinsert.md)：直接向 Salesforce 数据库插入记录。
- [Salesforce Update](salesforceupdate.md)：直接更新 Salesforce 数据库中的记录。
- [Salesforce Upsert](salesforceupsert.md)：更新现有记录并插入新记录到 Salesforce 数据库。

## 常规

在 transform 名称字段中输入以下信息：

- Transform name：指定画布上 Salesforce Delete transform 的唯一名称。
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

| Fieldname id a | 选择包含您要删除的记录集（批次）中第一条记录的 Salesforce ID 的字段名称。 |
|---|---|
