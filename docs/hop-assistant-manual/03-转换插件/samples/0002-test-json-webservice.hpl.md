# Pipeline: 0002-test-json-webservice

## Basic Information

- **Pipeline Name:** 0002-test-json-webservice
- **Source File:** `03-转换插件/samples/0002-test-json-webservice.hpl`

## Transforms

| Name | Type |
|------|------|
| Abort | Abort |
| Call testService | Http |
| Check Status Code | FilterRows |
| Generate rows | RowGenerator |

## Hops

| From | To |
|------|----|
| Call testService | Check Status Code |
| Check Status Code | Abort |
| Generate rows | Call testService |
