# Pipeline: 0004-webservice-available

## Basic Information

- **Pipeline Name:** 0004-webservice-available
- **Source File:** `03-转换插件/网络与服务类/samples/0004-webservice-available.hpl`

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
| Check if webservice is available | WebServiceAvailable |
| Get variables | GetVariable |
| Calculator | Calculator |

## Hops

| From | To |
|------|----|
| check result | Abort |
| Check if webservice is available | check result |
| Generate rows | Get variables |
| Get variables | Calculator |
| Calculator | Check if webservice is available |
