# Pipeline: 0001-generate-rows

## Basic Information

- **Pipeline Name:** 0001-generate-rows
- **Source File:** `03-转换插件/Beam大数据类/samples/0001-generate-rows.hpl`

## Transforms

| Name | Type |
|------|------|
| Dummy (do nothing) | Dummy |
| generate 1000 rows | RowGenerator |
| count <> 1000 | FilterRows |
| Abort | Abort |
| Group by | GroupBy |

## Hops

| From | To |
|------|----|
| generate 1000 rows | Dummy (do nothing) |
| count <> 1000 | Abort |
| Dummy (do nothing) | Group by |
| Group by | count <> 1000 |
