# Pipeline: mongo-insert-json

## Basic Information

- **Pipeline Name:** mongo-insert-json
- **Source File:** `03-转换插件/输入类/samples/mongo-insert-json.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| insert | MongoDbOutput |

## Hops

| From | To |
|------|----|
| Data grid | insert |
