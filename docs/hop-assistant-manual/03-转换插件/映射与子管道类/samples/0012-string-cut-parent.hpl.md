# Pipeline: 0012-string-cut-parent

## Basic Information

- **Pipeline Name:** 0012-string-cut-parent
- **Source File:** `03-转换插件/映射与子管道类/samples/0012-string-cut-parent.hpl`

## Transforms

| Name | Type |
|------|------|
| Dummy (do nothing) | Dummy |
| ETL metadata injection | MetaInject |
| metadata | DataGrid |

## Hops

| From | To |
|------|----|
| metadata | ETL metadata injection |
| ETL metadata injection | Dummy (do nothing) |
