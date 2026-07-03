# Pipeline: 0010-database-lookup-populate

## Basic Information

- **Pipeline Name:** 0010-database-lookup-populate
- **Source File:** `03-转换插件/映射与子管道类/samples/0013-tableinput-populate.hpl`

## Transforms

| Name | Type |
|------|------|
| 100 rows | RowGenerator |
| dblu_uuid | TableOutput |
| id | Sequence |
| uuid | RandomValue |

## Hops

| From | To |
|------|----|
| 100 rows | id |
| id | uuid |
| uuid | dblu_uuid |
