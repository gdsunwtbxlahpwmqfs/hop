# Beam 文件定义

## 描述

![](../assets/images/icons/folder.svg)

Beam 文件定义描述了 Beam Pipeline 中的文件布局，并指定了在 Beam Pipeline 中使用的文件布局（名称、字段定义、封闭符和分隔符）。

## 相关 plugin

Transform：

- [Beam Input](../03-转换插件/其他转换/beamfileinput.md)
- [Beam Output](../03-转换插件/其他转换/beamfileoutput.md)

## 选项

| 选项 | 描述 |
|---|---|
| Name | 此 Beam 文件定义的名称 |
| Description | 此 Beam 文件定义的描述 |
| Field Separator | 文件定义中字段之间使用的分隔符 |
| Enclosure | 文件定义中字段使用的封闭符 |
| Field Definitions | 字段名称、类型、格式、长度和精度的列表 |

## 示例

- beam/pipelines/complex.hpl
- beam/pipelines/generate-synthetic-data.hpl
- beam/pipelines/input-process-output.hpl
- beam/pipelines/switch-case.hpl
- beam/pipelines/unbounded-synthetic-data.hpl
