# Pipeline: 0001-http-post-test-headers

## Basic Information

- **Pipeline Name:** 0001-http-post-test-headers
- **Source File:** `03-转换插件/网络与服务类/samples/0001-http-post-test-headers.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| HOSTNAME | localhost |  |

## Transforms

| Name | Type |
|------|------|
| Abort | Abort |
| Generate rows | RowGenerator |
| HTTP post | HttpPost |
| JSON input | JsonInput |
| check result | FilterRows |

## Hops

| From | To |
|------|----|
| Generate rows | HTTP post |
| check result | Abort |
| HTTP post | JSON input |
| JSON input | check result |
