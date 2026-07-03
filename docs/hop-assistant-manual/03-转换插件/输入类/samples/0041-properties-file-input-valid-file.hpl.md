# Pipeline: 0041-properties-file-input-valid-file

## Basic Information

- **Pipeline Name:** 0041-properties-file-input-valid-file
- **Source File:** `03-转换插件/输入类/samples/0041-properties-file-input-valid-file.hpl`

## Transforms

| Name | Type |
|------|------|
| read non existing file required N | PropertyInput |
| Dummy (do nothing) | Dummy |

## Hops

| From | To |
|------|----|
| read non existing file required N | Dummy (do nothing) |
