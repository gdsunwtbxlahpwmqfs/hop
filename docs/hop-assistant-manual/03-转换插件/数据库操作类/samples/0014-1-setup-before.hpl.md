# Pipeline: 0014-1-setup-before

## Basic Information

- **Pipeline Name:** 0014-1-setup-before
- **Source File:** `03-转换插件/数据库操作类/samples/0014-1-setup-before.hpl`

## Transforms

| Name | Type |
|------|------|
| 100 rows | RowGenerator |
| id | Sequence |
| mod10 | Calculator |
| tr_data | TableOutput |

## Hops

| From | To |
|------|----|
| 100 rows | id |
| id | mod10 |
| mod10 | tr_data |
