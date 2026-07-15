# Neo4j Cypher 脚本

## 描述

`Neo4j Cypher script` action 在 workflow 中执行 Cypher 语句。

与所有 action 一样，目标不是检索数据，而是执行各种语句，如创建或删除索引、调用 APOC 过程、添加或删除一些静态数据等。
此 action 的结果是 `true` 或 `false` 退出代码，用于决定选择通往 workflow 中下一个 action 的成功或失败 hop。

## 重要说明

**为了允许您在命令、字符串等中输入分号（;），我们要求您在新行上用分号分隔命令**

## 配置选项

| 选项 | 描述 |
|---|---|
| Connection name | 要执行 cypher 语句的连接名称。 |
| Script | 包含 cypher 命令的脚本，命令之间以分号（;）分隔并放在新行上。 |
| Replace variables | 如果要在执行前替换脚本中格式为 `{openvar}VARIABLE_NAME{closevar}` 的变量，请勾选此选项。 |

## 示例

```
CREATE INDEX idx_company_id on :Company(id)
;
CREATE INDEX idx_company_id on :Company(id)
;
```
