# Pipeline: 0012-get-subfolder-names

## Basic Information

- **Pipeline Name:** 0012-get-subfolder-names
- **Source File:** `03-转换插件/Beam大数据类/samples/0012-get-subfolder-names.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| TEST_FOLDER | /tmp/0012 |  |

## Transforms

| Name | Type |
|------|------|
| /tmp/0012/get-subfolder-names*.csv | BeamOutput |
| Get subfolder names | GetSubFolders |
| Select values | SelectValues |
| folderName, rowNumber only | SelectValues |
| projectHome | GetVariable |
| shortName | ScriptValueMod |

## Hops

| From | To |
|------|----|
| Get subfolder names | folderName, rowNumber only |
| folderName, rowNumber only | projectHome |
| projectHome | shortName |
| shortName | Select values |
| Select values | /tmp/0012/get-subfolder-names*.csv |
