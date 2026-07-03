# Pipeline: mongo-delete-uuid

## Basic Information

- **Pipeline Name:** mongo-delete-uuid
- **Source File:** `03-转换插件/批量加载类/samples/mongo-delete-uuid.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| MongoDB Delete | MongoDbDelete |

## Hops

| From | To |
|------|----|
| Data grid | MongoDB Delete |

## Notes

Deletes 2 of the rows inserted by mongo-insert-uuid in uuid_insert.

If this fails, also check the prev transform.

---
