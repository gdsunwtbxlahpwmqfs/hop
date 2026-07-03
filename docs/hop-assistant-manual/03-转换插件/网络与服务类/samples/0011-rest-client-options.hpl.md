# Pipeline: 0011-rest-client-option

## Basic Information

- **Pipeline Name:** 0011-rest-client-option
- **Source File:** `03-转换插件/网络与服务类/samples/0011-rest-client-options.hpl`

## Transforms

| Name | Type |
|------|------|
| Abort | Abort |
| Generate rows | RowGenerator |
| REST client | Rest |
| check result | FilterRows |

## Hops

| From | To |
|------|----|
| Generate rows | REST client |
| REST client | check result |
| check result | Abort |
