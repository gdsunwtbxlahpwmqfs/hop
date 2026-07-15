### Metadata Injection

如果您需要多次创建"几乎"相同的 pipeline，请考虑使用 [Metadata Injection](pipeline/transforms/metainject.md) 来创建可重用的模板 pipeline。

- 避免手动填充对话框
- 当您需要动态 ETL 时
- 支持数据流式传输

示例用例：使用一个 pipeline 模板从 50 种不同的文件格式加载数据到数据库。这有助于您自动标准化和加载属性集。
