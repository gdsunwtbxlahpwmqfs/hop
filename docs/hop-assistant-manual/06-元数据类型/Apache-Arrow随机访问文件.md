# Apache Arrow 随机访问文件（Apache Arrow Random Access File）

## 核心功能

此实现写入/读取官方的 **Arrow IPC 文件格式**（带 footer）。它支持随机访问，当您需要多次读取同一文件或在数据中查找时非常理想。

## 配置

| 属性 | 说明 |
| --- | --- |
| Name（名称） | 数据流的唯一名称 |
| File name（文件名） | `.arrow` 文件的路径（支持变量） |
| Batch size（批大小） | 写入时每批的行数（默认 10,000） |
| Compression（压缩） | 无、LZ4、Zstd |

## 适用场景

适用于需要多次读取同一文件、在数据中进行随机查找，或需要持久化存储 Arrow 格式数据的场景。
