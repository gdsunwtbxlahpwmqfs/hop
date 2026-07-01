# Redshift 批量加载器

Redshift 批量加载器（Redshift Bulk Loader）转换使用 [COPY](https://docs.aws.amazon.com/redshift/latest/dg/r_COPY.html) 命令将数据从 hfxt data process 加载到 AWS Redshift。

> **提示**：确保目标 Redshift 表的布局与 Parquet 数据类型兼容，例如使用 `int8` 而非 `int4` 数据类型。

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称 | 转换的名称 |
| 数据库连接 | 目标 Redshift 数据库连接 |
| 目标表 | 要加载的目标表名 |
| S3 存储桶 | 用于暂存数据的 S3 存储桶 |
| AWS 凭据 | AWS 访问凭据 |
| 字段映射 | 输入字段到表列的映射 |

## 注意事项

- 该转换通过 COPY 命令从 S3 加载数据到 Redshift。
- 目标表的数据类型需与 Parquet 格式兼容（如使用 `int8`）。
- 需要 AWS S3 存储桶和相应的访问权限。
