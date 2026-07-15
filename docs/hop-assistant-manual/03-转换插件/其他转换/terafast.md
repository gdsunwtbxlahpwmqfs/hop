# ![Teradata Bulk Loader transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/terafast.svg) Teradata Bulk Loader

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称，此名称在单个 Pipeline 中必须唯一。 |
| Use control file | 以控制文件模式工作。 |
| Control file | 要使用的控制文件路径。 |
| Variable Substitution in control file | 在控制文件中使用 Hop 变量（例如：{openvar}RUN_ID{closevar}）。 |
| Path to fastload | fastload 命令行工具的路径。 |
| Error log | Fastload 创建的可选错误日志。 |
| Connection | 到 Teradata 数据库的连接。 |
| Target table | 要加载的表。 |
| Truncate table | 加载前截断目标表。 |
| Data file | 临时数据文件的名称。 |
| Sessions | Fastload 使用的会话数。 |
| Error limit | Fastload 的错误限制。 |
| Field mapping | Hop 与数据库字段的映射定义。 |

### Fastload 控制文件

作为 Pipeline 中的一个 transform 运行，完全独立于其他 transform。
