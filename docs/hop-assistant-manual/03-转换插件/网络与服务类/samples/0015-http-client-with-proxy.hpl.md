# Pipeline: 0015-http-client-with-proxy

## Basic Information

- **Pipeline Name:** 0015-http-client-with-proxy
- **Source File:** `03-转换插件/网络与服务类/samples/0015-http-client-with-proxy.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| HOSTNAME | localhost |  |

## Transforms

| Name | Type |
|------|------|
| Abort | Abort |
| Generate rows | RowGenerator |
| HTTP client | Http |
| check result | FilterRows |

## Hops

| From | To |
|------|----|
| Generate rows | HTTP client |
| check result | Abort |
| HTTP client | check result |
