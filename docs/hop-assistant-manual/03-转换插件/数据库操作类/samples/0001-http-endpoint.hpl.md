# Pipeline: 0001-http-endpoint

## Basic Information

- **Pipeline Name:** 0001-http-endpoint
- **Source File:** `03-转换插件/数据库操作类/samples/0001-http-endpoint.hpl`

## Transforms

| Name | Type |
|------|------|
| CrateDB bulk loader | CrateDBBulkLoader |
| Read 100 rows dataset | TextFileInput2 |

## Hops

| From | To |
|------|----|
| Read 100 rows dataset | CrateDB bulk loader |
