# Pipeline: 0006-rest-client-trustore

## Basic Information

- **Pipeline Name:** 0006-rest-client-trustore
- **Source File:** `03-转换插件/网络与服务类/samples/0006-rest-client-trustore.hpl`

## Transforms

| Name | Type |
|------|------|
| Dummy (do nothing) | Dummy |
| Generate rows | RowGenerator |
| custom trustore rest client | Rest |
| default trustore rest client | Rest |
| ignore trustore (SSL) rest client | Rest |

## Hops

| From | To |
|------|----|
| default trustore rest client | Dummy (do nothing) |
| Generate rows | default trustore rest client |
| Generate rows | custom trustore rest client |
| Generate rows | ignore trustore (SSL) rest client |
