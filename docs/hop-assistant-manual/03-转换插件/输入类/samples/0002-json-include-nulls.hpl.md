# Pipeline: 0002-json-include-nulls

## Basic Information

- **Pipeline Name:** 0002-json-include-nulls
- **Source File:** `03-转换插件/输入类/samples/0002-json-include-nulls.hpl`

## Transforms

| Name | Type |
|------|------|
| JSON input | JsonInput |
| JSON input Array | JsonInput |
| Write to log | WriteToLog |
| Is null present? | FilterRows |
| Add constants | Constant |
| Set variables | SetVariable |

## Hops

| From | To |
|------|----|
| JSON input | JSON input Array |
| JSON input Array | Write to log |
| Write to log | Is null present? |
| Is null present? | Add constants |
| Add constants | Set variables |
