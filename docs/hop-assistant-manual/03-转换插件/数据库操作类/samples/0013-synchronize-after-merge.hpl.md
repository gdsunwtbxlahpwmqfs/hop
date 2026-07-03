# Pipeline: 0013-synchronize-after-merge

## Basic Information

- **Pipeline Name:** 0013-synchronize-after-merge
- **Source File:** `03-转换插件/数据库操作类/samples/0013-synchronize-after-merge.hpl`

## Transforms

| Name | Type |
|------|------|
| Merge rows (diff) | MergeRows |
| Synchronize after merge | SynchronizeAfterMerge |
| new | DataGrid |
| source | DataGrid |

## Hops

| From | To |
|------|----|
| source | Merge rows (diff) |
| new | Merge rows (diff) |
| Merge rows (diff) | Synchronize after merge |
