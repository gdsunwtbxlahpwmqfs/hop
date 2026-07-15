# ![Merge Join transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/mergejoin.svg) Merge Join

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## 选项

| Option | Description |
|---|---|
| First Transform | 要从中读取数据的第一个 Transform（join 的左侧） |
| Second Transform | 要从中读取数据的第二个 Transform（join 的右侧） |
| Join type | 应使用的 join 类型：INNER、LEFT OUTER、RIGHT OUTER 和 FULL OUTER |
| Key Field | 用于 join key 的字段，仅支持等值连接（key first transform = key second transform） |

有关使用 Merge Join Transform 时避免死锁的指导，请参阅此操作指南：
**[避免死锁](../../16-HowTo指南/avoiding-deadlocks.md)**
