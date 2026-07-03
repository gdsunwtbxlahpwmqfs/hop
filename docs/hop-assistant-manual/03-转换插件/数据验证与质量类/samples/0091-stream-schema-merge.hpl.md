# Pipeline: 0091-stream-schema-merge

## Basic Information

- **Pipeline Name:** 0091-stream-schema-merge
- **Source File:** `03-转换插件/数据验证与质量类/samples/0091-stream-schema-merge.hpl`

## Transforms

| Name | Type |
|------|------|
| source1 | DataGrid |
| source2 | DataGrid |
| source3 | DataGrid |
| validate | Dummy |
| Stream Schema Merge | StreamSchema |

## Hops

| From | To |
|------|----|
| source1 | Stream Schema Merge |
| source2 | Stream Schema Merge |
| source3 | Stream Schema Merge |
| Stream Schema Merge | validate |
