# Pipeline: 0012-string-cut-child

## Basic Information

- **Pipeline Name:** 0012-string-cut-child
- **Source File:** `03-转换插件/映射与子管道类/samples/0012-string-cut-child.hpl`

## Transforms

| Name | Type |
|------|------|
| Generate input string | RowGenerator |
| Strings cut | StringCut |
| Verify | Dummy |

## Hops

| From | To |
|------|----|
| Generate input string | Strings cut |
| Strings cut | Verify |
