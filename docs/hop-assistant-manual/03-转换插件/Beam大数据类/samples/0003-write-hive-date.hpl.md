# Pipeline: 0003-write-hive-date

## Basic Information

- **Pipeline Name:** 0003-write-hive-date
- **Source File:** `03-转换插件/Beam大数据类/samples/0003-write-hive-date.hpl`

## Transforms

| Name | Type |
|------|------|
| fake book data | Fake |
| generate 10 rows | RowGenerator |
| write books | TableOutput |

## Hops

| From | To |
|------|----|
| generate 10 rows | fake book data |
| fake book data | write books |
