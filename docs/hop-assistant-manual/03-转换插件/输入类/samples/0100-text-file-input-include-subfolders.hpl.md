# Pipeline: 0100-text-file-input-include-subfolders

## Basic Information

- **Pipeline Name:** 0100-text-file-input-include-subfolders
- **Description:** Issue 7166: Text File Input must honor include_subfolders when loading legacy file XML.
- **Source File:** `03-转换插件/输入类/samples/0100-text-file-input-include-subfolders.hpl`

## Transforms

| Name | Type |
|------|------|
| Text file input | TextFileInput2 |
| Sort rows | SortRows |
| validate | Dummy |

## Hops

| From | To |
|------|----|
| Text file input | Sort rows |
| Sort rows | validate |
