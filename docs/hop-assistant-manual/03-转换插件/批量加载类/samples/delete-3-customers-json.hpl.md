# Pipeline: delete-3-customers-json

## Basic Information

- **Pipeline Name:** delete-3-customers-json
- **Source File:** `03-转换插件/批量加载类/samples/delete-3-customers-json.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| NAME1 | uvhph-name |  |
| NAME2 | qfwcj-name |  |
| NAME3 | xtukc-name |  |

## Transforms

| Name | Type |
|------|------|
| delete 3 customers | MongoDbDelete |
| generate 1 row | RowGenerator |

## Hops

| From | To |
|------|----|
| generate 1 row | delete 3 customers |
