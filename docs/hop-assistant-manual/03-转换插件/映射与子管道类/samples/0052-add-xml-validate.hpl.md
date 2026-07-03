# Pipeline: 0052-add-xml-validate

## Basic Information

- **Pipeline Name:** 0052-add-xml-validate
- **Source File:** `03-转换插件/映射与子管道类/samples/0052-add-xml-validate.hpl`

## Transforms

| Name | Type |
|------|------|
| /tmp/add-xml.hpl | getXMLData |
| validate | Dummy |
| /tmp/add-xml.hpl fields | getXMLData |
| validate 2 | Dummy |

## Hops

| From | To |
|------|----|
| /tmp/add-xml.hpl | validate |
| /tmp/add-xml.hpl fields | validate 2 |
