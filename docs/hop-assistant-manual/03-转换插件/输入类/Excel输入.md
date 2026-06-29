# Microsoft Excel 输入

Microsoft Excel 输入转换用于从 Microsoft Excel 电子表格中读取数据。默认的电子表格类型（引擎）设置为 Excel XLSX、XLS。

当你读取其他文件类型（如 OpenOffice ODS）并使用特殊功能（如受保护的工作表）时，需要在"内容"选项卡中相应地更改电子表格类型（引擎）。

## 主要选项

该转换通过多个选项卡进行配置：

| 选项卡 | 说明 |
|--------|------|
| 文件（Files） | 指定要读取的 Excel 文件或目录，支持通配符 |
| 工作表（Sheets） | 选择要读取的工作表及其范围 |
| 内容（Content） | 设置电子表格类型（引擎）、表头、偏移量、限制等 |
| 错误处理（Error Handling） | 配置错误处理行为 |
| 字段（Fields） | 定义要读取的字段及其类型和格式 |

## 注意事项

- 默认引擎支持 Excel XLSX 和 XLS 格式。
- 读取 ODS 等其他格式或使用受保护工作表等特殊功能时，需在"内容"选项卡中更改电子表格类型（引擎）。
- 该转换支持 Hop Engine、Spark、Flink、Dataflow 等多种引擎。
