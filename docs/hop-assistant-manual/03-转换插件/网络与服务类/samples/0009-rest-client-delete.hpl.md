# Pipeline: 0009-rest-client-delete

## Basic Information

- **Pipeline Name:** 0009-rest-client-delete
- **Source File:** `03-转换插件/网络与服务类/samples/0009-rest-client-delete.hpl`

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
