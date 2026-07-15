# ![CrateDB Bulk Loader transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/cratedbbulkloader.svg) CrateDB Bulk Loader

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

> **❗ 重要:** CrateDB Bulk Loader 与数据库类型关联。当使用 COPY 模式时，它将从 hop/lib/jdbc 文件夹获取 JDBC 驱动。
## 常规选项

| Option | Description |
|---|---|
| Transform name | Transform 的名称。 |
| Target schema | 要写入数据的目标 schema 名称。这是必填字段，因为 CrateDB 需要知道写入哪个默认 schema（`doc` 和 `blob` 是 CrateDB 中的默认 schema）。 |
| Target table | 要写入数据的目标表名。 |

## 主要选项

| Option | Description |
|---|---|
| Connection | 目标表所在的数据库连接名称。 |
| Use HTTP Endpoint | 选择将数据加载到 CrateDB 的模式。支持的选项有 `HTTP Endpoint` 和 `COPY`；当选择 `HTTP Endpoint` 时，`COPY` 选项被禁用，反之亦然。 |
| Batch size | HTTP 模式以批量方式写入。必须设置单个批次发送到 CrateDB 的行数，因为没有默认值。 |
| Specify database fields | 指定数据库和流字段的映射 |
| Stream to file | 在执行 `COPY` 加载之前，将当前 Pipeline 流写入本地文件系统或 S3 中的文件。 |
| Local folder | 存储将由 `COPY` 命令使用的文件的本地文件夹。 |
| Read from file | 不流式传输当前 Pipeline 的内容，而是从本地文件系统或 S3 中的现有文件执行 `COPY` 加载。支持的格式为 `CSV`（逗号分隔）。 |

## AWS 身份验证

| Option | Description |
|---|---|
| Use AWS system variables | 选中后，从操作系统的环境变量中获取 `AWS_ACCESS_KEY_ID` 和 `AWS_SECRET_ACCESS_KEY` 值。 |
| AWS_ACCESS_KEY_ID | （如果未选中 `Use AWS system variables`）指定你的 `AWS_ACCESS_KEY_ID` 的值或变量。 |
| AWS_SECRET_ACCESS_KEY | （如果未选中 `Use AWS system variables`）指定你的 `AWS_SECRET_ACCESS_KEY` 的值或变量。 |

## HTTP 身份验证
目前，Hop 仅支持 `Basic` 和 `Bearer` 身份验证方法。

| Option | Description |
|---|---|
| HTTP Login | 输入 HTTP 身份验证的用户名 |
| HTTP password | 输入 HTTP 身份验证的密码 |

## 字段

将当前流字段映射到 CrateDB 表的列。
