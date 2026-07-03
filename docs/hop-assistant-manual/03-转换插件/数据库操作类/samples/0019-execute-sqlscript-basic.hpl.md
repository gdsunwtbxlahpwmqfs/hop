# Pipeline: 0019-execute-sqlscript-basic

## Basic Information

- **Pipeline Name:** 0019-execute-sqlscript-basic
- **Source File:** `03-转换插件/数据库操作类/samples/0019-execute-sqlscript-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Execute SQL script | ExecSql |
| failed on count | Abort |
| success | Dummy |
| validate count | FilterRows |

## Hops

| From | To |
|------|----|
| validate count | success |
| validate count | failed on count |
| Execute SQL script | validate count |
