# XSL pipeline

## 描述

`XSL pipeline` action 使用可扩展样式表语言转换或链接：[XSLT](http://en.wikipedia.org/wiki/XSLT) 将 XML 文档转换为其他文档（XML 或其他格式，如 HTML 或纯文本）。

原文档不会被更改；而是基于 XML 文件的内容创建一个新文档。

## 选项

### General 选项卡

| 选项 | 描述 |
|---|---|
| Action name | Workflow action 的名称。 |
| Get filenames from previous result | 勾选此选项时，XML File name、XSL File name 和 Output File name 从先前的结果集中读取。 |
| XML File name | 源 XML 文件的完整名称 |
| XSL File name | XSL 文件的完整名称 |
| Output File name | 创建的文档的完整名称（XSL 转换的结果） |
| Transformer Factory | 您可以选择 JAXP 或 SAXON 作为转换器工厂。 |
| If file exists a | 定义当同名输出文件已存在时的行为。可用选项为： |
| Add files to result files name | 将目标文件名添加到此 workflow action 的结果文件列表中，以供后续 workflow action 使用。 |

### Advanced 选项卡

| 选项 | 描述 |
|---|---|
| Output properties a | XSL pipeline 输出的可能属性表。选项为： |
| Parameters | 传递给 XSL pipeline 的参数名和值列表 |
