# Pipeline: 0028-join-rows-metadata-parent

## Basic Information

- **Pipeline Name:** 0028-join-rows-metadata-parent
- **Source File:** `03-转换插件/映射与子管道类/samples/0028-join-rows-metadata-parent.hpl`

## Transforms

| Name | Type |
|------|------|
| Condition | DataGrid |
| Dummy (do nothing) | Dummy |
| ETL metadata injection | MetaInject |

## Hops

| From | To |
|------|----|
| Condition | ETL metadata injection |
| ETL metadata injection | Dummy (do nothing) |
