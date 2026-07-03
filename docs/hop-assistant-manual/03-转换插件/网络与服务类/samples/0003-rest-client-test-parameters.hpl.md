# Pipeline: 0003-rest-client-test-parameters

## Basic Information

- **Pipeline Name:** 0003-rest-client-test-parameters
- **Source File:** `03-转换插件/网络与服务类/samples/0003-rest-client-test-parameters.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| HOSTNAME | localhost |  |
| parameter |  |  |

## Transforms

| Name | Type |
|------|------|
| Abort | Abort |
| Generate rows | RowGenerator |
| JSON input | JsonInput |
| check result | FilterRows |
| REST client | Rest |

## Hops

| From | To |
|------|----|
| check result | Abort |
| JSON input | check result |
| Generate rows | REST client |
| REST client | JSON input |
