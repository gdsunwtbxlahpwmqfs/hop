# XSL管道（XSL pipeline）

## 功能概述


`XSL pipeline` 动作使用可扩展样式表语言转换（[XSLT](http://en.wikipedia.org/wiki/XSLT)）将 XML 文档转换为其他文档（XML 或其他格式，如 HTML 或纯文本）。
原始文档不会改变；而是基于 XML 文件的内容创建新文档。

## 主要选项

### 常规选项卡

| 选项 | 说明 |
|------|------|
| 动作名称（Action name） | 工作流动作的名称。 |
| 从先前结果获取文件名（Get filenames from previous result） | 勾选此选项时，XML 文件名、XSL 文件名和输出文件名从先前结果集读取。当存在多行时，将循环。此时 XML 文件名是结果集中的第一个字段，XSL 文件名是第二个字段，输出文件名是第三个字段。 |
