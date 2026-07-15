# 检查 XML 文件是否格式良好

## 描述

`Check if XML file is well-formed action` 验证一个或多个文件是否包含格式良好（合法）的 XML 内容。

## 选项

### General 选项卡

| 选项 | 描述 |
|---|---|
| Action name | Workflow action 的名称。 |
| Include Subfolders | 移动子文件夹内容的选项。 |
| Copy previous results to args | 勾选此项以将先前 action 的结果传递到此 action 的参数中。 |
| File/Folder source | 输入或选择（Browse 按钮）文件或文件夹 |
| Wildcard (RegExp) | 文件名模式的正则表达式通配符 |
| Files/Folders a | 在此网格中指定带有通配符（正则表达式）的文件或文件夹列表。 |

### Advanced 选项卡

在此选项卡中，您可以指定要移动的文件和/或文件夹。

| 选项 | 描述 |
|---|---|
| Success on | 允许您设置特定的成功条件：所有文件格式良好时成功、至少 x 个文件格式良好时成功，或格式不良文件数少于指定值时成功。 |
| Result files name | 指定要添加到内部结果文件名中的文件名类型：所有文件名、仅格式良好的文件名，或仅格式不良的文件名。 |
| Add filename a | 将检查的 XML 文件名添加到 workflow action 结果中。可用选项为： |
