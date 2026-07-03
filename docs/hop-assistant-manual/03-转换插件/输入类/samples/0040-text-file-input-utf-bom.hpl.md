# Pipeline: 0040-text-file-input-utf-bom

## Basic Information

- **Pipeline Name:** 0040-text-file-input-utf-bom
- **Source File:** `03-转换插件/输入类/samples/0040-text-file-input-utf-bom.hpl`

## Transforms

| Name | Type |
|------|------|
| Verify | Dummy |
| files/data-with-utf-8-bom.csv | TextFileInput2 |

## Hops

| From | To |
|------|----|
| files/data-with-utf-8-bom.csv | Verify |
