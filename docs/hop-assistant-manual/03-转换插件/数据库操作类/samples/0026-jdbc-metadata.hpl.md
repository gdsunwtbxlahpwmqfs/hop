# Pipeline: 0026-jdbc-metadata

## Basic Information

- **Pipeline Name:** 0026-jdbc-metadata
- **Source File:** `03-转换插件/数据库操作类/samples/0026-jdbc-metadata.hpl`

## Transforms

| Name | Type |
|------|------|
| Get JDBC Metadata | JdbcMetadata |
| count rows | GroupBy |
| >= 1 | FilterRows |
| Abort | Abort |

## Hops

| From | To |
|------|----|
| Get JDBC Metadata | count rows |
| count rows | >= 1 |
| >= 1 | Abort |
