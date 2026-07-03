# Pipeline: 0043-checksum

## Basic Information

- **Pipeline Name:** 0043-checksum
- **Source File:** `03-转换插件/计算与字段操作类/samples/0043-checksum.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| Replace in string | ReplaceString |
| Add a checksum | CheckSum |
| verify | Dummy |

## Hops

| From | To |
|------|----|
| Data grid | Replace in string |
| Replace in string | Add a checksum |
| Add a checksum | verify |
