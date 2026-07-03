# Pipeline: 0001-opensearch-create-hop-index

## Basic Information

- **Pipeline Name:** 0001-opensearch-create-hop-index
- **Source File:** `03-转换插件/输出类/samples/0001-opensearch-create-hop-index.hpl`

## Transforms

| Name | Type |
|------|------|
| DELETE hop index | Rest |
| GET hop index | Rest |
| INPUT | DataGrid |
| PUT hop index | Rest |
| getCode=200 | FilterRows |

## Hops

| From | To |
|------|----|
| INPUT | GET hop index |
| GET hop index | getCode=200 |
| getCode=200 | DELETE hop index |
| getCode=200 | PUT hop index |
| DELETE hop index | PUT hop index |
