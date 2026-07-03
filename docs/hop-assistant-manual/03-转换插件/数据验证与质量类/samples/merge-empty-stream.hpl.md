# Pipeline: merge_empty_stream

## Basic Information

- **Pipeline Name:** merge_empty_stream
- **Source File:** `03-转换插件/数据验证与质量类/samples/merge-empty-stream.hpl`

## Transforms

| Name | Type |
|------|------|
| A | Dummy |
| B | Dummy |
| Data grid | DataGrid |
| Default | Dummy |
| Stream Schema Merge | StreamSchema |
| Switch / case | SwitchCase |
| new_field_A | Constant |
| new_field_B | Constant |

## Hops

| From | To |
|------|----|
| A | new_field_A |
| Switch / case | A |
| Switch / case | Default |
| new_field_A | Stream Schema Merge |
| new_field_B | Stream Schema Merge |
| Switch / case | B |
| B | new_field_B |
| Default | Stream Schema Merge |
| Data grid | Switch / case |
