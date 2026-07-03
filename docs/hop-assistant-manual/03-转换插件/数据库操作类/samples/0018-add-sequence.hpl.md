# Pipeline: 0018-add-sequence

## Basic Information

- **Pipeline Name:** 0018-add-sequence
- **Source File:** `03-转换插件/数据库操作类/samples/0018-add-sequence.hpl`

## Transforms

| Name | Type |
|------|------|
| dummy row | RowGenerator |
| Add sequence | Sequence |
| Filter rows | FilterRows |
| Success | Dummy |
| Abort | Abort |
| Detect empty stream | DetectEmptyStream |

## Hops

| From | To |
|------|----|
| dummy row | Add sequence |
| Add sequence | Filter rows |
| Filter rows | Success |
| Filter rows | Abort |
| Add sequence | Detect empty stream |
| Detect empty stream | Abort |
