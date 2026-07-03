# Pipeline: 0017-json-output-parent

## Basic Information

- **Pipeline Name:** 0017-json-output-parent
- **Source File:** `03-转换插件/映射与子管道类/samples/0017-json-output-parent.hpl`

## Transforms

| Name | Type |
|------|------|
| 1st step done! | Dummy |
| ETL metadata injection | MetaInject |
| data | DataGrid |
| files | DataGrid |

## Hops

| From | To |
|------|----|
| files | ETL metadata injection |
| data | ETL metadata injection |
| ETL metadata injection | 1st step done! |
