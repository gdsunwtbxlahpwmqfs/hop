# 专业技巧

## 实用变量

Hop 的许多默认行为可以通过全局变量进行自定义。查看[完整变量列表](variables.md)以查看所有变量。有几个有用且常被忽视的环境变量可以设置（在 Hop 外部）：

HOP_AUDIT_FOLDER::
将此变量设置为机器上的有效路径，以存储 Hop 的审计信息。此信息包括每个项目最后打开的文件、缩放大小等等。

HOP_CONFIG_FOLDER::
Hop 默认将配置存储在 `config` 文件夹中。设置此环境变量以指向 Hop 安装目录之外的文件夹，以便无论您使用哪个 Hop 版本或安装，都能保持您的配置、项目和环境列表等。

> **💡 提示:** 将现有 `hop/conf` 文件夹的内容复制到 `HOP_CONFIG_FOLDER` 设置的路径中，即可将配置从您的某个 Hop 安装迁移到新的中心位置。
HOP_PLUGIN_BASE_FOLDERS::
设置此变量以将 Hop 指向一个逗号分隔的文件夹列表，让 Hop 在其中查找额外的 plugin。

> **❗ 重要:** 使用此变量时也会取消设置默认的 plugin 文件夹，请确保将默认 plugin 文件夹添加到逗号分隔的列表中。这可以是相对于安装目录的路径，例如：`export HOP_PLUGIN_BASE_FOLDERS=./plugins,/additional/plugin/folder`。
`./plugins` 将指向基础安装文件夹中的 plugin。
HOP_SHARED_JDBC_FOLDERS::
这是包含 JDBC 驱动的逗号分隔文件夹列表，默认值为 lib/jdbc。如果更改此设置时仍需要默认的 JDBC 驱动，请确保包含默认文件夹路径。
HOP_WINDOWS_SHELL_ENCODING::
此变量适用于 Windows 用户。Shell 编码（您在 CMD 窗口中看到的内容）可能与您的系统编码不匹配。这可能会在日志和 Shell Action/transform 中使用特殊字符时造成混淆。您可以通过在 PowerShell 中执行以下命令来确定系统使用的代码页/编码：`[System.Text.Encoding]::Default`。然后使用 `WindowsCodePage` 处显示的值，将 `HOP_WINDOWS_SHELL_ENCODING` 参数设置为该值。

要设置 Windows 变量，请转到系统 -> 系统属性 -> 环境变量，在用户或系统范围内定义。

## 键盘快捷键和鼠标操作

CTRL-K::
在 Hop 对话框的任何表格视图中，选择一行或多行，然后使用 `CTRL-K` 删除除您选择之外的所有行。

CTRL-SHIFT-Click::
将鼠标指针悬停在 workflow 中的任何 pipeline action、pipeline 或 workflow executor transform 等上面，然后使用 `CTRL-SHIFT-Click` 在新标签页中打开该项。相同的行为也可以通过悬停在项目上并按 `Z` 键来触发。

Copy as pipeline action::
在任何 pipeline 或 workflow 中，点击画布上的任意位置，然后选择"Copy as pipeline action"或"Copy as workflow action"。现在可以将选定的 pipeline 或 workflow 粘贴（CTRL-V）为任何 workflow 中完全配置好的 workflow 或 pipeline action。

Transform 悬停 + SPACE::
在 pipeline 中，将鼠标悬停在任何 transform 上，然后按 `SPACE` 显示所选 transform 的输出字段列表。

Action 或 transform 悬停 + `Z`::
将鼠标指针悬停在 workflow 中的任何 pipeline action、pipeline 或 workflow executor transform 等上面，然后按 `Z` 键在新标签页中打开该项。相同的行为也可以通过悬停在项目上并使用 `CTRL-SHIFT-Click` 组合键来触发。

## 项目和环境

- 项目可以从父项目继承 metadata 项（如数据库连接）。使用父项目跨多个项目重用 metadata 项。
- 这更多是最佳实践而非专业技巧：仅对所有环境中有效的变量使用项目变量。在不同环境中有不同值的变量应该在环境级别创建。
