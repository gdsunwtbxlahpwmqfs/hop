# Pipeline: delete-5-customers

## Basic Information

- **Pipeline Name:** delete-5-customers
- **Source File:** `03-转换插件/批量加载类/samples/delete-5-customers.hpl`

## Transforms

| Name | Type |
|------|------|
| 5 lines to delete | DataGrid |
| delete 5 customers | MongoDbDelete |

## Hops

| From | To |
|------|----|
| 5 lines to delete | delete 5 customers |
