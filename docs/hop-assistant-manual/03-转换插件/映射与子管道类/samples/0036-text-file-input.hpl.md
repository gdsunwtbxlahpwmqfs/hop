# Pipeline: 0036-text-file-input

## Basic Information

- **Pipeline Name:** 0036-text-file-input
- **Source File:** `03-转换插件/映射与子管道类/samples/0036-text-file-input.hpl`

## Transforms

| Name | Type |
|------|------|
| files/textfile/text-file-input.xml - general | getXMLData |
| inject into /tmp/text-file-input-mdi.hpl | MetaInject |
| files | DataGrid |
| files/textfile/text-file-input.xml - fields | getXMLData |

## Hops

| From | To |
|------|----|
| files/textfile/text-file-input.xml - general | inject into /tmp/text-file-input-mdi.hpl |
| files | inject into /tmp/text-file-input-mdi.hpl |
| files/textfile/text-file-input.xml - fields | inject into /tmp/text-file-input-mdi.hpl |
