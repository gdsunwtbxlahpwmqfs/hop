# Pipeline: mongo-insert-template

## Basic Information

- **Pipeline Name:** mongo-insert-template
- **Source File:** `03-转换插件/映射与子管道类/samples/mongo-insert-template.hpl`

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
