# Pipeline: mongo-delete-parent

## Basic Information

- **Pipeline Name:** mongo-delete-parent
- **Source File:** `03-转换插件/映射与子管道类/samples/mongo-delete-parent.hpl`

## Transforms

| Name | Type |
|------|------|
| ETL metadata injection | MetaInject |
| fields | DataGrid |
| general settings | DataGrid |

## Hops

| From | To |
|------|----|
| general settings | ETL metadata injection |
| fields | ETL metadata injection |
