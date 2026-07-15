# ![Get table names transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/gettablenames.svg) Get table names

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 预览

*Preview* 按钮打开与 Table Input 相同的预览设置对话框（行限制和查询超时，以秒为单位）。JDBC 语句超时**仅在 Hop GUI 的预览模式下**应用。

您可以通过 [`HOP_QUERY_PREVIEW_TIMEOUT`](variables.md#_available_global_variables.md) 应用变量设置超时字段的默认值。有关相同的行为详情，请参阅 [Table Input: Preview](pipeline/transforms/tableinput.md#_preview.md)。

## 选项

| 选项 | 描述 |
|---|---|
| Transform name | 此 transform 在 pipeline 工作区中显示的名称 |
| Connection | 要使用的连接 |
| Schema name | 要使用的 schema |
| Get schema from field | 允许传递包含 schema 名称的字段 |
| Schema name field | 包含 schema 名称的字段 |
| Include catalogs | 在输出中包含 catalog |
| Include schemas | 在输出中包含 schema |
| Include views | 在输出中包含视图 |
| Include procedures | 在输出中包含存储过程 |
| Include synonyms | 在输出中包含同义词 |
| Add schema in object name | 将 schema 添加到对象名称中 |
| Tablename fieldname | 包含表名的输出字段 |
| Object type fieldname | 包含对象类型的输出字段（catalog、schema、table 等） |
| Is system object fieldname | 包含布尔值的输出字段：该对象是否为系统对象 |
| Creation SQL fieldname | 包含对象创建语句的输出字段 |
