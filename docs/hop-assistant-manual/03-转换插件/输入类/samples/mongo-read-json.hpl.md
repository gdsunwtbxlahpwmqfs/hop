# Pipeline: mongo-read-json

## Basic Information

- **Pipeline Name:** mongo-read-json
- **Source File:** `03-转换插件/输入类/samples/mongo-read-json.hpl`

## Transforms

| Name | Type |
|------|------|
| Abort | Abort |
| Data validator | Validator |
| Is 11500? | FilterRows |
| MongoDB input | MongoDbInput |

## Hops

| From | To |
|------|----|
| Is 11500? | Abort |
| MongoDB input | Data validator |
| MongoDB input | Is 11500? |

## Notes

If mongo-insert-json and mongo-delete-json successfully executed, json_insert will have 1 row.

---
