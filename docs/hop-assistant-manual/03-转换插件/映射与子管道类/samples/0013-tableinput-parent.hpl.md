# Pipeline: 0013-tableinput-parent

## Basic Information

- **Pipeline Name:** 0013-tableinput-parent
- **Source File:** `03-转换插件/映射与子管道类/samples/0013-tableinput-parent.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| exportsFilePath | /Users/dcampen/exports/hop |  |
| schema | dbo |  |
| sqlObject | tenant |  |

## Transforms

| Name | Type |
|------|------|
| ETL Metadata Injection | MetaInject |
| Generate Rows | RowGenerator |
| countRows <> 100 | FilterRows |
| Abort | Abort |

## Hops

| From | To |
|------|----|
| Generate Rows | ETL Metadata Injection |
| ETL Metadata Injection | countRows <> 100 |
| countRows <> 100 | Abort |
