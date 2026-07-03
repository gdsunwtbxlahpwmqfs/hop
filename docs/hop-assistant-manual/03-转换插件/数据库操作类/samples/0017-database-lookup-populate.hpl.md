# Pipeline: 0017-database-lookup-populate

## Basic Information

- **Pipeline Name:** 0017-database-lookup-populate
- **Source File:** `03-转换插件/数据库操作类/samples/0017-database-lookup-populate.hpl`

## Transforms

| Name | Type |
|------|------|
| 100k rows | RowGenerator |
| dblu_uuid | TableOutput |
| id | Sequence |
| uuid | RandomValue |

## Hops

| From | To |
|------|----|
| 100k rows | id |
| id | uuid |
| uuid | dblu_uuid |
