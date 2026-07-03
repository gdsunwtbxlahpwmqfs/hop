# Pipeline: 0008-rest-client-put

## Basic Information

- **Pipeline Name:** 0008-rest-client-put
- **Source File:** `03-转换插件/网络与服务类/samples/0008-rest-client-put.hpl`

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
