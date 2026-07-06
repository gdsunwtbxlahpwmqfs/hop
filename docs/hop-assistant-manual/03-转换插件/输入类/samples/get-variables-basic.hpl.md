# Pipeline: get-variables-basic

## Basic Information

- **Pipeline Name:** get-variables-basic
- **Source File:** `03-转换插件/输入类/samples/get-variables-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| generate 1 row | RowGenerator |
| get variables | GetVariable |
| log variable values | WriteToLog |

## Hops

| From | To |
|------|----|
| generate 1 row | get variables |
| get variables | log variable values |

## Notes

get a number of different variables from the current Qi Hop project, os, java and user environment

---
