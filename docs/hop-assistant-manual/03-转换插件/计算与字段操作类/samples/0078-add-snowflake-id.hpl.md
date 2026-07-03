# Pipeline: 0078-add-snowflake-id

## Basic Information

- **Pipeline Name:** 0078-add-snowflake-id
- **Source File:** `03-转换插件/计算与字段操作类/samples/0078-add-snowflake-id.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| Generate snowflake id | SnowflakeId |
| Write to log | WriteToLog |

## Hops

| From | To |
|------|----|
| Data grid | Generate snowflake id |
| Generate snowflake id | Write to log |
