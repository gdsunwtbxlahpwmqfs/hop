# ![MySql Bulk loader transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/mysqlbulkloader.svg) MySql Bulk Loader

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

> **📝 注意:** 批量加载必须在服务器和客户端上都启用。在客户端连接中添加以下选项 `allowLoadLocalInfile=true`。
在服务器端，以下查询应返回 "ON"：`show global variables like 'local_infile';`

### General

| option | description |
|---|---|
| Connection | 批量加载时使用的数据库连接。 |
| Target Schema |（可选）包含要加载表的 schema。 |
| Target Table | 要加载的表的名称。 |
| Fifo file | 临时文件位置。 |
| Delimiter | 用于确定字段的分隔符。 |
| Enclosure | 您可以指定一个封闭字符串，将其放置在值周围时允许值中包含分隔符。 |
| Escape character | 为了在值中包含分隔符字符，有时会使用转义字符串，如反斜杠、双反斜杠等。 |
| Character set (load command) | 加载命令中使用的字符集（可选）。 |
| Character set (file creation) | 创建文件时使用的字符集（可选）。 |
| Bulk size (rows) | 这将把数据加载拆分为多个数据块。 |
| Use replace clause | 使用 REPLACE，与现有行中唯一键值相同的新行将替换现有行。 |
| Use Ignore clause | 使用 IGNORE，在唯一键值上与现有行重复的新行将被丢弃。 |
| Local data | 如果未指定 LOCAL，文件必须位于服务器主机上。 |

### Fields

| option | description |
|---|---|
| Table field | 表中字段的名称。 |
| Stream field | 流中字段的名称。 |
| Field format OK? a | 您可以决定是保持格式不变（Don't change formatting）还是更改格式： |
