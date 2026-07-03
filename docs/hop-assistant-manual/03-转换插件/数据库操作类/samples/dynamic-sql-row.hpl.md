# Pipeline: dynamic-sql-row

## Basic Information

- **Pipeline Name:** dynamic-sql-row
- **Source File:** `03-转换插件/数据库操作类/samples/dynamic-sql-row.hpl`

## Transforms

| Name | Type |
|------|------|
| Dynamic SQL row | DynamicSqlRow |
| log table_name, id | WriteToLog |
| build query | ScriptValueMod |
| get ID columns | TableInput |
| keep TABLE_NAME. ID_COL | SelectValues |

## Hops

| From | To |
|------|----|
| get ID columns | build query |
| build query | Dynamic SQL row |
| Dynamic SQL row | keep TABLE_NAME. ID_COL |
| keep TABLE_NAME. ID_COL | log table_name, id |

## Notes

get a list of ID columns from the all the tables in the PUBLIC schema.

build a query to list of of the available IDs per table

---
