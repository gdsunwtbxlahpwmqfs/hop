# Pipeline: 0042-formula-information

## Basic Information

- **Pipeline Name:** 0042-formula-information
- **Source File:** `03-转换插件/字符串与文本处理类/samples/0042-formula-information.hpl`

## Transforms

| Name | Type |
|------|------|
| Verify | Dummy |
| information formulas | Formula |
| information grid | DataGrid |

## Hops

| From | To |
|------|----|
| information grid | information formulas |
| information formulas | Verify |
