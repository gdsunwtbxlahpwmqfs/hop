# Pipeline: mongo-insert-uuid

## Basic Information

- **Pipeline Name:** mongo-insert-uuid
- **Source File:** `03-转换插件/批量加载类/samples/mongo-insert-uuid.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| insert | MongoDbOutput |

## Hops

| From | To |
|------|----|
| Data grid | insert |
