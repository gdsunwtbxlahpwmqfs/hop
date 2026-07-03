# Pipeline: 0016-excelwriter-parent

## Basic Information

- **Pipeline Name:** 0016-excelwriter-parent
- **Source File:** `03-转换插件/映射与子管道类/samples/0016-excelwriter-parent.hpl`

## Transforms

| Name | Type |
|------|------|
| ETL metadata injection | MetaInject |
| 1st step done! | Dummy |
| files | DataGrid |
| metadata | DataGrid |

## Hops

| From | To |
|------|----|
| files | ETL metadata injection |
| metadata | ETL metadata injection |
| ETL metadata injection | 1st step done! |
