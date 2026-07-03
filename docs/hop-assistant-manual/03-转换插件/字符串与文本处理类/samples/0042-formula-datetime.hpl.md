# Pipeline: 0042-formula-datetime

## Basic Information

- **Pipeline Name:** 0042-formula-datetime
- **Source File:** `03-转换插件/字符串与文本处理类/samples/0042-formula-datetime.hpl`

## Transforms

| Name | Type |
|------|------|
| Calculator | Calculator |
| Invalid | Abort |
| Valid | Dummy |
| Verify | FilterRows |
| date time formulas | Formula |
| generate 1 row | RowGenerator |
| get now | SystemInfo |

## Hops

| From | To |
|------|----|
| generate 1 row | get now |
| date time formulas | Verify |
| get now | Calculator |
| Calculator | date time formulas |
| Verify | Valid |
| Verify | Invalid |
