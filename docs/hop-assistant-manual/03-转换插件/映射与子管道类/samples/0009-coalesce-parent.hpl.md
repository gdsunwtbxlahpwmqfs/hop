# Pipeline: 0009-coalesce-parent

## Basic Information

- **Pipeline Name:** 0009-coalesce-parent
- **Source File:** `03-转换插件/映射与子管道类/samples/0009-coalesce-parent.hpl`

## Transforms

| Name | Type |
|------|------|
| ETL metadata injection | MetaInject |
| Verify | Dummy |
| transform metadata | DataGrid |
| fields metadata | DataGrid |

## Hops

| From | To |
|------|----|
| fields metadata | ETL metadata injection |
| transform metadata | ETL metadata injection |
| ETL metadata injection | Verify |
