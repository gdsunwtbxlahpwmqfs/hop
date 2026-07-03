# Pipeline: 0053-xml-output-validate

## Basic Information

- **Pipeline Name:** 0053-xml-output-validate
- **Source File:** `03-转换插件/映射与子管道类/samples/0053-xml-output-validate.hpl`

## Transforms

| Name | Type |
|------|------|
| /tmp/xml-output.hpl | getXMLData |
| /tmp/xml-output.hpl fields | getXMLData |
| validate | Dummy |
| validate fields | Dummy |

## Hops

| From | To |
|------|----|
| /tmp/xml-output.hpl | validate |
| /tmp/xml-output.hpl fields | validate fields |
