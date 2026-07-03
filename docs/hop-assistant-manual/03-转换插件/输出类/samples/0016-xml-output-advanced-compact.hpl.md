# Pipeline: 0016-xml-output-advanced-compact

## Basic Information

- **Pipeline Name:** 0016-xml-output-advanced-compact
- **Description:** Integration test: XML Output (Advanced) compact mode + force-create + create-attribute-if-null/unmapped + default values for null fields.
- **Source File:** `03-转换插件/输出类/samples/0016-xml-output-advanced-compact.hpl`

## Transforms

| Name | Type |
|------|------|
| products grid | DataGrid |
| XML Output (Advanced) | AdvancedXMLOutput |

## Hops

| From | To |
|------|----|
| products grid | XML Output (Advanced) |
