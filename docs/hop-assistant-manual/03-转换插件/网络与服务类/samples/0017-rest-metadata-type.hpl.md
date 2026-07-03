# Pipeline: 0017-rest-metadata-type

## Basic Information

- **Pipeline Name:** 0017-rest-metadata-type
- **Source File:** `03-转换插件/网络与服务类/samples/0017-rest-metadata-type.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| HOSTNAME | localhost |  |

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
| check result | Abort |
| REST client | check result |
