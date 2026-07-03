# Pipeline: 0022-stream-lookup-default-variables

## Basic Information

- **Pipeline Name:** 0022-stream-lookup-default-variables
- **Source File:** `03-转换插件/查找与连接类/samples/0022-stream-lookup-default-variables.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| UNKNOWN_COUNTRY_NAME | Unknown | The default value for the lookup of the country name. |

## Transforms

| Name | Type |
|------|------|
| Countries | DataGrid |
| Input data | DataGrid |
| Lookup country name | StreamLookup |
| Verify | Dummy |

## Hops

| From | To |
|------|----|
| Input data | Lookup country name |
| Countries | Lookup country name |
| Lookup country name | Verify |
