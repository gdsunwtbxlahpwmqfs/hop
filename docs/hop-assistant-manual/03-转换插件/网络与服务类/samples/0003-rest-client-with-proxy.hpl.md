# Pipeline: 0003-rest-client-with-proxy

## Basic Information

- **Pipeline Name:** 0003-rest-client-with-proxy
- **Source File:** `03-转换插件/网络与服务类/samples/0003-rest-client-with-proxy.hpl`

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
| check result | Abort |
| Generate rows | REST client |
| REST client | check result |
