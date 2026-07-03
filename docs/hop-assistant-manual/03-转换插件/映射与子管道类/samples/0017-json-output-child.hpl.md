# Pipeline: 0017-json-output-child

## Basic Information

- **Pipeline Name:** 0017-json-output-child
- **Source File:** `03-转换插件/映射与子管道类/samples/0017-json-output-child.hpl`

## Transforms

| Name | Type |
|------|------|
| Generate rows | RowGenerator |
| JSON output | JsonOutput |

## Hops

| From | To |
|------|----|
| Generate rows | JSON output |
