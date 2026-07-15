# Hop Arrow 命令

## 概述

`hop arrow` 子命令用于启动 Arrow 相关服务。

### flight-server

### 用法

```bash
sh hop arrow --help
Usage: hop arrow [-hV] [--arrow-flight-host=<hostname>]
                 [--arrow-flight-port=<port>] [-e=<environmentOption>]
                 [-j=<projectOption>]
Run the hop arrow command to start a flight or socket server
      --arrow-flight-host=<hostname>
                  The hostname on which the Apache Arrow Flight server will
                    listen, defaults to 0.0.0.0
      --arrow-flight-port=<port>
                  The port on which the Apache Arrow Flight server will listen,
                    defaults to 33333
  -e, --environment=<environmentOption>
                  The name of the lifecycle environment to use
  -h, --help      Show this help message and exit.
  -j, --project=<projectOption>
                  The name of the project to use
  -V, --version   Print version information and exit.
```

```bash
hop arrow flight-server --environment my-environment --host 0.0.0.0 --port 33333
```

### 工作原理

Hop 将启动一个 Apache Arrow Flight 服务器，监听指定的地址和端口。

> **❗ 重要:** 请确保指定 `--project` 或 `--environment` 选项，以便服务器能够找到引用的 Data Stream metadata。

#### 向 Flight 发送数据

当 Hop Flight 服务器接收到数据时，它会尝试将指定的路径与 [Data Stream](../06-元数据类型/data-stream.md) 名称进行匹配。该 data stream 的类型必须为 "Apache Arrow Flight"。
此时，指定的 [Schema Definition](../06-元数据类型/static-schema-definition.md) 会与接收到的 [Schema](https://arrow.apache.org/cookbook/py/schema) 进行匹配。
数据行被存储在内存中，以便后续被获取。

### 从 Flight 读取数据

使用 Hop 从 Flight 服务器读取数据非常简单，只需引用相同的 Data Stream 即可。

### 支持的选项：

- `--arrow-flight-host` : 绑定地址（默认：0.0.0.0）
- `--arrow-flight-port` : 监听端口（默认：33333）
- `--project` : 用于查找要引用的 Data Stream metadata
- `--environment` : 用于查找项目和 metadata，同时设置变量。

另请参见：[Apache Arrow Flight Data Stream](../06-元数据类型/arrow-flight.md)
