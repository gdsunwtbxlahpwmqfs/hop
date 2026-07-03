# Pipeline: 0003-rest-client

## Basic Information

- **Pipeline Name:** 0003-rest-client
- **Source File:** `03-转换插件/网络与服务类/samples/0003-rest-client.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| HOSTNAME | localhost |  |

## Transforms

| Name | Type |
|------|------|
| Abort | Abort |
| Generate rows | RowGenerator |
| check result | FilterRows |
| REST client | Rest |

## Hops

| From | To |
|------|----|
| check result | Abort |
| Generate rows | REST client |
| REST client | check result |
