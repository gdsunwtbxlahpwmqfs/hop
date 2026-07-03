# Pipeline: 0060-get-subfolder-names

## Basic Information

- **Pipeline Name:** 0060-get-subfolder-names
- **Source File:** `03-转换插件/输入类/samples/0060-get-subfolder-names.hpl`

## Transforms

| Name | Type |
|------|------|
| Get subfolder names | GetSubFolders |
| Select values | SelectValues |
| folderName, rowNumber only | SelectValues |
| projectHome | GetVariable |
| shortName | ScriptValueMod |
| validate | Dummy |

## Hops

| From | To |
|------|----|
| projectHome | shortName |
| Get subfolder names | folderName, rowNumber only |
| folderName, rowNumber only | projectHome |
| shortName | Select values |
| Select values | validate |
