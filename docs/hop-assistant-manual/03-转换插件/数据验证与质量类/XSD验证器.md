# XSD 验证器（XSD Validator）

XSD Validator 转换对文件或输入字段中的数据执行 XSD 验证。

XSD 是 XML Schema Definition（XML 模式定义）的缩写。该转换涉及两个实体：要验证其结构的 XML 数据，以及描述 XML 应有结构的 XSD（模式）文件。需要注意的是，XSD 也可以直接嵌入存储在 XML 文档本身之中。

另请参阅 XSD Validator 工作流动作（workflow action）。

## 主要选项

| 选项 | 说明 |
|------|------|
| XML 来源 | 指定要验证的 XML 数据来源（文件或输入字段） |
| XSD 文件 | 指定用于验证的 XSD 模式文件路径 |
| 内部 XSD | 当 XSD 嵌入在 XML 中时，启用此选项 |
| 输出字段 | 指定存放验证结果（通过/失败及错误信息）的输出字段 |
