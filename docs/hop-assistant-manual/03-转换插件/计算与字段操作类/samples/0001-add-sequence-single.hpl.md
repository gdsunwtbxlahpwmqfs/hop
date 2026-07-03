# Pipeline: 00100-add-sequence-single

## Basic Information

- **Pipeline Name:** 00100-add-sequence-single
- **Source File:** `03-转换插件/计算与字段操作类/samples/0001-add-sequence-single.hpl`

## Transforms

| Name | Type |
|------|------|
| 10 rows | RowGenerator |
| IDS | SetVariable |
| concat | GroupBy |
| id | Sequence |
| not expected? | FilterRows |
| Abort | Abort |

## Hops

| From | To |
|------|----|
| 10 rows | id |
| id | concat |
| concat | IDS |
| not expected? | Abort |
| IDS | not expected? |
