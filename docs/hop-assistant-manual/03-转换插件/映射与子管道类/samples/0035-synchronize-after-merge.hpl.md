# Pipeline: 0035-synchronize-after-merge

## Basic Information

- **Pipeline Name:** 0035-synchronize-after-merge
- **Source File:** `03-转换插件/映射与子管道类/samples/0035-synchronize-after-merge.hpl`

## Transforms

| Name | Type |
|------|------|
| 0035-synchronize-after-merge-template.hpl | MetaInject |
| general / advanced | DataGrid |
| keys | DataGrid |
| updates | DataGrid |

## Hops

| From | To |
|------|----|
| general / advanced | 0035-synchronize-after-merge-template.hpl |
| keys | 0035-synchronize-after-merge-template.hpl |
| updates | 0035-synchronize-after-merge-template.hpl |
