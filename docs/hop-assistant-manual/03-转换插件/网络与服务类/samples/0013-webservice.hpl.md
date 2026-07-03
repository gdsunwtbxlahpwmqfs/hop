# Pipeline: 0013-webservice

## Basic Information

- **Pipeline Name:** 0013-webservice
- **Source File:** `03-转换插件/网络与服务类/samples/0013-webservice.hpl`

## Transforms

| Name | Type |
|------|------|
| Generate rows | RowGenerator |
| Web services lookup | WebServiceLookup |

## Hops

| From | To |
|------|----|
| Generate rows | Web services lookup |
