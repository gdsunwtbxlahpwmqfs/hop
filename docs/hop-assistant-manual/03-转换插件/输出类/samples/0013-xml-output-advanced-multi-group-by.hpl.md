# Pipeline: 0013-xml-output-advanced-multi-group-by

## Basic Information

- **Pipeline Name:** 0013-xml-output-advanced-multi-group-by
- **Description:** Integration test: XML Output (Advanced) with two levels of group-by ancestors above the row loop.
- **Source File:** `03-转换插件/输出类/samples/0013-xml-output-advanced-multi-group-by.hpl`

## Transforms

| Name | Type |
|------|------|
| order lines grid | DataGrid |
| sort by region, orderId | SortRows |
| XML Output (Advanced) | AdvancedXMLOutput |

## Hops

| From | To |
|------|----|
| order lines grid | sort by region, orderId |
| sort by region, orderId | XML Output (Advanced) |
