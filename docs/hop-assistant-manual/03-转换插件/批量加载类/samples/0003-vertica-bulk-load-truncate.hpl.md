# Pipeline: 0003-vertica-bulk-load-truncate

## Basic Information

- **Pipeline Name:** 0003-vertica-bulk-load-truncate
- **Source File:** `03-转换插件/批量加载类/samples/0003-vertica-bulk-load-truncate.hpl`

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
