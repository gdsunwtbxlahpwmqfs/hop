# Pipeline: 0039-json-output-validate

## Basic Information

- **Pipeline Name:** 0039-json-output-validate
- **Source File:** `03-转换插件/映射与子管道类/samples/0039-json-output-validate.hpl`

## Transforms

| Name | Type |
|------|------|
| /tmp/json-output.hpl | getXMLData |
| validate | Dummy |
| /tmp/json-output.hpl fields | getXMLData |
| validate fields | Dummy |

## Hops

| From | To |
|------|----|
| /tmp/json-output.hpl | validate |
| /tmp/json-output.hpl fields | validate fields |
