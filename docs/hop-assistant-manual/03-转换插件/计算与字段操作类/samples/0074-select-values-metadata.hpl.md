# Pipeline: 0074-select-values-metadata

## Basic Information

- **Pipeline Name:** 0074-select-values-metadata
- **Source File:** `03-转换插件/计算与字段操作类/samples/0074-select-values-metadata.hpl`

## Transforms

| Name | Type |
|------|------|
| Assert | Dummy |
| Read csv | TextFileInput2 |
| Select values | SelectValues |

## Hops

| From | To |
|------|----|
| Read csv | Select values |
| Select values | Assert |
