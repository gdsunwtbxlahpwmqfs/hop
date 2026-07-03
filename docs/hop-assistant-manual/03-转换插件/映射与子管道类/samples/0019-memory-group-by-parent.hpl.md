# Pipeline: 0019-memory-group-by-parent

## Basic Information

- **Pipeline Name:** 0019-memory-group-by-parent
- **Source File:** `03-转换插件/映射与子管道类/samples/0019-memory-group-by-parent.hpl`

## Transforms

| Name | Type |
|------|------|
| ETL metadata injection | MetaInject |
| Output | Dummy |
| X | JoinRows |
| a | DataGrid |
| aggregates | DataGrid |
| b | DataGrid |
| c | DataGrid |
| groups | DataGrid |

## Hops

| From | To |
|------|----|
| a | X |
| b | X |
| c | X |
| X | ETL metadata injection |
| groups | ETL metadata injection |
| aggregates | ETL metadata injection |
| ETL metadata injection | Output |
