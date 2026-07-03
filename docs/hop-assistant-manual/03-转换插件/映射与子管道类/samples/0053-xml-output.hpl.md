# Pipeline: 0053-xml-output

## Basic Information

- **Pipeline Name:** 0053-xml-output
- **Source File:** `03-转换插件/映射与子管道类/samples/0053-xml-output.hpl`

## Transforms

| Name | Type |
|------|------|
| 0053-xml-output-template.hpl | MetaInject |
| files/xml-output.xml | getXMLData |
| files/xml-output.xml fields | getXMLData |

## Hops

| From | To |
|------|----|
| files/xml-output.xml | 0053-xml-output-template.hpl |
| files/xml-output.xml fields | 0053-xml-output-template.hpl |
