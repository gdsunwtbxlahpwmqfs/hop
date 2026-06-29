# Google Sheets 输入

Google Sheets 输入（Google Sheets Input）转换从 Google Sheets 工作表中读取数据。

该转换需要 Google 服务账户（JSON 文件）以及启用了 Google Drive 和 Google Sheets API 的 Google Cloud 项目。

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称 | 转换的名称 |
| Google 服务账户 JSON | 服务账户密钥 JSON 文件路径 |
| 电子表格 ID | Google Sheets 电子表格 ID |
| 工作表 | 要读取的工作表名称或范围 |

## 注意事项

- 需要配置 Google 服务账户，并确保 Google Cloud 项目已启用 Google Drive 和 Google Sheets API。
- 需要与该服务账户共享目标 Google Sheets 电子表格。
