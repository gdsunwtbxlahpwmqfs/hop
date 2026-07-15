# 处理结果文件名

## 描述

`Process result filenames` action 允许您将多个文件和/或文件夹复制、移动或删除到文件系统上的其他位置。

## 选项

| 选项 | 描述 |
|---|---|
| Workflow action name | Workflow action 的名称。 |
| Action a | 选择以下操作之一 |
| Destination folder | 定义目标文件夹 |
| Create destination folder | 当目标文件夹不存在时，使用此选项创建目标文件夹 |
| Replace existing file | 使用此选项覆盖已存在的文件 |
| Remove source filenames from result | 使用此选项从结果文件名列表中移除已处理的文件名 |
| Add destination filenames to result | 使用此选项将已处理的目标文件名添加到结果文件名列表中 |
| Add date to filename | 将日期添加到目标文件名中，例如 yyyyMMdd |
| Add time to filename | 将时间添加到目标文件名中，例如 HHmmss |
| Specify date time format | 允许您指定自定义日期时间格式，例如 yyyyMMdd_HHmmss |
| Add date before extension | 当不勾选此选项时，日期/时间将附加在文件扩展名之后 |
| Limit action to | 定义包含和排除通配符 |
| Wildcard (RegExp) | 用于包含文件的正则表达式通配符 |
| Exclude Wildcard (RegExp | 用于排除文件的正则表达式通配符 |
| Success on a | 成功条件：选择以下选项之一： |
| Nr errors lesser than | 上方 `Success on` 选项中指定的错误数或文件数。 |
