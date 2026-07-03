# Pipeline: xml-output-advanced-document-fragment

## Basic Information

- **Pipeline Name:** xml-output-advanced-document-fragment
- **Description:** DocumentFragment node example: an input field holds a well-formed XML fragment that is parsed and inserted as XML rather than escaped text.
- **Source File:** `03-转换插件/输出类/samples/xml-output-advanced-document-fragment.hpl`

## Transforms

| Name | Type |
|------|------|
| products grid | DataGrid |
| XML Output (Advanced) | AdvancedXMLOutput |

## Hops

| From | To |
|------|----|
| products grid | XML Output (Advanced) |

## Notes

The "extras" field holds a pre-built XML fragment per row. The

DocumentFragment node parses it and inserts the result as real XML

nodes (not escaped text). Multiple top-level fragment elements are

supported - they are written as siblings under the parent <product>.

Output: ${java.io.tmpdir}/xml-output-advanced-document-fragment.xml

---
