# Pipeline: xml-output-advanced-basic

## Basic Information

- **Pipeline Name:** xml-output-advanced-basic
- **Description:** Minimal XML Output (Advanced) example: emit a flat <customers><customer>... document with a sibling .xsd schema.
- **Source File:** `03-转换插件/输出类/samples/xml-output-advanced-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| customers grid | DataGrid |
| XML Output (Advanced) | AdvancedXMLOutput |

## Hops

| From | To |
|------|----|
| customers grid | XML Output (Advanced) |

## Notes

Writes a flat <customers><customer>... document together with a sibling

.xsd describing the structure.

Output: ${java.io.tmpdir}/xml-output-advanced-basic.xml (+ .xsd)

---
