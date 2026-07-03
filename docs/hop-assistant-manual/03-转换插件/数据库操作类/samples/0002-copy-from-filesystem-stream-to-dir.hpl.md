# Pipeline: 0002-copy-from-filesystem-stream-to-dir

## Basic Information

- **Pipeline Name:** 0002-copy-from-filesystem-stream-to-dir
- **Source File:** `03-转换插件/数据库操作类/samples/0002-copy-from-filesystem-stream-to-dir.hpl`

## Transforms

| Name | Type |
|------|------|
| CrateDB bulk loader | CrateDBBulkLoader |
| Read 100 rows dataset | TextFileInput2 |

## Hops

| From | To |
|------|----|
| Read 100 rows dataset | CrateDB bulk loader |
