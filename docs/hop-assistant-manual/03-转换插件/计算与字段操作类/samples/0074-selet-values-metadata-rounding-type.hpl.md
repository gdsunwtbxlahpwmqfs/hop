# Pipeline: 0074-selet-values-metadata-rounding-type

## Basic Information

- **Pipeline Name:** 0074-selet-values-metadata-rounding-type
- **Source File:** `03-转换插件/计算与字段操作类/samples/0074-selet-values-metadata-rounding-type.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| mask | SelectValues |
| validate | Dummy |

## Hops

| From | To |
|------|----|
| Data grid | mask |
| mask | validate |
