# Data Stream

## 概述

**Data Stream** 元数据类型提供了一种统一、高性能的方式，在 Hop Pipeline 和外部系统（特别是通过 PyHop 的 Python）之间流式传输行数据，无需总是将数据落地到磁盘。

它被设计为可插拔的，以便随时间推移可以添加不同的流式传输技术。

### 目标

- 实现 Hop 与 Python（及其他 Arrow 兼容工具）之间的快速、低延迟数据交换
- 提供背压和适当的流式传输语义
- 保持一致的用户体验（在 Input/Output transform 中按名称选择 Data Stream）

## 可用实现

- [Apache Arrow 随机访问文件](arrow-random-access-file.md)
- [Apache Arrow 文件流](arrow-file-stream.md)
- [Apache Arrow Flight](arrow-flight.md)

另请参阅：

- [`hop arrow` 命令](../09-Hop工具/hop-arrow-command.md)
- [Apache Arrow 技术页面](../19-技术集成/apache-arrow.md)

## 用法

1. 在 Metadata perspective 中创建新的 **Data Stream**
2. 选择所需的实现
3. 在你的 Pipeline 中使用 **Data Stream Output** 或 **Data Stream Input** transform，并按名称选择流
