# Pipeline: mongo-delete-template

## Basic Information

- **Pipeline Name:** mongo-delete-template
- **Source File:** `03-转换插件/映射与子管道类/samples/mongo-delete-template.hpl`

## Transforms

| Name | Type |
|------|------|
| 5 lines to delete | DataGrid |
| delete 5 customers | MongoDbDelete |

## Hops

| From | To |
|------|----|
| 5 lines to delete | delete 5 customers |
