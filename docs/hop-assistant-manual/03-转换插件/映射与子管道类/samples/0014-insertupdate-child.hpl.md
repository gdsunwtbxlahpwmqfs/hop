# Pipeline: 0014-insertupdate-child

## Basic Information

- **Pipeline Name:** 0014-insertupdate-child
- **Source File:** `03-转换插件/映射与子管道类/samples/0014-insertupdate-child.hpl`

## Transforms

| Name | Type |
|------|------|
| Dummy (do nothing) | Dummy |
| Generate rows | RowGenerator |
| Insert new record | InsertUpdate |
| Update existing record | InsertUpdate |

## Hops

| From | To |
|------|----|
| Generate rows | Update existing record |
| Update existing record | Insert new record |
| Insert new record | Dummy (do nothing) |
