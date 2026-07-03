# Pipeline: 0046-set-constant-values

## Basic Information

- **Pipeline Name:** 0046-set-constant-values
- **Source File:** `03-转换插件/映射与子管道类/samples/0046-set-constant-values.hpl`

## Transforms

| Name | Type |
|------|------|
| 0046-set-contant-values-template.hpl | MetaInject |
| use-variables | DataGrid |
| Get data from XML | getXMLData |

## Hops

| From | To |
|------|----|
| use-variables | 0046-set-contant-values-template.hpl |
| Get data from XML | 0046-set-contant-values-template.hpl |
