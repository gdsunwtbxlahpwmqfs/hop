# Pipeline: 0014-insertupdate-parent

## Basic Information

- **Pipeline Name:** 0014-insertupdate-parent
- **Source File:** `03-转换插件/映射与子管道类/samples/0014-insertupdate-parent.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| exportsFilePath | /Users/dcampen/exports/hop |  |
| schema | dbo |  |
| sqlObject | tenant |  |

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| ETL Metadata Injection | MetaInject |
| Generate Rows | RowGenerator |
| Dummy (do nothing) | Dummy |

## Hops

| From | To |
|------|----|
| Generate Rows | ETL Metadata Injection |
| Data grid | ETL Metadata Injection |
| ETL Metadata Injection | Dummy (do nothing) |
