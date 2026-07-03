# Pipeline: 0001-http-post-test-body

## Basic Information

- **Pipeline Name:** 0001-http-post-test-body
- **Source File:** `03-转换插件/网络与服务类/samples/0001-http-post-test-body.hpl`

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
