# Pipeline: 0100-load-file-content-include-subfolders

## Basic Information

- **Pipeline Name:** 0100-load-file-content-include-subfolders
- **Description:** Issue 7166: Load file content must honor include_subfolders when loading legacy file XML.
- **Source File:** `03-转换插件/输入类/samples/0100-load-file-content-include-subfolders.hpl`

## Transforms

| Name | Type |
|------|------|
| Load file content | LoadFileInput |
| Sort rows | SortRows |
| validate | Dummy |

## Hops

| From | To |
|------|----|
| Load file content | Sort rows |
| Sort rows | validate |
