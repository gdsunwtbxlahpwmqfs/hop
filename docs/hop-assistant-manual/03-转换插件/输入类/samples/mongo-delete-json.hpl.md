# Pipeline: mongo-delete-json

## Basic Information

- **Pipeline Name:** mongo-delete-json
- **Source File:** `03-转换插件/输入类/samples/mongo-delete-json.hpl`

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

Deletes 2 of the rows inserted by mongo-insert-json in json_insert.

---
