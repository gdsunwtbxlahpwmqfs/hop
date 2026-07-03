# Pipeline: xml-output-advanced-chained

## Basic Information

- **Pipeline Name:** xml-output-advanced-chained
- **Description:** Chained XML Output (Advanced): first step builds <addresses> per person into field addressesXml (output to field + split); second step merges into <people> and writes file + peopleXml. Same data and tree as xml-output-advanced-person-addresses.hpl.
- **Source File:** `03-转换插件/输出类/samples/xml-output-advanced-chained.hpl`

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
