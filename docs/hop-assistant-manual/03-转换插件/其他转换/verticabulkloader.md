# ![Vertica Bulk Loader transform Icon, role="image-doc-icon", width="60px"](../../assets/images/transforms/icons/vertica.svg) Vertica Bulk Loader

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

### 全局 transform 选项

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称。 |
| Connection | 目标表所在的数据库连接名称。 |
| Target schema | 要写入数据的表的 Schema 名称。这对于允许表名中包含点 '.' 的数据源很重要。 |
| Target table | 目标表的名称。 |
| Truncate table | 加载数据前截断此表。 |
| Truncate on first row | 仅当有数据时（收到第一行时）截断此表。 |
| Specify database fields | 启用此选项以在 Database fields 选项卡中指定字段。否则默认情况下将考虑所有字段。 |

### 主要选项 选项卡

| 选项 | 描述 |
|---|---|
| Insert directly to ROS | 直接插入到 ROS（Read Optimized Store）。ROS（Read Optimized Store）容器是存储在特定文件组中的一组行。 |
| Abort on error | 发生错误时是停止还是继续加载数据。 |
| Exceptions log file | 异常日志文件的路径。 |
| Reject data log file | 被拒绝数据日志文件的路径。 |
| Stream name | Vertica COPY 流的名称。 |

### 数据库字段 选项卡

使用 'Get Fields' 和/或 'Enter Field Mapping' 将表列映射到流字段。
