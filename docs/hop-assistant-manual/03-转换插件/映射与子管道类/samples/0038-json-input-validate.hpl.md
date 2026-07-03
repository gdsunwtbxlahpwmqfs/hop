# Pipeline: 0038-json-input-validate

## Basic Information

- **Pipeline Name:** 0038-json-input-validate
- **Source File:** `03-转换插件/映射与子管道类/samples/0038-json-input-validate.hpl`

## Transforms

| Name | Type |
|------|------|
| /tmp/json-input.hpl | getXMLData |
| /tmp/json-input.hpl fields | getXMLData |
| /tmp/json-input.hpl files | getXMLData |
| validate | Dummy |
| validate fields | Dummy |
| validate files | Dummy |

## Hops

| From | To |
|------|----|
| /tmp/json-input.hpl | validate |
| /tmp/json-input.hpl fields | validate fields |
| /tmp/json-input.hpl files | validate files |
