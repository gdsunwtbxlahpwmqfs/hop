# Data Stream Output

## 概述

**Data Stream Output** Transform 将行从 Hop Pipeline 写入配置的 [Data Stream](/metadata-types/data-stream/data-stream.md)。

它支持所有可用的 Data Stream 实现（Arrow File Stream、Arrow Random Access File 和 Arrow Flight）。

## 选项

| Option | Description |
|---|---|
| *Data Stream* |  |

## 说明

- 该 Transform 将自动使用所选 Data Stream 中定义的实现（基于文件或 Flight）。
- 使用 Arrow Flight 时，数据使用流名称直接发送到正在运行的 Flight 服务器。
