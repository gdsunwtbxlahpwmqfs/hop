# ![MonetDB Bulk Loader transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/monetdbbulkloader.svg) MonetDB Bulk Loader

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## 选项

### General

| 选项 | 描述 |
|---|---|
| Transform name | 指定 Pipeline 中此 MonetDB Bulk Loader Transform 的唯一名称。 |
| Connection | 选择您的 MonetDB 数据库连接 |

### General Settings 选项卡

此选项卡包含目标设置、缓冲区大小和日志文件位置。

| 选项 | 描述 |
|---|---|
| Target Schema | 指定要使用的数据库 schema。 |
| Target Table | 指定数据库表，使用此字段旁边的 Browse 按钮通过菜单选择表和 schema。 |
| Buffer size (rows) | 指定在传输到 MonetDB 之前在内存中保留多少行。 |
| Log file | 指定 MonetDB 返回的批量命令日志的位置。 |
| Truncate table | 在加载数据之前删除目标表中的所有数据。 |
| Fully quote all SQL statements | 执行时强制在所有对象周围加上引号。 |

### MonetDB Settings 选项卡

此选项卡包含有关为加载数据而生成的临时文件的信息。

| 选项 | 描述 |
|---|---|
| Field separator | 这是 Bulk copy 命令中使用的分隔符，输入数据中不允许包含此字段。 |
| Field enclosure | 用于包围值的封闭字符。 |
| Null values represented | Null 值将转换为此字符串，这样可以区分空字符串和 null 值。 |
| Encoding | 为 copy 语句生成文件时使用的文件编码。 |

### Output Fields 选项卡

此选项卡包含源到目标的映射。

| 选项 | 描述 |
|---|---|
| Target table field | 包含目标表中字段名称的字段。 |
| Incoming stream field | 包含要插入目标表的值的字段。 |
| Format is ok | 如果传入流的字段格式符合目标数据类型，则设置为 Y。 |
