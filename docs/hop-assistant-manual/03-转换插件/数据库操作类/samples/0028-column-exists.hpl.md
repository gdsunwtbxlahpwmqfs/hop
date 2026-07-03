# Pipeline: 0028-column-exists

## Basic Information

- **Pipeline Name:** 0028-column-exists
- **Source File:** `03-转换插件/数据库操作类/samples/0028-column-exists.hpl`

## Transforms

| Name | Type |
|------|------|
| Column exists | ColumnExists |
| Data grid | DataGrid |
| end result | Dummy |

## Hops

| From | To |
|------|----|
| Data grid | Column exists |
| Column exists | end result |
