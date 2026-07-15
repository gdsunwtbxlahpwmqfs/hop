# Apache Arrow

Apache Arrow 是一种跨语言的列式内存数据格式和工具包，用于高效的分析和数据交换。

Hop 利用 Arrow 在 pipeline 和外部工具（特别是 Python）之间实现高性能流式传输。

Hop 中使用的主要特性：

- Arrow IPC（流式和文件格式）
- Arrow Flight（基于 RPC 的流式传输）

请查看官方文档：

- [Apache Arrow 文档](https://arrow.apache.org/docs/)
- [Arrow Java](https://arrow.apache.org/docs/java/)
- [PyArrow](https://arrow.apache.org/docs/python/)

## Hop 如何使用 Arrow：

- [Apache Arrow Random Access File 数据流类型](metadata-types/data-stream/arrow-random-access-file.md)
- [Apache Arrow File Stream 数据流类型](metadata-types/data-stream/arrow-file-stream.md)
- [Apache Arrow Flight 数据流类型](metadata-types/data-stream/arrow-flight.md)
- [Hop Arrow Flight 服务器](hop-tools/hop-arrow-command.md)
