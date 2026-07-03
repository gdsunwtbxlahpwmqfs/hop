# Pipeline: read-and-inject-metadata

## Basic Information

- **Pipeline Name:** read-and-inject-metadata
- **Source File:** `03-转换插件/映射与子管道类/samples/read-and-inject-metadata.hpl`

## Transforms

| Name | Type |
|------|------|
| ETL metadata injection | MetaInject |
| read customer metadata | FileMetadataPlugin |
| rename fields | DataGrid |
| static filename, format | DataGrid |
| zips per state | DataGrid |

## Hops

| From | To |
|------|----|
| read customer metadata | ETL metadata injection |
| static filename, format | ETL metadata injection |
| rename fields | ETL metadata injection |
| zips per state | ETL metadata injection |
