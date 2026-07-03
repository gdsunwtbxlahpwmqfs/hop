# Pipeline: 0031-get-data-from-xml

## Basic Information

- **Pipeline Name:** 0031-get-data-from-xml
- **Source File:** `03-转换插件/映射与子管道类/samples/0031-get-data-from-xml.hpl`

## Transforms

| Name | Type |
|------|------|
| ETL metadata injection | MetaInject |
| OUTPUT | Dummy |
| fields | DataGrid |
| metadata | DataGrid |

## Hops

| From | To |
|------|----|
| metadata | ETL metadata injection |
| fields | ETL metadata injection |
| ETL metadata injection | OUTPUT |
