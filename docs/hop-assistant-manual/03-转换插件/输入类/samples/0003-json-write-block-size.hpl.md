# Pipeline: 0003-json-write-block-size

## Basic Information

- **Pipeline Name:** 0003-json-write-block-size
- **Source File:** `03-转换插件/输入类/samples/0003-json-write-block-size.hpl`

## Transforms

| Name | Type |
|------|------|
| Fake data | Fake |
| Generate 1000 rows | RowGenerator |
| Write with bloc size 123 | JsonOutput |

## Hops

| From | To |
|------|----|
| Generate 1000 rows | Fake data |
| Fake data | Write with bloc size 123 |
