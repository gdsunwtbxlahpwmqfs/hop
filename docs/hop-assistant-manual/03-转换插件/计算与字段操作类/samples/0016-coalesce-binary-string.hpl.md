# Pipeline: 0044-coalesce-binary-string

## Basic Information

- **Pipeline Name:** 0044-coalesce-binary-string
- **Source File:** `03-转换插件/计算与字段操作类/samples/0016-coalesce-binary-string.hpl`

## Transforms

| Name | Type |
|------|------|
| CSV file input | CSVInput |
| Coalesce Fields | Coalesce |
| Select values | SelectValues |
| Verify | Dummy |

## Hops

| From | To |
|------|----|
| CSV file input | Coalesce Fields |
| Coalesce Fields | Select values |
| Select values | Verify |
