# Pipeline: json-input-basic

## Basic Information

- **Pipeline Name:** json-input-basic
- **Source File:** `03-转换插件/输入类/samples/json-input-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| preview | Dummy |
| read pipeline-log-example.json | JsonInput |

## Hops

| From | To |
|------|----|
| read pipeline-log-example.json | preview |

## Notes

This sample reads the JSON definition for the Pipeline Log Example metadata item.

This metadata item is defined in ${PROJECT_HOME}/metadata/pipeline-log/pipeline-log-example.json

This is a fairly simple file, but contains an array (one element) and 3 data types.

---
