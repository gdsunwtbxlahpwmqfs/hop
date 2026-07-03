# Pipeline: 0050-sorted-merge

## Basic Information

- **Pipeline Name:** 0050-sorted-merge
- **Source File:** `03-转换插件/映射与子管道类/samples/0050-sorted-merge.hpl`

## Transforms

| Name | Type |
|------|------|
| 0050-sorted-merge-template.hpl | MetaInject |
| fields | DataGrid |

## Hops

| From | To |
|------|----|
| fields | 0050-sorted-merge-template.hpl |
