# ![DDL Icon, role="image-doc-icon"](../../assets/images/transforms/icons/ddl.svg) DDL

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## 选项

| Option | Description |
|---|---|
| Transform name | Transform 的名称。 |
| Connection name | 用于 SQL 方言和执行的数据库连接名称。 |
| Table schema | 表的数据库 schema。 |
| Table name | 要为其生成 DDL 的表名。 |
| Column field name | 包含表列名的输入字段名称。 |
| Column type name | 包含列名的 Qi Hop 类型的输入字段名称。它将被转换为所选数据库的正确类型。 |
| Column length name | 包含列长度的输入字段名称。 |
| Column precision name | 包含列精度的输入字段名称。 |
| Execute DDL? | 是否执行生成的 DDL？ |
| DDL output field (optional) | 指定输出中包含生成的 SQL 的字段名称。 |
| Load all data from table | 预加载查找表中存在的所有数据到缓存中。 |
| Drop table before creation? | 如果你想始终重新创建指定的表，请勾选此选项。如果未执行 DDL，则不会删除表。 |

## 示例

我们有 2 行 DDL Transform 的输入行：

| name | hopType | length | precision |
|---|---|---|---|
| id | Integer | 9 | 0 |
| name | String | 100 | -1 |

对于标准的 [H2](https://en.wikipedia.org/wiki/H2_Database_Engine) 数据库，我们可以为表 `ddlTest` 生成 DDL。运行时将生成

```sql
CREATE TABLE ddlTest
(
  id INT
, name VARCHAR(100)
)
;
```

## Hop 元数据

如果你想重用字段级别的元数据，可以考虑使用 [Metadata Input](metadata-input.md) Transform。例如，你可以访问 [Beam File Definition](../../06-元数据类型/beam-file-definition.md) 或 [Static Schema Definition](../../06-元数据类型/static-schema-definition.md) 元素的 JSON。然后你可以使用 [JSON Input](../输入类/jsoninput.md) Transform 解析元数据并提取所需的 4 个字段。
