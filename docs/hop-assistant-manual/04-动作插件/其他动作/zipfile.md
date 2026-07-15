# Zip 文件

## 描述

`Zip file` action 使用您在对话框中指定的选项创建标准 ZIP 存档。

## 选项

### General 选项卡

| 选项 | 描述 |
|---|---|
| Action name | Workflow action 的名称。 |
| Get arguments from previous a | 此复选框从先前 workflow action 的结果行中获取文件名规范。 |
| Source directory/file | 要压缩文件的源目录 |
| Include wildcard (RegExp) | 要包含在 zip 存档中的文件通配符（正则表达式） |
| Exclude wildcard (RegExp) | 要从 zip 存档中排除的文件通配符（正则表达式） |
| Include sub-folders | 启用此选项以在子文件夹中搜索文件 |
| Zip file name | 目标存档的完整名称 |
| Create parent folder | 如果父文件夹不存在则创建 |
| Include date in filename | 将日期添加到文件名中 |
| Include time in filename | 将时间添加到文件名中 |
| Specify date time format | 允许您指定日期/时间格式掩码， |
| Date time format | 日期时间格式掩码，如 yyyy/MM/dd HHmmss |
| Show filename | 根据您指定的选项显示示例文件名 |

### Advanced 选项卡

| 选项 | 描述 |
|---|---|
| Compression a | 要使用的压缩级别。选项为 |
| If zip file exists a | 当目标位置已存在文件时要采取的操作。选项 |
| After zipping a | 压缩后要采取的操作。选项为 |
| Move files to | 压缩后将源文件移动到的目标目录 |
| Create folder | 创建要移动到的文件夹 |
| Stored source path depth a | 这是源文件路径中在 ZIP 文件存档结构中保留的部分。 |
| Add Zip file to result | 启用此选项以将目标 zip 文件添加到结果中 |
