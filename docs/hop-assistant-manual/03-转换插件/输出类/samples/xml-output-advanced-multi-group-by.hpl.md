# Pipeline: xml-output-advanced-multi-group-by

## Basic Information

- **Pipeline Name:** xml-output-advanced-multi-group-by
- **Description:** Two levels of group-by: rows are folded under <region> then under <order>, with line items as the row loop.
- **Source File:** `03-转换插件/输出类/samples/xml-output-advanced-multi-group-by.hpl`

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

## Notes

Two levels of group-by demonstrate that the loop can sit inside multiple

group-by ancestors. The Sort Rows step pre-sorts by region, then orderId, so

the runtime can collapse consecutive matching keys without buffering.

Tree: regions/region[group region] / order[group orderId] / lines/line[loop]

Output: ${java.io.tmpdir}/xml-output-advanced-multi-group-by.xml

---
