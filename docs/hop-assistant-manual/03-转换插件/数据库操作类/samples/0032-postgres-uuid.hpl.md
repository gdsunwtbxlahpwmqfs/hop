# Pipeline: 0032-postgres-uuid

## Basic Information

- **Pipeline Name:** 0032-postgres-uuid
- **Source File:** `03-转换插件/数据库操作类/samples/0032-postgres-uuid.hpl`

## Transforms

| Name | Type |
|------|------|
| Join rows (cartesian product) | JoinRows |
| String to Uuid | SelectValues |
| Table input CHAR | TableInput |
| Table input UUID | TableInput |
| Table output | TableOutput |

## Hops

| From | To |
|------|----|
| Table input CHAR | String to Uuid |
| Table input UUID | Join rows (cartesian product) |
| String to Uuid | Join rows (cartesian product) |
| Join rows (cartesian product) | Table output |
