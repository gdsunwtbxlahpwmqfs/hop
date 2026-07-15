# Apache Arrow 文件流数据流

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

## 重要行为

- **流名称**就是 Data Stream 元数据元素的名称。
- Schema 验证是严格的：客户端必须使用与 **Static Schema** 字段中定义的完全相同的 schema 来发送数据。
- 必须使用 `hop arrow flight-server` 命令单独启动 Flight 服务器。

## 相关内容

- [`hop arrow` 命令](../09-Hop工具/hop-arrow-command.md)

## Python

### 生成 Arrow 流文件

```python
import pyarrow as pa
import pyarrow.ipc as ipc
import pandas as pd
from datetime import datetime

schema = pa.schema([
    ("id", pa.int64()),
    ("name", pa.string()),
    ("value", pa.float64()),
    ("timestamp", pa.timestamp("ms")),
    ("active", pa.bool_())
])

# Generate 10 batches of 500 rows (5000 rows)
#
number_of_batches=10
batch_size=500

with open("from_python.arrow", "wb") as f:
    #
    # Write data in an Arrow File Stream format.
    # This can be read back by Qi Hop using the Arrow File Stream data stream plugin type.
    #
    with ipc.new_stream(f, schema) as writer:
        for i in range(number_of_batches):
            batch = pa.RecordBatch.from_pydict({
                "id": list(range(i*batch_size`1, i*batch_size ` batch_size+1)),
                "name": [f"row_{j}" for j in range(i*batch_size, i*batch_size + batch_size)],
                "value": [j * 1.23 for j in range(i*batch_size, i*batch_size + batch_size)],
                "timestamp": [datetime.now() for _ in range(batch_size)],
                "active": [j % 2 == 0 for j in range(i*batch_size, i*batch_size + batch_size)]
            }, schema=schema)
            writer.write_batch(batch)

print("✅ Wrote from_python.arrow in Arrow IPC Streaming Format")
```

然后可以使用 [Data Stream Input](../03-转换插件/其他转换/data-stream-input.md) transform 和 [Apache Arrow 文件流](arrow-file-stream.md)数据流类型来读取此文件。

### 读取流文件

以下示例读取并打印指定流文件中的所有行：

```python
import pyarrow as pa
import pyarrow.ipc as ipc

file_path = "/path/to/stream.arrow"

# Best practice: use memory mapping for performance
with pa.memory_map(file_path, 'r') as source:
    reader = ipc.open_stream(source)

    schema = reader.schema
    print("Schema:", schema)

    # Read everything into one Table (simple & efficient for most cases)
    table = pa.Table.from_batches(reader)     # or list(reader) then from_batches
    print(f"Total rows: {len(table)}")

    # Convert to pandas (or Polars) if needed
    df = table.to_pandas()

    df = df.reset_index()

    for index, row in df.iterrows():
        print(row['id'], row['str'], row['num'], row['int'], row['uuid'], row['sysdate'])

    #print(df.to_csv())
```

你可以使用 [Data Stream Output](../03-转换插件/其他转换/data-stream-output.md) transform 和 [Apache Arrow 文件流](arrow-file-stream.md)数据流类型来生成 `.arrow` 流文件。
