# Pipeline: 0015-check-table-exists

## Basic Information

- **Pipeline Name:** 0015-check-table-exists
- **Source File:** `03-转换插件/数据库操作类/samples/0015-check-table-exists.hpl`

## Transforms

| Name | Type |
|------|------|
| dummy row | RowGenerator |
| Table exists | TableExists |

## Hops

| From | To |
|------|----|
| dummy row | Table exists |
