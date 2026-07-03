# Pipeline: mongo-input-template

## Basic Information

- **Pipeline Name:** mongo-input-template
- **Source File:** `03-转换插件/映射与子管道类/samples/mongo-input-template.hpl`

## Transforms

| Name | Type |
|------|------|
| CUSTOMERS | Dummy |
| MongoDB input 2 | MongoDbInput |

## Hops

| From | To |
|------|----|
| MongoDB input 2 | CUSTOMERS |
