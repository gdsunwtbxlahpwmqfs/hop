# Pipeline: template-file-input

## Basic Information

- **Pipeline Name:** template-file-input
- **Source File:** `03-转换插件/映射与子管道类/samples/process-files-with-mdi-template.hpl`

## Transforms

| Name | Type |
|------|------|
| Select values | SelectValues |
| Text file input | TextFileInput2 |
| Text file output | TextFileOutput |

## Hops

| From | To |
|------|----|
| Text file input | Select values |
| Select values | Text file output |
