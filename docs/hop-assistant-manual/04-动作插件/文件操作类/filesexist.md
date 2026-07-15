# 检查文件是否存在

## 描述

`Checks if files exist` action 验证指定文件是否存在。如果_所有_列出的文件都存在，action 返回 `true`（成功）；如果_任何_文件不存在，则返回 `false`（失败）。

## 选项

| 选项 | 描述 |
|---|---|
| Action 名称 | workflow action 的名称。此名称在单个 workflow 中必须唯一。 |
| 文件/文件夹 | 指定要验证的文件和文件夹完整路径，每行一个。您可以在路径中使用 `{openvar}PROJECT_HOME{closevar}` 等变量，它们将被解析。 |
