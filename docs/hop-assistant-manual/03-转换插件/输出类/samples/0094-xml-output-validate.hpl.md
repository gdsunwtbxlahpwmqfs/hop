# Pipeline: 0094-xml-output-validate

## Basic Information

- **Pipeline Name:** 0094-xml-output-validate
- **Source File:** `03-转换插件/输出类/samples/0094-xml-output-validate.hpl`

## Transforms

| Name | Type |
|------|------|
| validate | Dummy |
| /tmp/xml-output-file.xml | getXMLData |

## Hops

| From | To |
|------|----|
| /tmp/xml-output-file.xml | validate |
