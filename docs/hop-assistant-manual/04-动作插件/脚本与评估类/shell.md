# Shell

## 描述

`Shell` action 在 workflow 运行的主机上执行 shell 脚本。

例如，假设您有一个程序读取五个数据表并以指定格式创建一个文件。

### 脚本或可执行文件

Shell 允许您在 Qi Hop 中完成部分工作，同时按需重用读取数据表的程序。

Shell workflow action 与平台无关；您可以使用批处理文件、UNIX 脚本等。

当您使用 Shell workflow action 时，Hop 发出 Java 调用以在指定位置执行程序。

### 内嵌脚本

作为在主机上调用脚本的替代方案，您可以直接在 `Shell` action 中输入脚本（例如 Windows 上的批处理、Linux 上的 shell 脚本）。

### 返回状态

返回状态由操作系统调用提供。

例如，在批处理脚本中，返回值 1 表示脚本成功；返回值 0（零）表示不成功。

您可以为 Shell workflow action 传递命令行参数并设置日志记录。

## 选项

### General 选项卡

| 选项 | 描述 |
|---|---|
| Action name | Workflow action 的名称。 |
| Insert script a | 当您想在 Script 选项卡中执行脚本而不是执行 Script file name 时，请勾选此选项。 |
| Script file name | 要启动的 shell 脚本的文件名，应包含完整路径，否则使用 {openvar}user.dir{closevar} 作为路径。 |
| Working directory | 用作 shell 脚本工作目录的目录。 |
| Specify log file | 启用以为此 workflow 的执行指定单独的日志文件。 |
| Append logfile | 启用以追加到日志文件而非创建新文件 |
| Name of log file | 日志文件的目录和基础名称（例如 C:\logs） |
| Extension of the log file | 文件名扩展（例如：log 或 txt） |
| Include date in filename? | 将系统日期添加到文件名中。(_20051231) |
| Include time in filename? | 将系统时间添加到文件名中。(_235959) |
| Loglevel | 指定 shell 执行的日志级别。 |
| Copy previous results to arguments? | 可以使用 "Copy rows to result" transform 将先前 pipeline 的结果发送到 shell 脚本。（作为参数） |
| Execute once for every input row | 这实现了循环。 |
| Arguments table | 指定用作 shell 脚本参数的字符串。 |

### Script 选项卡

如果勾选了 `Insert script` 选项，此选项卡包含您的批处理或 shell 脚本。
