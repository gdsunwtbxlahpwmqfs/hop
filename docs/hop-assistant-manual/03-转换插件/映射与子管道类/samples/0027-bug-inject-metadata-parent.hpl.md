# Pipeline: 0027-bug-inject-metadata-parent

## Basic Information

- **Pipeline Name:** 0027-bug-inject-metadata-parent
- **Source File:** `03-转换插件/映射与子管道类/samples/0027-bug-inject-metadata-parent.hpl`

## Transforms

| Name | Type |
|------|------|
| ETL metadata injection | MetaInject |
| read customer metadata | FileMetadataPlugin |
| static filename, format | DataGrid |
| switch metadata | DataGrid |

## Hops

| From | To |
|------|----|
| read customer metadata | ETL metadata injection |
| static filename, format | ETL metadata injection |
| switch metadata | ETL metadata injection |
