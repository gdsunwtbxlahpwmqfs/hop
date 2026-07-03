# Pipeline: 0009-getConnectionValues

## Basic Information

- **Pipeline Name:** 0009-getConnectionValues
- **Source File:** `03-转换插件/计算与字段操作类/samples/0009-getConnectionValues.hpl`

## Transforms

| Name | Type |
|------|------|
| Copy rows to result | RowsToResult |
| Get variables | GetVariable |
| If Null | IfNull |
| Text file input | TextFileInput2 |

## Hops

| From | To |
|------|----|
| Text file input | If Null |
| If Null | Get variables |
| Get variables | Copy rows to result |

## Notes

semi-colon delimited file specs: (no header)

field list:

tenantname

type (see types list below)

url

dbName (for s3, use s3 bucket path)

port

username	 (for s3 use ACCESS_KEY)

pw (for s3 use SECRET_ACCESS_KEY)

environment

types:

s3 (for amazon s3)

com (for direct db connection)

---
