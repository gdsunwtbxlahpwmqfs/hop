# Pipeline: formula-datetime

## Basic Information

- **Pipeline Name:** formula-datetime
- **Source File:** `03-转换插件/字符串与文本处理类/samples/formula-datetime.hpl`

## Transforms

| Name | Type |
|------|------|
| date time formulas | Formula |
| generate 1 row | RowGenerator |
| get now | SystemInfo |

## Hops

| From | To |
|------|----|
| generate 1 row | get now |
| get now | date time formulas |
