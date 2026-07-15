# 等待文件

## 描述

`Wait for file` action 等待并定期检查文件是否存在。

此 workflow action 将休眠并定期检查指定文件是否存在，之后流程将继续。

该 action 可以无限期等待文件，也可以在一定时间后超时。

> **⚠️ 警告:** 此 action 需要定义单个文件。可以使用变量，但此 action 不支持通配符，因此不能根据文件名模式等待多个文件的存在。

## 选项

| 选项 | 描述 |
|---|---|
| Action name | Workflow action 的名称。 |
| File name | 要等待的文件名和路径。 |
| Maximum timeout | 最大超时时间（秒），或 0 表示无限等待。 |
| Check cycle time | 检查文件之间的时间间隔（秒）。 |
| Success on timeout | 此选项决定当达到 "Maximum timeout" 且未找到文件时的处理方式。 |
| File size check | 当此选项开启时，workflow action 检测到指定文件后，只有在文件大小在上一个检查 "cycle time seconds" 期间未变化时才会继续。 |
| Add filename to result | 将文件名添加到此 workflow action 的结果字段中。 |
