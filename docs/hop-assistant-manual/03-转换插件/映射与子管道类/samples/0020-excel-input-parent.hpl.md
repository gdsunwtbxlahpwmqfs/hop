# Pipeline: 0020-excel-input-parent

## Basic Information

- **Pipeline Name:** 0020-excel-input-parent
- **Source File:** `03-转换插件/映射与子管道类/samples/0020-excel-input-parent.hpl`

## Transforms

| Name | Type |
|------|------|
| ETL metadata injection | MetaInject |
| Fields | DataGrid |
| Sheets | DataGrid |
| Type | DataGrid |
| Verify | Dummy |
| filenames | DataGrid |

## Hops

| From | To |
|------|----|
| filenames | ETL metadata injection |
| ETL metadata injection | Verify |
| Fields | ETL metadata injection |
| Sheets | ETL metadata injection |
| Type | ETL metadata injection |
