# Pipeline: 0016-excelwriter-verify

## Basic Information

- **Pipeline Name:** 0016-excelwriter-verify
- **Source File:** `03-转换插件/映射与子管道类/samples/0016-excelwriter-verify.hpl`

## Transforms

| Name | Type |
|------|------|
| Filter rows | FilterRows |
| KO | Abort |
| Microsoft Excel input | ExcelInput |
| OK | Dummy |

## Hops

| From | To |
|------|----|
| Filter rows | KO |
| Filter rows | OK |
| Microsoft Excel input | Filter rows |
