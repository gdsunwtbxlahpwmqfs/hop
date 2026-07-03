# Pipeline: 0032-injection-with-partial-information

## Basic Information

- **Pipeline Name:** 0032-injection-with-partial-information
- **Source File:** `03-转换插件/映射与子管道类/samples/0032-injection-with-partial-information.hpl`

## Transforms

| Name | Type |
|------|------|
| ETL metadata injection | MetaInject |
| Filter rows | FilterRows |
| OUTPUT | Dummy |
| empty stream | DataGrid |
| fields | DataGrid |
| metadata | DataGrid |

## Hops

| From | To |
|------|----|
| metadata | ETL metadata injection |
| fields | ETL metadata injection |
| ETL metadata injection | OUTPUT |
| empty stream | Filter rows |
| Filter rows | ETL metadata injection |
