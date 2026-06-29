# Beam 文件定义（Beam File Definition）

## 核心功能

Beam 文件定义描述了 Beam 管道中的文件布局，指定在 Beam 管道中使用的文件布局（名称、字段定义、封闭符和分隔符）。

## 主要参数

| 参数 | 说明 |
| --- | --- |
| Name（名称） | 此 Beam 文件定义使用的名称 |
| Description（描述） | 此 Beam 文件定义使用的描述 |
| Field Separator（字段分隔符） | 文件定义中字段之间使用的分隔符 |
| Enclosure（封闭符） | 文件定义中字段使用的封闭符 |
| Field Definitions（字段定义） | 字段名、类型、格式、长度和精度的列表。描述此字段定义的文件布局 |

## 相关插件（转换）

- Beam Input（Beam 输入）
- Beam Output（Beam 输出）
