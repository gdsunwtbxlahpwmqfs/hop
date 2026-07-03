# Pipeline: 0014-insertupdate-populate

## Basic Information

- **Pipeline Name:** 0014-insertupdate-populate
- **Source File:** `03-转换插件/映射与子管道类/samples/0014-insertupdate-populate.hpl`

## Transforms

| Name | Type |
|------|------|
| Insert 1 row | RowGenerator |
| dblu_uuid | TableOutput |
| id | Sequence |
| uuid | RandomValue |

## Hops

| From | To |
|------|----|
| Insert 1 row | id |
| id | uuid |
| uuid | dblu_uuid |
