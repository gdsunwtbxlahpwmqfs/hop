# Pipeline: mongo-update-validation

## Basic Information

- **Pipeline Name:** mongo-update-validation
- **Source File:** `03-转换插件/批量加载类/samples/mongo-update-validation.hpl`

## Transforms

| Name | Type |
|------|------|
| CUSTOMERS | Dummy |
| read m_updates_1 | MongoDbInput |

## Hops

| From | To |
|------|----|
| read m_updates_1 | CUSTOMERS |
