# ![Parquet File Output transform Icon, role="image-doc-icon"](../../assets/images/transforms/icons/parquet_output.svg) Parquet File Output

| Hop Engine | ![Supported, 24](../../assets/images/check_mark.svg) |
|---|---|
| Spark | ![Supported, 24](../../assets/images/check_mark.svg) |
| Flink | ![Supported, 24](../../assets/images/check_mark.svg) |
| Dataflow | ![Supported, 24](../../assets/images/check_mark.svg) |

## 选项

注意：

- 输出文件名中可选引用的日期将是 pipeline 执行开始的时间。
- Hop Date 类型序列化为 EPOC：自 `1970-01-01 00:00:00.000` 以来的毫秒数。
- 字符串以 UTF-8 二进制格式写入。
- 数据压缩为列式格式在内存中完成。
这在所有行写入完成时发生。
为确保不会内存溢出，请务必指定分片大小。

| 选项 | 描述 |
|---|---|
| Transform name |  |
| Transform 的名称，此名称在单个 pipeline 中必须唯一。 |  |
| Base file name |  |
| 指定基本文件名。 |  |
| Extension |  |
| 文件的扩展名。 |  |
| Include date? |  |
| 如果想在文件名中包含日期（掩码 `yyyMMdd`），请勾选此项。 |  |
| Include time? |  |
| 如果想在文件名中包含时间（掩码 `HHmmss`），请勾选此项。 |  |
| Include date-time-format? |  |
| 如果想在文件名中包含特定的自定义日期时间格式，请勾选此项。 |  |
| Include transform copy number? |  |
| 如果在多个副本中运行此 transform，启用此选项可避免多个线程写入同一文件。 |  |
| Split into parts and include number? |  |
| 如果想将输出拆分为多个部分，请启用此选项。 |  |
| Compression codec |  |
| 在此可以指定要使用的压缩编解码器。 |  |
| Version |  |
| 选择 Parquet 的协议版本（1.0 或 2.0）。 |  |
| Row group size |  |
| 一个行组中的行数。 |  |
| Data page size |  |
| 以 1kB 为边界的数据页大小（默认为 1048576）。 |  |
| Dictionary page size |  |
| 以 1kB 为边界的数据字典页大小（默认为 1048576）。 |  |
| Fields |  |
| 可以指定要写入的字段及其顺序。 |  |
