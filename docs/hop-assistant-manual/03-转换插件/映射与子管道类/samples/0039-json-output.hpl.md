# Pipeline: 0039-json-output

## Basic Information

- **Pipeline Name:** 0039-json-output
- **Source File:** `03-转换插件/映射与子管道类/samples/0039-json-output.hpl`

## Transforms

| Name | Type |
|------|------|
| 0039-json-output-template.hpl | MetaInject |
| files/json-output.xml | getXMLData |
| files/json-output.xml fields | getXMLData |

## Hops

| From | To |
|------|----|
| files/json-output.xml | 0039-json-output-template.hpl |
| files/json-output.xml fields | 0039-json-output-template.hpl |
