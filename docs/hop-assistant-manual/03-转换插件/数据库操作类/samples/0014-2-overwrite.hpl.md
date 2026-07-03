# Pipeline: 0014-2-overwrite

## Basic Information

- **Pipeline Name:** 0014-2-overwrite
- **Source File:** `03-转换插件/数据库操作类/samples/0014-2-overwrite.hpl`

## Transforms

| Name | Type |
|------|------|
| 100 rows | RowGenerator |
| id | Sequence |
| tr_data | TableOutput |
| random mod10 | RandomValue |

## Hops

| From | To |
|------|----|
| 100 rows | id |
| id | random mod10 |
| random mod10 | tr_data |
