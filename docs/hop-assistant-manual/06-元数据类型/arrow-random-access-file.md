# Apache Arrow 随机访问文件数据流

## 概述

此实现写入/读取官方的 **Arrow IPC File Format**（带有页脚）。它支持随机访问，当需要多次读取同一文件或在数据中定位时非常理想。

## 配置

| 属性 | 描述 |
|---|---|
| *Name* | Data Stream 的唯一名称 |
| *File name* | `.arrow` 文件路径（支持变量） |
| *Batch size* | 写入时每批的行数（默认 10,000） |
| *Compression* | None、LZ4、Zstd |

## 使用场景

- 你需要归档或共享数据
- 下游进程需要随机访问
- 你倾向于使用标准 Arrow 文件格式而非流式传输
