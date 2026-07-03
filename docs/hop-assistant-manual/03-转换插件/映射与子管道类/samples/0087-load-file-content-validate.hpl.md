# Pipeline: 0087-load-file-content-validate

## Basic Information

- **Pipeline Name:** 0087-load-file-content-validate
- **Source File:** `03-转换插件/映射与子管道类/samples/0087-load-file-content-validate.hpl`

## Transforms

| Name | Type |
|------|------|
| /tmp/load-file-content.hpl | getXMLData |
| validate | Dummy |
| /tmp/load-file-content.hpl files | getXMLData |
| /tmp/load-file-content.hpl fields | getXMLData |
| validate files | Dummy |
| validate fields | Dummy |

## Hops

| From | To |
|------|----|
| /tmp/load-file-content.hpl | validate |
| /tmp/load-file-content.hpl files | validate files |
| /tmp/load-file-content.hpl fields | validate fields |
