# Pipeline: 0011-xml-output-advanced-basic

## Basic Information

- **Pipeline Name:** 0011-xml-output-advanced-basic
- **Description:** Integration test: minimal XML Output (Advanced) example. Writes a flat <customers><customer>... document with a sibling .xsd schema.
- **Source File:** `03-转换插件/输出类/samples/0011-xml-output-advanced-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| customers grid | DataGrid |
| XML Output (Advanced) | AdvancedXMLOutput |

## Hops

| From | To |
|------|----|
| customers grid | XML Output (Advanced) |
