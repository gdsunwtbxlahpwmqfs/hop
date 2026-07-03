# Pipeline: mongo-read-uuid

## Basic Information

- **Pipeline Name:** mongo-read-uuid
- **Source File:** `03-转换插件/批量加载类/samples/mongo-read-uuid.hpl`

## Transforms

| Name | Type |
|------|------|
| Abort | Abort |
| Data validator | Validator |
| Filter rows | FilterRows |
| Group by | GroupBy |
| MongoDB input | MongoDbInput |

## Hops

| From | To |
|------|----|
| MongoDB input | Data validator |
| MongoDB input | Group by |
| Group by | Filter rows |
| Filter rows | Abort |

## Notes

If mongo-insert-uuid and mongo-delete-uuid successfully executed, uuid_insert will have 2 rows.

If this fails, also check the prev transforms.

---
