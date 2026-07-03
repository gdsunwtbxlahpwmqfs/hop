# Pipeline: 0052-add-xml

## Basic Information

- **Pipeline Name:** 0052-add-xml
- **Source File:** `03-转换插件/映射与子管道类/samples/0052-add-xml.hpl`

## Transforms

| Name | Type |
|------|------|
| 0052-add-xml-template.hpl | MetaInject |
| files/add-xml.xml | getXMLData |
| files/add-xml.xml fields | getXMLData |

## Hops

| From | To |
|------|----|
| files/add-xml.xml | 0052-add-xml-template.hpl |
| files/add-xml.xml fields | 0052-add-xml-template.hpl |
