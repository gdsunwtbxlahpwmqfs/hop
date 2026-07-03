# Pipeline: loops-copy-rows-to-result

## Basic Information

- **Pipeline Name:** loops-copy-rows-to-result
- **Source File:** `03-转换插件/映射与子管道类/samples/child-loops-copy-rows-to-result.hpl`

## Transforms

| Name | Type |
|------|------|
| generate 10 rows | RowGenerator |
| add counter | Sequence |
| Copy rows to result | RowsToResult |

## Hops

| From | To |
|------|----|
| generate 10 rows | add counter |
| add counter | Copy rows to result |
