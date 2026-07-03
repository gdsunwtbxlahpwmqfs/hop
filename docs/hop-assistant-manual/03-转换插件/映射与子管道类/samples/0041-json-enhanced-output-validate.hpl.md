# Pipeline: 0041-json-enhanced-output-validate

## Basic Information

- **Pipeline Name:** 0041-json-enhanced-output-validate
- **Source File:** `03-转换插件/映射与子管道类/samples/0041-json-enhanced-output-validate.hpl`

## Transforms

| Name | Type |
|------|------|
| /tmp/json-enhanced-output.hpl | getXMLData |
| /tmp/json-enhanced-output.hpl fields | getXMLData |
| /tmp/json-enhanced-output.hpl keys | getXMLData |
| validate | Dummy |
| validate fields | Dummy |
| validate keys | Dummy |

## Hops

| From | To |
|------|----|
| /tmp/json-enhanced-output.hpl | validate |
| /tmp/json-enhanced-output.hpl fields | validate fields |
| /tmp/json-enhanced-output.hpl keys | validate keys |
