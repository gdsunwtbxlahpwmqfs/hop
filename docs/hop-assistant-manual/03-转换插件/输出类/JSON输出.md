# JSON 输出

JSON 输出（JSON Output）转换允许你基于输入转换的值生成 JSON 块。

根据转换设置，输出 JSON 可作为 JavaScript 数组或 JavaScript 对象。

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称 | 转换的名称 |
| 输出值 | 要输出到 JSON 的字段名 |
| 字段 | JSON 字段定义（字段名、元素名称等） |
| 分组 | 是否按字段分组 |
| 输出到文件 | 是否将 JSON 写入文件 |

## 注意事项

- 该转换支持 Hop Engine、Spark、Flink、Dataflow 等多种引擎。
- 输出可作为 JavaScript 数组或对象，取决于转换设置。
- 可将 JSON 输出为字段或直接写入文件。
