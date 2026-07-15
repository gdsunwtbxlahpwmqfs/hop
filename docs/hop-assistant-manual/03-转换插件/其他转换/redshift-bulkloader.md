# ![Redshift Bulk Loader transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/redshiftbulkloader.svg) Redshift Bulk Loader

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

> **❗ 重要:** Redshift Bulk Loader 与数据库类型相关联。它会从 hop/lib/jdbc 文件夹中获取 JDBC 驱动。
## 常规选项

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称。 |
| Connection | 目标表所在的数据库连接名称。 |
| Target schema | 要写入数据的目标 schema 名称。 |
| Target table | 要写入数据的目标表名称。 |
| AWS Authentication a | 选择与 `COPY` 命令一起使用的认证方式。支持的选项有 `AWS Credentials` 和 `IAM Role`。 |
| Use AWS system variables | （仅限 `Credentials`！）从操作系统的环境变量中获取 `AWS_ACCESS_KEY_ID` 和 `AWS_SECRET_ACCESS_KEY` 的值。 |
| AWS_ACCESS_KEY_ID | （如果选择了 `Credentials` 且未勾选 `Use AWS system variables`）指定 `AWS_ACCESS_KEY_ID` 的值或变量。 |
| AWS_SECRET_ACCESS_KEY | （如果选择了 `Credentials` 且未勾选 `Use AWS system variables`）指定 `AWS_SECRET_ACCESS_KEY` 的值或变量。 |
| IAM Role | （如果选择了 `IAM Role`）指定要使用的 IAM Role，格式为 `arn:aws:iam::<aws-account-id>:role/<role-name>` |
| Truncate table | 加载数据前截断目标表。 |
| Truncate on first row | 加载数据前截断目标表，但仅在收到第一条数据行时执行（当 Pipeline 运行空流（0 行）时不会截断）。 |
| Specify database fields | 指定数据库字段与流字段的映射 |

## 主要选项

| 选项 | 描述 |
|---|---|
| Stream to S3 CSV | 在执行 `COPY` 加载之前，将当前 Pipeline 流写入 S3 存储桶中的 CSV 文件。 |
| Load from existing file | 不流式传输当前 Pipeline 的内容，而是从 S3 中已有的文件执行 `COPY` 加载。支持的格式有 `CSV`（逗号分隔）和 `Parquet`。 |
| Copy into Redshift from existing file | S3 中要从中 `COPY` 加载数据的文件路径。 |

## 数据库字段

将当前流字段映射到 Redshift 表的列。
