# Apache Arrow Flight（Apache Arrow Flight Data Stream）

## 核心功能

**Apache Arrow Flight** 通过 gRPC 使用 Arrow 列式格式，提供高性能、低延迟的基于 RPC 的流式传输。

此实现允许 Hop 与 Python（以及其他 Arrow Flight 客户端）交换数据，而无需写入中间文件。

## 配置

创建 **Arrow Flight** 数据流时可用的属性：

| 属性 | 说明 |
| --- | --- |
| Name（名称） | 此数据流的唯一名称。此名称用作 Flight 服务器上的**路径**（`FlightDescriptor.for_path(name)`）。 |
| Description（描述） | 数据流的可选描述。 |

## 适用场景

适用于需要在不同进程或机器之间进行高性能、低延迟数据交换的场景，尤其适合 Hop 与 Python 生态的集成。
