# Pipeline: 0076-concat-fields-with-enclosure

## Basic Information

- **Pipeline Name:** 0076-concat-fields-with-enclosure
- **Source File:** `03-转换插件/计算与字段操作类/samples/0076-concat-fields-with-enclosure.hpl`

## Transforms

| Name | Type |
|------|------|
| Concat Fields | ConcatFields |
| dummy data | DataGrid |
| preview | Dummy |

## Hops

| From | To |
|------|----|
| Concat Fields | preview |
| dummy data | Concat Fields |
