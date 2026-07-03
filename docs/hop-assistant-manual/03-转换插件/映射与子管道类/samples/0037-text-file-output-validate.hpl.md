# Pipeline: 0037-text-file-output-validate

## Basic Information

- **Pipeline Name:** 0037-text-file-output-validate
- **Source File:** `03-转换插件/映射与子管道类/samples/0037-text-file-output-validate.hpl`

## Transforms

| Name | Type |
|------|------|
| Validate | Dummy |
| Validate fields | Dummy |
| files/textfile/text-file-output.xml - general | getXMLData |
| text-file-output-mdi.hpl - fields | getXMLData |

## Hops

| From | To |
|------|----|
| files/textfile/text-file-output.xml - general | Validate |
| text-file-output-mdi.hpl - fields | Validate fields |
