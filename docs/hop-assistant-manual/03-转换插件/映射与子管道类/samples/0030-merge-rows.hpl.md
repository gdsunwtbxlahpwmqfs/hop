# Pipeline: 0030-merge-rows

## Basic Information

- **Pipeline Name:** 0030-merge-rows
- **Source File:** `03-转换插件/映射与子管道类/samples/0030-merge-rows.hpl`

## Transforms

| Name | Type |
|------|------|
| ETL metadata injection | MetaInject |
| OUTPUT | Dummy |
| keys | DataGrid |
| metadata | DataGrid |
| values | DataGrid |

## Hops

| From | To |
|------|----|
| metadata | ETL metadata injection |
| keys | ETL metadata injection |
| values | ETL metadata injection |
| ETL metadata injection | OUTPUT |
