# Pipeline: 0013-verify-inserted-documents

## Basic Information

- **Pipeline Name:** 0013-verify-inserted-documents
- **Source File:** `03-转换插件/数据库操作类/samples/verify-inserted-documents.hpl`

## Transforms

| Name | Type |
|------|------|
| Abort | Abort |
| Dummy (do nothing) | Dummy |
| Filter rows | FilterRows |
| Memory group by | MemoryGroupBy |
| MongoDB input | MongoDbInput |

## Hops

| From | To |
|------|----|
| MongoDB input | Memory group by |
| Memory group by | Filter rows |
| Filter rows | Dummy (do nothing) |
| Filter rows | Abort |
