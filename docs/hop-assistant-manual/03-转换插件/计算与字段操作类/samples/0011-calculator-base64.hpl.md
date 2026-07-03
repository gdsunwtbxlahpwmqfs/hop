# Pipeline: 0011-calculator-base64

## Basic Information

- **Pipeline Name:** 0011-calculator-base64
- **Source File:** `03-转换插件/计算与字段操作类/samples/0011-calculator-base64.hpl`

## Transforms

| Name | Type |
|------|------|
| clean | SelectValues |
| decode | Calculator |
| encode | Calculator |
| generate 1 row | RowGenerator |
| verify | Dummy |

## Hops

| From | To |
|------|----|
| clean | verify |
| encode | decode |
| decode | clean |
| generate 1 row | encode |
