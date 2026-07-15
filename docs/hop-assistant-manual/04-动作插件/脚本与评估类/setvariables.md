# 设置变量

## 描述

`Set variables` action 在不同范围内设置变量。

## 选项

| 选项 | 描述 |
|---|---|
| Workflow action name | Workflow action 的名称。 |
| Name of properties file | 属性文件的名称。 |
| Variable scope a |  |
| Variable substitution | 是否替换变量。仅当您确实想在 Value 列中设置如 `{openvar}MyVariable{closevar}` 这样的字面值时才不勾选。 |
| Variables | 要在特定范围内设置为特定值的变量列表。 |

## 使用属性文件

属性文件是用于存储键值对的简单文本文件。它们通常用于管理代码库之外的配置设置和变量。在 Qi Hop 中，属性文件可用于在 workflow 中一次性设置多个变量。

属性文件是具有 `.properties` 扩展名的文本文件。文件中的每一行代表一个键值对。键和值可以用等号（`=`）、冒号（`:`）或空白字符分隔。

## 变量替换

"Variable substitution" 选项允许您根据现有变量值动态创建和设置变量。启用后，变量名或值字段中的任何变量引用将在设置变量之前被解析。这使得创建名称和值依赖于其他变量内容的新变量成为可能，从而在 workflow 中提供了一种灵活且动态的变量管理方法。使用语法 `{openvar}MyVariable{closevar}` 来获取变量值。
