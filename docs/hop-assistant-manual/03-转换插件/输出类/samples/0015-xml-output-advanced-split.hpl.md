# Pipeline: 0015-xml-output-advanced-split

## Basic Information

- **Pipeline Name:** 0015-xml-output-advanced-split
- **Description:** Integration test: XML Output (Advanced) split-every rolls over to a new output file every N rows.
- **Source File:** `03-转换插件/输出类/samples/0015-xml-output-advanced-split.hpl`

## Transforms

| Name | Type |
|------|------|
| events grid | DataGrid |
| XML Output (Advanced) | AdvancedXMLOutput |

## Hops

| From | To |
|------|----|
| events grid | XML Output (Advanced) |
