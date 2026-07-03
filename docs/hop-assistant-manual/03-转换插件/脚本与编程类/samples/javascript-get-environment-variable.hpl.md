# Pipeline: javascript-get-path

## Basic Information

- **Pipeline Name:** javascript-get-path
- **Source File:** `03-转换插件/脚本与编程类/samples/javascript-get-environment-variable.hpl`

## Transforms

| Name | Type |
|------|------|
| Write to log | WriteToLog |
| generate 1 row | RowGenerator |
| read environment variable | ScriptValueMod |

## Hops

| From | To |
|------|----|
| generate 1 row | read environment variable |
| read environment variable | Write to log |

## Notes

This sample uses javascript to read a system variable ("PATH" in this case) as a field into the pipeline.

---
