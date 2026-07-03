# Pipeline: 0011-data-grid-parent

## Basic Information

- **Pipeline Name:** 0011-data-grid-parent
- **Source File:** `03-转换插件/映射与子管道类/samples/0011-data-grid-parent.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| Dummy (do nothing) | Dummy |
| ETL metadata injection | MetaInject |
| metadata | DataGrid |

## Hops

| From | To |
|------|----|
| metadata | ETL metadata injection |
| Data grid | ETL metadata injection |
| ETL metadata injection | Dummy (do nothing) |
