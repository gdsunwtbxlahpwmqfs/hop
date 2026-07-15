# ![Synchronize after merge transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/synchronizeaftermerge.svg) Synchronize after merge

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Flink | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |
| Dataflow | ![Maybe Supported, 24](../../assets/images/question_mark.svg) |

## 选项

### 常规 选项卡

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称。 |
| Connection | 写入数据的数据库连接。 |
| Target schema | 要写入数据的表的 Schema 名称。 |
| Target table | 您要执行插入/更新/删除操作的表名称。 |
| Commit size | 执行提交前要更改的行数。 |
| Use batch update |  |
| Tablename is defined in a field |  |
| Key Lookup table | 允许您指定字段值和比较器的列表。 |
| Update Fields | 允许您指定表中要插入/更新的所有字段，包括键。 |
| SQL button | 点击 SQL 以生成用于正确操作的创建表和索引的 SQL。 |

### 高级 选项卡

| 选项 | 描述 | 来自 Merge Rows (diff) transform 的默认值 |
|---|---|---|
| Operation fieldname | 这是一个必填字段。 |  |
| Insert when value equal | 指定 Operation fieldname 中表示应执行插入操作的值。 | "new" |
| Update when value equal | 指定 Operation fieldname 中表示应执行更新操作的值。 | "changed" |
| Delete when value equal | 指定 Operation fieldname 中表示应执行删除操作的值。 | "deleted" |
| Perform lookup | 在删除或更新时执行查找。 |  |
