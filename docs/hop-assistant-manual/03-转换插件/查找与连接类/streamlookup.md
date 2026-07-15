# ![Stream Lookup transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/streamlookup.svg) Stream Lookup

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## Options

| Option | Description |
|---|---|
| Transform name | Transform 的名称，此名称在单个 Pipeline 中必须唯一 |
| Lookup Transform | 查找数据来源的 Transform 名称 |
| The keys to lookup... | 允许您指定用于查找值的字段名称。 |
| Fields to retrieve | 您可以在此指定要检索的字段名称，以及值未找到时的默认值，或者如果您不喜欢旧字段名，可以指定新字段名。 |
| Preserve memory | 在排序时编码数据行以节省内存。 |
| Key and value are exactly one integer field | 在执行排序时节省内存。注意：仅在勾选 "Preserve memory" 时有效。 |
| Use sorted list | 启用以使用排序列表存储值；在处理包含宽行的数据集时提供更好的内存使用。 |
| Get fields | 自动填入源端（A）所有可用字段的名称；然后您可以删除不想用于查找的所有字段。 |
| Get lookup fields | 自动插入查找端（B）所有可用字段的名称。 |

有关使用 Stream Lookup transform 时防止死锁的指导，请参阅此操作指南：
**[避免死锁](../../16-HowTo指南/avoiding-deadlocks.md)**
