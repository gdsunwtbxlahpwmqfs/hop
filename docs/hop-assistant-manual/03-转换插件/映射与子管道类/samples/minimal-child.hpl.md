# Pipeline: minimal-child

## Basic Information

- **Pipeline Name:** minimal-child
- **Source File:** `03-转换插件/映射与子管道类/samples/minimal-child.hpl`

## Transforms

| Name | Type |
|------|------|
| Generate rows | RowGenerator |
| Copy rows to result | RowsToResult |

## Hops

| From | To |
|------|----|
| Generate rows | Copy rows to result |
