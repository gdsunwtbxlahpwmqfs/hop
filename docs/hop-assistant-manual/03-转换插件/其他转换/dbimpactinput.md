# ![Database Impact Input, role="image-doc-icon"](../../assets/images/icons/database.svg) Database Impact Input

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## 选项

| Option | Description |
|---|---|
| Transform name |  |
| Transform 的名称。 |  |
| File name field |  |
| Transform 输入中将包含要分析数据库影响的文件名的字段名称。 |  |

> **💡 提示:** 你可以使用 [Get File Names](pipeline/transforms/getfilenames.md) Transform 作为输入。要搜索你的项目 Pipeline，请指定文件夹 `{openvar}PROJECT_HOME{closevar}` 并使用通配符 `.*\.hpl$`。

## 输出字段

| Fieldname | Type |
|---|---|
| Type | String |
| PipelineName | String |
| PipelineFileName | String |
| TransformName | String |
| DatabaseName | String |
| DatabaseTable | String |
| TableField | String |
| FieldName | String |
| FieldOrigin | String |
| SQL | String |
| Remark | String |
