# Pipeline: 0014-xml-output-advanced-document-fragment

## Basic Information

- **Pipeline Name:** 0014-xml-output-advanced-document-fragment
- **Description:** Integration test: XML Output (Advanced) DocumentFragment node parses an XML fragment field and inserts it as real XML.
- **Source File:** `03-转换插件/输出类/samples/0014-xml-output-advanced-document-fragment.hpl`

## Transforms

| Name | Type |
|------|------|
| products grid | DataGrid |
| XML Output (Advanced) | AdvancedXMLOutput |

## Hops

| From | To |
|------|----|
| products grid | XML Output (Advanced) |
