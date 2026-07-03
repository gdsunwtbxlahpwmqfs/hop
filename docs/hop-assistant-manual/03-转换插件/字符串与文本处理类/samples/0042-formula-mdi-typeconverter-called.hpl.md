# Pipeline: 5164-002

## Basic Information

- **Pipeline Name:** 5164-002
- **Source File:** `03-转换插件/字符串与文本处理类/samples/0042-formula-mdi-typeconverter-called.hpl`

## Transforms

| Name | Type |
|------|------|
| Generate rows | RowGenerator |
| Formula | Formula |
| Write to log | WriteToLog |

## Hops

| From | To |
|------|----|
| Generate rows | Formula |
| Formula | Write to log |
