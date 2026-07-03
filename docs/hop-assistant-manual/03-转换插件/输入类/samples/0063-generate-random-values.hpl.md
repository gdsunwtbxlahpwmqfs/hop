# Pipeline: 0063-generate-random-values

## Basic Information

- **Pipeline Name:** 0063-generate-random-values
- **Source File:** `03-转换插件/输入类/samples/0063-generate-random-values.hpl`

## Transforms

| Name | Type |
|------|------|
| 10 rows | RowGenerator |
| Generate random value | RandomValue |
| Verify | Dummy |

## Hops

| From | To |
|------|----|
| 10 rows | Generate random value |
| Generate random value | Verify |
