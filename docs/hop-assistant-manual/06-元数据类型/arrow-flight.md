# Apache Arrow Flight 数据流

## 概述

**Apache Arrow Flight** 通过 gRPC 使用 Arrow 列式格式，提供高性能、低延迟的基于 RPC 的流式传输。

该实现允许 Hop 与 Python（及其他 Arrow Flight 客户端）交换数据，而无需写入中间文件。

## 配置

创建 **Arrow Flight** 数据流时，可以使用以下属性：

| 属性 | 描述 |
|---|---|
| *Name* |  |
| *Description* |  |
| *Static Schema* |  |
| *Batch Size* |  |
| *Maximum Buffer Size* |  |

> **❗ 重要:** 这是一个 IPC 系统，而不是一个安全的数据队列。其目的是尽快将数据移交给接收方。这就是为什么我们在向 Flight 服务器写入数据时不会发生阻塞。为了避免 gRPC 后端系统停滞（这会导致数据丢失），数据行会保留在内存中。唯一需要等待的时间是从 Flight 服务器读取数据时。因此建议先用一个进程开始读取数据，再用另一个进程写入数据。已配置的超时时间为 1 分钟，给你充足的时间。

## 重要行为

- **流名称**就是 Data Stream 元数据元素的名称。
- Schema 验证是严格的：客户端必须使用与 **Static Schema** 字段中定义的完全相同的 schema 来发送数据。
- 必须使用 `hop arrow` [命令](../09-Hop工具/hop-arrow-command.md)单独启动 Flight 服务器。

## 相关内容

- [`hop arrow` 命令](../09-Hop工具/hop-arrow-command.md)

## Python 读取示例

以下是使用 Python 从 Hop Flight 服务器读取数据的示例。流名称为 `FlightStream`，服务器在默认的 `0.0.0.0:33333` 上启动：

```python
import pyarrow.flight as flight
import pyarrow as pa

# Connect to your Flight server
client = flight.FlightClient("grpc://localhost:33333")   # or "grpc://0.0.0.0:33333"

# Define the stream name (must match what your server expects: the name of the Data Stream metadata element)
stream_name = "FlightStream"

# 1. Get FlightInfo (this gives you the schema and other metadata)
descriptor = flight.FlightDescriptor.for_path(stream_name)

flight_info = client.get_flight_info(descriptor)

print(f"Schema: {flight_info.schema}")
print(f"Descriptor: {flight_info.descriptor}")
print(f"Endpoints: {len(flight_info.endpoints)}")

# 2. Read the data using the first endpoint
reader = client.do_get(flight_info.endpoints[0].ticket)

# Option A: Read everything into one Table (simple)
table = reader.read_all()
print(f"✅ Read {len(table)} rows from stream '{stream_name}'")

# Show preview
print(table.to_pandas().head())
```
