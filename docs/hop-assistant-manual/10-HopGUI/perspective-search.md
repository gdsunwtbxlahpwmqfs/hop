# Search Perspective

图标：![width="24px"](../assets/images/icons/search.svg)

## 描述
Search perspective 允许你搜索 Hop 项目中的所有 metadata。
这不仅包括 workflow 和 action、pipeline 和 transform，还包括 Hop 已知的任何其他 metadata 类型。

搜索选项包括位置（Hop Gui 中的所有对象或仅当前 project），以及搜索字符串是否区分大小写（默认：不区分大小写）或搜索字符串是否为正则表达式（默认：false）。

结果表显示类型、名称、文件名、位置、匹配文本和描述。
使用 perspective 底部的 'Open' 按钮可直接跳转到所选的 metadata 对象（例如在 pipeline 中打开一个 transform）。

> **💡 提示:** [hop-search](hop-tools/hop-search.md) 工具通过命令行而非 GUI 提供与 Search Perspective 相同的功能。
