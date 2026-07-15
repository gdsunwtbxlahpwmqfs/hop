# ![Doris Bulk Loader transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/dorisbulkloader.svg) Doris Bulk Loader

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

### 常规

| Option | Description |
|---|---|
| Transform name |  |
| Transform 的名称；该名称在单个 Pipeline 中必须唯一 |  |
| FE Host |  |
| FE Host 的主机名 |  |
| FE HTTP Port |  |
| 用于连接 FE Host 的端口 |  |
| Database Name |  |
| 你要连接的数据库 |  |
| Table Name |  |
| 要批量加载记录的表名 |  |
| Login User |  |
| 用于连接数据库的用户 |  |
| Login Password |  |
| 连接数据库的密码 |  |
| Data Field |  |
| 数据必须合并为单个字段，可以是 csv 风格的字段或 json 数据 |  |
| Format |  |
| 指示 Transform 期望的是 CSV 风格还是 JSON 风格数据 |  |
| Line Delimiter |  |
| 用于指示行尾的分隔符 |  |
| Column separator |  |
| 使用 CSV 风格数据时，这将指示用什么字符拆分字段 |  |
| Buffer Size |  |
| 缓冲区大小（字节，0 = 无限制） |  |
| Buffer Count |  |
| Buffer Size * Buffer Count 是执行真正流式加载前缓冲数据的最大容量（0 = 无限制） |  |

### Headers

| Option | Description |
|---|---|
| Header/value |  |
| 使用此项向批量加载命令添加额外选项 |  |
