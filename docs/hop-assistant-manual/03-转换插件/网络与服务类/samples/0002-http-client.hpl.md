# Pipeline: 0002-http-client

## Basic Information

- **Pipeline Name:** 0002-http-client
- **Source File:** `03-转换插件/网络与服务类/samples/0002-http-client.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| HOSTNAME | localhost |  |

## Transforms

| Name | Type |
|------|------|
| HTTP client | Http |
| Generate rows | RowGenerator |
| Abort | Abort |
| check result | FilterRows |

## Hops

| From | To |
|------|----|
| Generate rows | HTTP client |
| check result | Abort |
| HTTP client | check result |
