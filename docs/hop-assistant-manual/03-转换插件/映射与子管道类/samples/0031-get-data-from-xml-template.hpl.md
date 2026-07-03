# Pipeline: 0031-get-data-from-xml-template

## Basic Information

- **Pipeline Name:** 0031-get-data-from-xml-template
- **Source File:** `03-转换插件/映射与子管道类/samples/0031-get-data-from-xml-template.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| Get data from XML | getXMLData |
| OUTPUT | Dummy |

## Hops

| From | To |
|------|----|
| Data grid | Get data from XML |
| Get data from XML | OUTPUT |
