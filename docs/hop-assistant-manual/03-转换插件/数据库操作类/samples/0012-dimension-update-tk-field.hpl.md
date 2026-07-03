# Pipeline: 0012-dimension-update-tk-field

## Basic Information

- **Pipeline Name:** 0012-dimension-update-tk-field
- **Source File:** `03-转换插件/数据库操作类/samples/0012-dimension-update-tk-field.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| Prefix | Constant |
| hkey | CheckSum |
| dimension_field | DimensionLookup |

## Hops

| From | To |
|------|----|
| Data grid | Prefix |
| Prefix | hkey |
| hkey | dimension_field |
