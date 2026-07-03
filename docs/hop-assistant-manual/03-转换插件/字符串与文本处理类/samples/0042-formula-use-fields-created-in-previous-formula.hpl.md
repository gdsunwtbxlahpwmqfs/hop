# Pipeline: 0069-formula-use-fields-created-in-previous-formula

## Basic Information

- **Pipeline Name:** 0069-formula-use-fields-created-in-previous-formula
- **Source File:** `03-转换插件/字符串与文本处理类/samples/0042-formula-use-fields-created-in-previous-formula.hpl`

## Transforms

| Name | Type |
|------|------|
| foo, bar | DataGrid |
| re-use formula field | Formula |
| verify | Dummy |

## Hops

| From | To |
|------|----|
| re-use formula field | verify |
| foo, bar | re-use formula field |
