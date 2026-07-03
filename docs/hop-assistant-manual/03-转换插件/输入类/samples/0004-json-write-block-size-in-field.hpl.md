# Pipeline: 0004-json-write-block-size-in-field

## Basic Information

- **Pipeline Name:** 0004-json-write-block-size-in-field
- **Source File:** `03-转换插件/输入类/samples/0004-json-write-block-size-in-field.hpl`

## Transforms

| Name | Type |
|------|------|
| Abort | Abort |
| Fake data | Fake |
| Generate 1000 rows | RowGenerator |
| Write with bloc size 123 | JsonOutput |
| count rows | GroupBy |
| rowcount <> 9 | FilterRows |

## Hops

| From | To |
|------|----|
| Generate 1000 rows | Fake data |
| Fake data | Write with bloc size 123 |
| Write with bloc size 123 | count rows |
| count rows | rowcount <> 9 |
| rowcount <> 9 | Abort |
