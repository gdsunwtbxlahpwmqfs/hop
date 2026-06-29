# Excel 写入器

Excel 写入器（Excel Writer）转换将传入的行写入 Microsoft Excel（.xls、.xlsx）或 OpenDocument 电子表格（.ods）文件。

该转换支持三种输出格式：

- `.xls` — 传统的 Excel 二进制格式（Apache POI）
- `.xlsx` — 现代的 Excel Open XML 格式（Apache POI）
- `.ods` — OpenDocument 电子表格格式（LibreOffice Calc、Apache OpenOffice；通过 ODFDOM 写入）

`.xls` 和 `.xlsx` 后端共享相同的 POI 代码路径。`.ods` 后端是独立的实现，在 ODF 格式允许的范围内镜像相同的转换选项。

`.xls` 文件使用二进制格式，更适合简单内容；`.xlsx` 文件使用 Open XML 格式，与模板配合良好，因为它能更好地保留图表和杂项对象。

## 主要选项

| 选项 | 说明 |
|------|------|
| 转换名称 | 转换的名称 |
| 输出文件 | 输出的 Excel 文件名 |
| 扩展名 | 输出格式（xls、xlsx 或 ods） |
| 模板文件 | 用作模板的 Excel 文件（可选） |
| 工作表 | 要写入的工作表名称 |
| 是否追加 | 是否追加到已有文件 |

## 注意事项

- `.xlsx` 格式更适合使用模板，因为它能更好地保留图表和杂项对象。
- 支持基于模板写入，保留模板中的格式、图表等内容。
