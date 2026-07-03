# Pipeline: concat-fields-basic

## Basic Information

- **Pipeline Name:** concat-fields-basic
- **Source File:** `03-转换插件/字符串与文本处理类/samples/concat-fields-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| dummy data grid | DataGrid |
| Concat Fields | ConcatFields |
| Concat Fields Removed Source Fields | ConcatFields |

## Hops

| From | To |
|------|----|
| dummy data grid | Concat Fields |
| dummy data grid | Concat Fields Removed Source Fields |
