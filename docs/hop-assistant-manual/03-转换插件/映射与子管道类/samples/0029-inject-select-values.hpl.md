# Pipeline: 0029-inject-select-values

## Basic Information

- **Pipeline Name:** 0029-inject-select-values
- **Source File:** `03-转换插件/映射与子管道类/samples/0029-inject-select-values.hpl`

## Transforms

| Name | Type |
|------|------|
| ETL metadata injection | MetaInject |
| metadata fields | DataGrid |
| remove fields | DataGrid |
| select fields | DataGrid |

## Hops

| From | To |
|------|----|
| select fields | ETL metadata injection |
| remove fields | ETL metadata injection |
| metadata fields | ETL metadata injection |
