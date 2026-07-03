# Pipeline: 0002-abort-child

## Basic Information

- **Pipeline Name:** 0002-abort-child
- **Source File:** `03-转换插件/映射与子管道类/samples/0002-abort-child.hpl`

## Transforms

| Name | Type |
|------|------|
| 10 rows | RowGenerator |
| Abort after a few rows | Abort |

## Hops

| From | To |
|------|----|
| 10 rows | Abort after a few rows |
