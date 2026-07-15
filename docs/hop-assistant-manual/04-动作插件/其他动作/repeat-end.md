# 结束循环

## 描述

`End Repeat` action 结束由 [Repeat](workflow/actions/repeat.md) action 执行的重复 workflow。

它会在父 workflow 中留下一个标志，指示其可以在下一个时机停止重复。

> **💡 提示:** End Repeat action 仅在其由 Repeat action 执行时对其父 workflow 生效。

此 action 没有选项。它作为 [Success](workflow/actions/success.md) action 工作，并清除之前可能的错误。
