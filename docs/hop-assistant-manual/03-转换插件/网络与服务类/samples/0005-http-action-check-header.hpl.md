# Pipeline: 0005-http-action-check-header

## Basic Information

- **Pipeline Name:** 0005-http-action-check-header
- **Source File:** `03-转换插件/网络与服务类/samples/0005-http-action-check-header.hpl`

## Transforms

| Name | Type |
|------|------|
| Abort | Abort |
| Filter rows | FilterRows |
| Generate rows | RowGenerator |
| Get variables | GetVariable |
| JSON input | JsonInput |

## Hops

| From | To |
|------|----|
| Filter rows | Abort |
| Generate rows | Get variables |
| Get variables | JSON input |
| JSON input | Filter rows |
