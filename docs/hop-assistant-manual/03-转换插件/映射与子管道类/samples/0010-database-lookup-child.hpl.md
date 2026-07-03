# Pipeline: 0010-database-lookup-child

## Basic Information

- **Pipeline Name:** 0010-database-lookup-child
- **Source File:** `03-转换插件/映射与子管道类/samples/0010-database-lookup-child.hpl`

## Transforms

| Name | Type |
|------|------|
| 100 rows | RowGenerator |
| OUTPUT | Dummy |
| id | Sequence |
| myUuid | DBLookup |

## Hops

| From | To |
|------|----|
| 100 rows | id |
| id | myUuid |
| myUuid | OUTPUT |
