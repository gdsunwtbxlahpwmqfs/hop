# Pipeline: 0100-json-input-include-subfolders

## Basic Information

- **Pipeline Name:** 0100-json-input-include-subfolders
- **Description:** Issue 7166: JSON Input must honor include_subfolders when loading legacy file XML.
- **Source File:** `03-转换插件/输入类/samples/0100-json-input-include-subfolders.hpl`

## Transforms

| Name | Type |
|------|------|
| JSON input | JsonInput |
| Sort rows | SortRows |
| validate | Dummy |

## Hops

| From | To |
|------|----|
| JSON input | Sort rows |
| Sort rows | validate |
