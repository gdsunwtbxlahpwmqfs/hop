# Pipeline: mongo-update-collection

## Basic Information

- **Pipeline Name:** mongo-update-collection
- **Source File:** `03-转换插件/批量加载类/samples/mongo-update-collection.hpl`

## Transforms

| Name | Type |
|------|------|
| 1000 rows | RowGenerator |
| id | Sequence |
| m_updates_1 | MongoDbOutput |
| otherId | Janino |

## Hops

| From | To |
|------|----|
| 1000 rows | id |
| id | otherId |
| otherId | m_updates_1 |

## Notes

Commit size set to 1000 to see that a commit is

done at the end (borderline case)

---
