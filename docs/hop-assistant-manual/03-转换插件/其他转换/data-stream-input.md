# Data Stream Input

## 概述

**Data Stream Input** Transform 从配置的 [Data Stream](../../06-元数据类型/data-stream.md) 中读取行到 Hop Pipeline 中。

它支持所有可用的 Data Stream 实现（Arrow File Stream、Arrow Random Access File 和 Arrow Flight）。

## 选项

| Option | Description |
|---|---|
| *Data Stream* |  |

## 说明

- 该 Transform 将自动使用所选 Data Stream 中定义的实现。
- 使用 Arrow Flight 时，该 Transform 连接到正在运行的 Flight 服务器并使用流名称拉取数据。
- 从基于文件的流读取时，根据实现方式，以顺序（流式格式）或随机访问方式读取文件。
