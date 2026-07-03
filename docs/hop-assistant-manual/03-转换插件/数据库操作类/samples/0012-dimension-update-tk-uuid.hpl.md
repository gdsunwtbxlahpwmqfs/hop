# Pipeline: 0012-dimension-update-tk-uuid

## Basic Information

- **Pipeline Name:** 0012-dimension-update-tk-uuid
- **Source File:** `03-转换插件/数据库操作类/samples/0012-dimension-update-tk-uuid.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| dimension_uuid | DimensionLookup |

## Hops

| From | To |
|------|----|
| Data grid | dimension_uuid |
