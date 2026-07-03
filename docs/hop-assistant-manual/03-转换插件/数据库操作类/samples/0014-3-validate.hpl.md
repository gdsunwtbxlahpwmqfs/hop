# Pipeline: 0014-3-validate

## Basic Information

- **Pipeline Name:** 0014-3-validate
- **Source File:** `03-转换插件/数据库操作类/samples/0014-3-validate.hpl`

## Transforms

| Name | Type |
|------|------|
| tr_data | TableInput |
| avgMod | GroupBy |
| avgMod<>4 | FilterRows |
| Abort | Abort |

## Hops

| From | To |
|------|----|
| tr_data | avgMod |
| avgMod | avgMod<>4 |
| avgMod<>4 | Abort |
