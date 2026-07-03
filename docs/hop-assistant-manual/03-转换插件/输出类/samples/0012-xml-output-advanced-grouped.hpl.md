# Pipeline: 0012-xml-output-advanced-grouped

## Basic Information

- **Pipeline Name:** 0012-xml-output-advanced-grouped
- **Description:** Integration test: XML Output (Advanced) with group-by, attributes, default namespace, DOCTYPE, XSL PI and a generated XSD.
- **Source File:** `03-转换插件/输出类/samples/0012-xml-output-advanced-grouped.hpl`

## Transforms

| Name | Type |
|------|------|
| order lines grid | DataGrid |
| sort by orderId | SortRows |
| XML Output (Advanced) | AdvancedXMLOutput |

## Hops

| From | To |
|------|----|
| order lines grid | sort by orderId |
| sort by orderId | XML Output (Advanced) |
