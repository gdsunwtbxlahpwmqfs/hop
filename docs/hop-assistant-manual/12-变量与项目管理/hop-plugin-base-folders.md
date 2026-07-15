HOP_PLUGIN_BASE_FOLDERS::
将此变量设置为以逗号分隔的文件夹列表，让 Hop 在其中查找额外的 plugin。

> **❗ 重要:** 使用此变量时，它还会取消设置你的默认 plugin 文件夹，请确保将默认 plugin 文件夹添加到逗号分隔的列表中。这可以是相对于安装目录的路径，例如 `export HOP_PLUGIN_BASE_FOLDERS=./plugins,/additional/plugin/folder`。
`./plugins` 将指向基础安装文件夹中的 plugin
