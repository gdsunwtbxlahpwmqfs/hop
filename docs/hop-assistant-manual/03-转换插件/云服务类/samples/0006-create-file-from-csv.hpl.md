# Pipeline: 0006-create-file-from-csv

## Basic Information

- **Pipeline Name:** 0006-create-file-from-csv
- **Source File:** `03-转换插件/云服务类/samples/0006-create-file-from-csv.hpl`

## Transforms

| Name | Type |
|------|------|
| Output to container root | TextFileOutput |
| Output to container subfolder - level 1 | TextFileOutput |
| Output to container subfolder - level 2 | TextFileOutput |
| Read from csv | TextFileInput2 |

## Hops

| From | To |
|------|----|
| Read from csv | Output to container root |
| Read from csv | Output to container subfolder - level 1 |
| Read from csv | Output to container subfolder - level 2 |
