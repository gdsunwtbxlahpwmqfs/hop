# Pipeline: 0005-input-get-data

## Basic Information

- **Pipeline Name:** 0005-input-get-data
- **Source File:** `03-转换插件/云服务类/samples/0005-standard-sql-test.hpl`

## Transforms

| Name | Type |
|------|------|
| Beam BigQuery Input | BeamBQInput |
| Dummy (do nothing) | Dummy |

## Hops

| From | To |
|------|----|
| Beam BigQuery Input | Dummy (do nothing) |
