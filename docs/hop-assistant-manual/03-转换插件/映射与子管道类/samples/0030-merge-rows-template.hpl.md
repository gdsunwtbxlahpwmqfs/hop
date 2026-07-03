# Pipeline: 0030-merge-rows-template

## Basic Information

- **Pipeline Name:** 0030-merge-rows-template
- **Source File:** `03-转换插件/映射与子管道类/samples/0030-merge-rows-template.hpl`

## Transforms

| Name | Type |
|------|------|
| Merge rows | MergeRows |
| source1 | DataGrid |
| source2 | DataGrid |
| OUTPUT | Dummy |

## Hops

| From | To |
|------|----|
| source1 | Merge rows |
| source2 | Merge rows |
| Merge rows | OUTPUT |
