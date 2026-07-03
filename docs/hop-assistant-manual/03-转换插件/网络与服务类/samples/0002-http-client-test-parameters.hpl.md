# Pipeline: 0002-http-client-test-parameters

## Basic Information

- **Pipeline Name:** 0002-http-client-test-parameters
- **Source File:** `03-转换插件/网络与服务类/samples/0002-http-client-test-parameters.hpl`

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
| JSON input | JsonInput |
| check result | FilterRows |

## Hops

| From | To |
|------|----|
| check result | Abort |
| JSON input | check result |
| Generate rows | HTTP client |
| HTTP client | JSON input |
