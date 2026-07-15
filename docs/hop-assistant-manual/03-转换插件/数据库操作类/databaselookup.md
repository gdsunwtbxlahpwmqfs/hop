# ![Database Lookup transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/dblookup.svg) Database Lookup

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## 选项

| 选项 | 描述 |
|---|---|
| Transform name | Transform 的名称。 |
| Connection | 到查找表的数据库连接。 |
| Lookup schema | 包含查找表的数据库 schema。 |
| Lookup Table | 用于查找的数据库表名称。 |
| Enable cache? | 启用数据库查找缓存。 |
| Cache size in rows | 缓存大小（行数），0 表示缓存所有内容。 |
| Load all data from table | 预加载查找表中存在的所有数据到缓存。 |
| Keys to look up table | 执行数据库查找的键和条件。 |
| Values to return table a | 要添加到输出流的查找表字段。 |
| Do not pass the row if the lookup fails | 启用此项可在查找失败时不传递行。 |
| Fail on multiple results? | 启用此项可强制 transform 在查找返回多个结果时失败。 |
| Order by | 如果查找查询返回多个结果，ORDER BY 子句可帮助您选择要取的记录。 |
| Get Fields | 点击可返回 transform 输入流中可用字段的列表。 |
| Get lookup fields | 点击可返回查找表中可添加到 transform 输出流的可用字段列表。 |

*关于 Load all data from table 选项的说明*：例如：当存储类型为"CHAR(3)"的值时，许多数据库会使用 3 个字符存储"ab"："ab "（注意空格）。
当您执行"SELECT * FROM my_lookup_table WHERE key_column = 'ab'"时，数据库很聪明，会在应用 where 子句之前添加一个空格（它查找的是 'ab ' 而不是 'ab'）。

当您使用"load all data from table"预加载缓存时会出现问题：启动时，缓存使用数据库中存在的所有值构建，因此我们在缓存中存储了 'ab '。
之后，查找 'ab' 会失败，因为在 Java 相等性比较中尾随空格是有影响的。

请注意，如果禁用"load all data from table"，使用缓存时不会出现此问题。
事实上，第一次查找 'ab' 会在缓存中找不到条目，调用数据库（数据库足够智能可以处理尾随空格问题），获得正确结果，然后将其以 'ab' 为键存储在 Java 缓存中。
因此，下次查找 'ab' 时会在缓存中找到正确的结果 :)
