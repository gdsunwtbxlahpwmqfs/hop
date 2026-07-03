# Pipeline: xml-output-advanced-person-addresses

## Basic Information

- **Pipeline Name:** xml-output-advanced-person-addresses
- **Description:** Long address rows: first XML Output (Advanced) builds <addresses> per person (group-by + loop) into field addressesXml using Output XML as field + Split every 4 rows; second transform merges into <people> and writes file + peopleXml. DocumentFragment removes the duplicate <addresses> wrapper.
- **Source File:** `03-转换插件/输出类/samples/xml-output-advanced-person-addresses.hpl`

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

Grid: one row per address (4 per person × 3 people). Rows stay sorted by person so group-by works.

First XML Output (Advanced): Output = "Output XML as field", field addressesXml, Split every 4 rows (one segment per person; must match rows-per-group). Tree: <addresses group-by person_name> <address loop> …

Second XML Output (Advanced): Output = "Both", peopleXml + ${java.io.tmpdir}/xml-output-advanced-person-addresses.xml. Under each <person>, DocumentFragment maps addressesXml with "Remove outer wrapper (duplicate parent tag)" so the modeled <addresses> is not doubled.

---
