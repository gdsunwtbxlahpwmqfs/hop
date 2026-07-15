# 复制文件

## 描述

Copy Files action 复制一个或多个文件或文件夹。

## Files 选项卡

| 选项 | 描述 |
|---|---|
| Action 名称 | 表示 action 的名称。 |
| 源环境 a | 表示可以找到要输入项的文件系统或特定集群。 |
| 源文件/文件夹* * | 要从中复制的文件或目录。 |
| 通配符 | 以正则表达式方式定义要复制的文件（而非静态文件名），例如：.*\.txt 表示任何扩展名为 .txt 的文件。 |
| 目标环境 a | 表示您希望放置文件的文件系统或特定集群。 |
| 目标文件 | 表示目标环境的名称。 |

## 设置

| 选项 | 描述 | 默认值 |
|---|---|---|
| 包含子文件夹 | 如果选中，所选目录内的所有子目录也将被复制 | 未选中 |
| 目标是文件 | 确定目标是文件还是目录 | 未选中 |
| 复制空文件夹 | 如果选中，将复制所有目录，即使它们为空也必须选中 Include Subfolders 选项才能使此选项生效。 |  |
| 创建目标文件夹 | 如果选中，将在指定目标目录当前不存在时创建它 | 未选中 |
| 替换现有文件 | 如果选中，目标目录中的重复文件将被覆盖 | 未选中 |
| 删除源文件 | 如果选中，在复制后删除源文件（即移动操作） | 未选中 |
| 将上一步结果复制到参数 | 将上一步结果复制到参数。 | 未选中 |
| 将文件添加到结果文件名 | 所有复制的文件将作为此 action 的结果显示；显示此 action 中复制的文件列表 | 未选中 |

// == Open File
//
// |===
// |Option|Description
// |Open from Folder|Indicates the path and name of the directory you want to browse.
// This directory becomes the active directory.
// |Up One Level|Displays the parent directory of the active directory shown in the Open from Folder field.
// |Delete|Deletes a folder from the active directory.
// |Create Folder|Creates a new folder in the active directory.
// |Name|Displays the active directory, which is the one that is listed in the Open from Folder field.
// |Filter|Applies a filter to the results displayed in the active directory contents.
// |===
