# Pipeline: 0001-deserialize

## Basic Information

- **Pipeline Name:** 0001-deserialize
- **Source File:** `03-转换插件/输入类/samples/0001-deserialize.hpl`

## Transforms

| Name | Type |
|------|------|
| Generate rows | RowGenerator |
| Test deserialize | UserDefinedJavaClass |

## Hops

| From | To |
|------|----|
| Generate rows | Test deserialize |
