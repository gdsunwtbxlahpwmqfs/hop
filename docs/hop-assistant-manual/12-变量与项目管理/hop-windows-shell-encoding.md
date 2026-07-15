HOP_WINDOWS_SHELL_ENCODING::
此变量适用于 Windows 用户。Shell 编码（你在 CMD 窗口中看到的内容）可能与你系统的编码不匹配。这可能导致在日志和 Shell Action/Transform 中使用特殊字符时产生混淆。
你可以通过在 PowerShell 中执行以下命令来确定你的系统使用的代码页/编码：`[System.Text.Encoding]::Default`。然后你可以使用 `WindowsCodePage` 处显示的值，将 `HOP_WINDOWS_SHELL_ENCODING` 参数设置为该值。

要设置 Windows 变量，请转到 系统 -> 系统属性 -> 环境变量，并在用户或系统范围内定义此变量。
