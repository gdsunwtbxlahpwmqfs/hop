# Pipeline: 0013-tableinput-child

## Basic Information

- **Pipeline Name:** 0013-tableinput-child
- **Source File:** `03-转换插件/映射与子管道类/samples/0013-tableinput-child.hpl`

## Transforms

| Name | Type |
|------|------|
| Group by | GroupBy |
| SQL | TableInput |
| Dummy (do nothing) | Dummy |

## Hops

| From | To |
|------|----|
| SQL | Group by |
| Group by | Dummy (do nothing) |
