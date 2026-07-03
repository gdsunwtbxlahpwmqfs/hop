# Pipeline: 0018-xml-output-advanced-chained

## Basic Information

- **Pipeline Name:** 0018-xml-output-advanced-chained
- **Description:** Integration test: two chained XML Output (Advanced) transforms — output compared to golden files. Same data and tree as sample xml-output-advanced-chained.hpl.
- **Source File:** `03-转换插件/输出类/samples/0018-xml-output-advanced-chained.hpl`

## Transforms

| Name | Type |
|------|------|
| people addresses grid | DataGrid |
| addresses to xml field | AdvancedXMLOutput |
| people xml file and field | AdvancedXMLOutput |

## Hops

| From | To |
|------|----|
| people addresses grid | addresses to xml field |
| addresses to xml field | people xml file and field |

## Notes

Demonstrates two XML Output (Advanced) transforms in sequence (see also xml-output-advanced-person-addresses.hpl).

---
