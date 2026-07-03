# Pipeline: mongo-input-parent

## Basic Information

- **Pipeline Name:** mongo-input-parent
- **Source File:** `03-转换插件/映射与子管道类/samples/mongo-input-parent.hpl`

## Transforms

| Name | Type |
|------|------|
| Dummy (do nothing) | Dummy |
| ETL metadata injection | MetaInject |
| fields | DataGrid |
| general setting | DataGrid |

## Hops

| From | To |
|------|----|
| ETL metadata injection | Dummy (do nothing) |
| fields | ETL metadata injection |
| general setting | ETL metadata injection |
