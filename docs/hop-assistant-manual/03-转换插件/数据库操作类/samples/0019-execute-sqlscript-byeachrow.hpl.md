# Pipeline: 0019-execute-sqlscript-byeachrow

## Basic Information

- **Pipeline Name:** 0019-execute-sqlscript-byeachrow
- **Source File:** `03-转换插件/数据库操作类/samples/0019-execute-sqlscript-byeachrow.hpl`

## Transforms

| Name | Type |
|------|------|
| Execute SQL script | ExecSql |
| Generate rows | RowGenerator |
| failed on count | Abort |
| success | Dummy |
| validate count | FilterRows |

## Hops

| From | To |
|------|----|
| validate count | success |
| validate count | failed on count |
| Generate rows | Execute SQL script |
| Execute SQL script | validate count |
