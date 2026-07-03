# Pipeline: 0008-analytic-query-parent

## Basic Information

- **Pipeline Name:** 0008-analytic-query-parent
- **Source File:** `03-转换插件/映射与子管道类/samples/0008-analytic-query-parent.hpl`

## Transforms

| Name | Type |
|------|------|
| ETL metadata injection | MetaInject |
| Verify | Dummy |
| group metadata | DataGrid |
| query metadata | DataGrid |

## Hops

| From | To |
|------|----|
| query metadata | ETL metadata injection |
| group metadata | ETL metadata injection |
| ETL metadata injection | Verify |
