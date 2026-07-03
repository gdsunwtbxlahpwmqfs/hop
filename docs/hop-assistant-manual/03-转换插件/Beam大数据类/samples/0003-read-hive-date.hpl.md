# Pipeline: 0003-read-hive-date

## Basic Information

- **Pipeline Name:** 0003-read-hive-date
- **Source File:** `03-转换插件/Beam大数据类/samples/0003-read-hive-date.hpl`

## Transforms

| Name | Type |
|------|------|
| 10 rows? | FilterRows |
| Abort | Abort |
| ok | Dummy |
| read books | TableInput |
| row count | MemoryGroupBy |

## Hops

| From | To |
|------|----|
| 10 rows? | ok |
| 10 rows? | Abort |
| read books | row count |
| row count | 10 rows? |
