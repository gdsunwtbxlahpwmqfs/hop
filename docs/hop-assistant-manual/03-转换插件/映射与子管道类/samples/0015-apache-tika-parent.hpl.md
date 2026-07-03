# Pipeline: 0015-apache-tika-parent

## Basic Information

- **Pipeline Name:** 0015-apache-tika-parent
- **Source File:** `03-转换插件/映射与子管道类/samples/0015-apache-tika-parent.hpl`

## Transforms

| Name | Type |
|------|------|
| ETL metadata injection | MetaInject |
| files | DataGrid |
| metadata | DataGrid |
| validate | Dummy |

## Hops

| From | To |
|------|----|
| files | ETL metadata injection |
| metadata | ETL metadata injection |
| ETL metadata injection | validate |
