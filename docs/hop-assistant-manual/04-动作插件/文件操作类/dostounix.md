# 在 Windows 和 Unix 之间转换文件

## 描述

`Convert file between Windows and Unix` action 将文件从 Windows (DOS) 格式（行尾为 CR/LF）转换为 Linux/Unix 格式（行尾为 LF），反之亦然。

当格式未知时，此 action 还具有猜测功能，可转换为另一种格式。

## 选项

### 常规选项卡

| 选项 | 描述 |
|---|---|
| Action 名称 | workflow action 的名称。 |
| 设置 a |  |
| 文件/文件夹 a |  |

指定文件夹和通配符（正则表达式）后，使用 `Add` 按钮将您的文件/文件夹选择添加到表中。使用 `Delete` 或 `Edit` 按钮可删除或编辑表中选定的行。

// |Conversion a|
// This can be:
//
// * Windows to Unix: Convert files from Windows (DOS) Format (lines end with CR/LF) to Linux/Unix Format (lines end with LF)
// * Unix to Windows: Convert files from Linux/Unix Format (lines end with LF) to Windows (DOS) Format (lines end with CR/LF)
// * Guess: When the format is unknown, it converts to the other format.
// |===

### 高级

| 选项 | 描述 |
|---|---|
| Success Condition | 如果所选条件为 true，此 action 将沿着成功路径执行。当前选项有：所有文件都处理完毕时成功、至少处理 x 个文件时成功、错误文件数少于时成功 |
| Nr Files | 指定必须满足上方所选条件的文件数量 |
| Add Filenames | 将满足所选条件的文件名添加到 workflow 输出流中。条件有：不添加文件名、添加所有文件名、仅添加已处理的文件名、仅添加错误的文件名 |
