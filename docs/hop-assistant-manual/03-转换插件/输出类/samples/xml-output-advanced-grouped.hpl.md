# Pipeline: xml-output-advanced-grouped

## Basic Information

- **Pipeline Name:** xml-output-advanced-grouped
- **Description:** XML Output (Advanced) example with group-by, attributes, a default XML namespace, a DOCTYPE, an XSL stylesheet processing instruction, and a generated XSD.
- **Source File:** `03-转换插件/输出类/samples/xml-output-advanced-grouped.hpl`

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

## Notes

Group-by example: order rows are sorted by orderId then folded under one

<order> element per id, with their item rows nested inside.

The transform also emits:

- a default XML namespace on the root,

- a DOCTYPE declaration,

- an <?xml-stylesheet?> processing instruction,

- a sibling .xsd schema next to the .xml output.

Output: ${java.io.tmpdir}/xml-output-advanced-grouped.xml (+ .xsd)

---
