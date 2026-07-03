# Pipeline: 0003-add-sequence-child

## Basic Information

- **Pipeline Name:** 0003-add-sequence-child
- **Source File:** `03-转换插件/映射与子管道类/samples/0003-add-sequence-child.hpl`

## Transforms

| Name | Type |
|------|------|
| 10 rows | RowGenerator |
| sequence | Sequence |
| minId, maxId | GroupBy |
| minId<>2 or maxId<>7 | FilterRows |
| Abort | Abort |

## Hops

| From | To |
|------|----|
| 10 rows | sequence |
| sequence | minId, maxId |
| minId, maxId | minId<>2 or maxId<>7 |
| minId<>2 or maxId<>7 | Abort |
