# Pipeline: 0010-database-lookup-parent

## Basic Information

- **Pipeline Name:** 0010-database-lookup-parent
- **Source File:** `03-转换插件/映射与子管道类/samples/0010-database-lookup-parent.hpl`

## Transforms

| Name | Type |
|------|------|
| Abort | Abort |
| ETL metadata injection | MetaInject |
| count | GroupBy |
| count <> 100 | FilterRows |
| keys | DataGrid |
| metadata | DataGrid |
| myUuid <> '-' | FilterRows |
| returns | DataGrid |

## Hops

| From | To |
|------|----|
| metadata | ETL metadata injection |
| keys | ETL metadata injection |
| returns | ETL metadata injection |
| ETL metadata injection | myUuid <> '-' |
| myUuid <> '-' | count |
| count | count <> 100 |
| count <> 100 | Abort |
