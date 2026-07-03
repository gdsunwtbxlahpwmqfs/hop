# Pipeline: 0003-http-endpoint-double-precision

## Basic Information

- **Pipeline Name:** 0003-http-endpoint-double-precision
- **Source File:** `03-转换插件/数据库操作类/samples/0003-http-endpoint-double-precision.hpl`

## Transforms

| Name | Type |
|------|------|
| CrateDB bulk loader | CrateDBBulkLoader |
| Dummy (do nothing) | Dummy |
| Read housing dataset | TextFileInput2 |

## Hops

| From | To |
|------|----|
| Read housing dataset | CrateDB bulk loader |
| CrateDB bulk loader | Dummy (do nothing) |
