# Pipeline: 0002-vertica-bulk-load

## Basic Information

- **Pipeline Name:** 0002-vertica-bulk-load
- **Source File:** `03-转换插件/批量加载类/samples/0002-vertica-bulk-load.hpl`

## Transforms

| Name | Type |
|------|------|
| Vertica bulk loader | VerticaBulkLoader |
| generate 1M rows | RowGenerator |
| generate book data | Fake |
| string length 150 | SelectValues |

## Hops

| From | To |
|------|----|
| generate 1M rows | generate book data |
| generate book data | string length 150 |
| string length 150 | Vertica bulk loader |
