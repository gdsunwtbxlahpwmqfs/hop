# Pipeline: 0017-xml-output-advanced-zipped

## Basic Information

- **Pipeline Name:** 0017-xml-output-advanced-zipped
- **Description:** Integration test: XML Output (Advanced) zipped output. The XML lives inside a .zip archive and the sibling .xsd is written next to the .zip.
- **Source File:** `03-转换插件/输出类/samples/0017-xml-output-advanced-zipped.hpl`

## Transforms

| Name | Type |
|------|------|
| transactions grid | DataGrid |
| XML Output (Advanced) | AdvancedXMLOutput |

## Hops

| From | To |
|------|----|
| transactions grid | XML Output (Advanced) |
