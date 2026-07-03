# Pipeline: delete-5-customers-test

## Basic Information

- **Pipeline Name:** delete-5-customers-test
- **Source File:** `03-转换插件/批量加载类/samples/delete-5-customers-test.hpl`

## Transforms

| Name | Type |
|------|------|
| get first name, last name | JsonInput |
| read customers | MongoDbInput |
| sort | SortRows |
| test | Dummy |

## Hops

| From | To |
|------|----|
| sort | test |
| read customers | get first name, last name |
| get first name, last name | sort |
