# Pipeline: mongo-insert-in-collection

## Basic Information

- **Pipeline Name:** mongo-insert-in-collection
- **Source File:** `03-转换插件/输入类/samples/mongo-insert-in-collection.hpl`

## Transforms

| Name | Type |
|------|------|
| customers-1k.txt | CSVInput |
| m_inserts_1 | MongoDbOutput |

## Hops

| From | To |
|------|----|
| customers-1k.txt | m_inserts_1 |

## Notes

Commit size set to 123 to check for dangling records

---
