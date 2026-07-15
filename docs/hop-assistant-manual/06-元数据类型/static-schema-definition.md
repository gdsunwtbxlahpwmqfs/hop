# 静态 Schema 定义

## 描述

![](../assets/images/icons/folder.svg)

静态 Schema 定义描述了一个可以应用于选定输入/输出 transform 集合的流布局。Schema 定义是一种定义可重用流布局的方式，可以在多个 Pipeline 中重复使用。

能够在多个 Pipeline 中使用相同的 schema 定义，简化了开发并减少了手动指定字段或布局时出错的风险。

## 相关 plugin

Transform：

- [Text File Input](../03-转换插件/输入类/textfileinput.md)
- [Text File Output](../03-转换插件/输出类/textfileoutput.md)
- [CSV Input](../03-转换插件/输入类/csvinput.md)
- [Excel Input](../03-转换插件/输入类/excelinput.md)
- [Excel Writer](../03-转换插件/其他转换/excelwriter.md)
- [Schema Mapping](../03-转换插件/其他转换/schemamapping.md)

## 选项

| 选项 | 描述 |
|---|---|
| Name | 此 schema 定义的名称 |
| Description | 此 schema 定义的描述 |
| Field Separator | schema 定义中字段之间使用的分隔符 |
| Enclosure | schema 定义中字段使用的封闭符 |
| Field Definitions | 描述此 schema 定义的文件布局的字段和属性列表。 |
