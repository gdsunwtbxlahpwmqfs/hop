# Pipeline: mongo-insert-parent

## Basic Information

- **Pipeline Name:** mongo-insert-parent
- **Source File:** `03-转换插件/映射与子管道类/samples/mongo-insert-parent.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| ETL metadata injection | MetaInject |
| fields | DataGrid |

## Hops

| From | To |
|------|----|
| Data grid | ETL metadata injection |
| fields | ETL metadata injection |
