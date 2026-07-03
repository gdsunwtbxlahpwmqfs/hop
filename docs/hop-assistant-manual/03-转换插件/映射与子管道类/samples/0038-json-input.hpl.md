# Pipeline: 0038-json-input

## Basic Information

- **Pipeline Name:** 0038-json-input
- **Source File:** `03-转换插件/映射与子管道类/samples/0038-json-input.hpl`

## Transforms

| Name | Type |
|------|------|
| 0038-json-input-template.hpl | MetaInject |
| files/json-input.xml | getXMLData |
| files/json-input.xml fields | getXMLData |
| files/json-input.xml files | getXMLData |

## Hops

| From | To |
|------|----|
| files/json-input.xml | 0038-json-input-template.hpl |
| files/json-input.xml fields | 0038-json-input-template.hpl |
| files/json-input.xml files | 0038-json-input-template.hpl |
