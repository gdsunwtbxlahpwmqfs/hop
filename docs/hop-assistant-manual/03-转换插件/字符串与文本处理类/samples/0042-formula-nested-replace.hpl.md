# Pipeline: 0042-formula-nested-replace

## Basic Information

- **Pipeline Name:** 0042-formula-nested-replace
- **Source File:** `03-转换插件/字符串与文本处理类/samples/0042-formula-nested-replace.hpl`

## Transforms

| Name | Type |
|------|------|
| foo, bar | DataGrid |
| formula - nested replace | Formula |
| verify | Dummy |

## Hops

| From | To |
|------|----|
| foo, bar | formula - nested replace |
| formula - nested replace | verify |
