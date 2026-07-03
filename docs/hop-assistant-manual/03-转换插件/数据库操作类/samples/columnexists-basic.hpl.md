# Pipeline: columnexists-basic

## Basic Information

- **Pipeline Name:** columnexists-basic
- **Source File:** `03-转换插件/数据库操作类/samples/columnexists-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| columns | DataGrid |
| Column exists | ColumnExists |
| Output | Dummy |

## Hops

| From | To |
|------|----|
| columns | Column exists |
| Column exists | Output |
