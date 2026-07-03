# Pipeline: 0008-append

## Basic Information

- **Pipeline Name:** 0008-append
- **Source File:** `03-转换插件/samples/0008-append.hpl`

## Transforms

| Name | Type |
|------|------|
| Append streams | Append |
| Verify | Dummy |
| head | DataGrid |
| tail | DataGrid |

## Hops

| From | To |
|------|----|
| head | Append streams |
| Append streams | Verify |
| tail | Append streams |
