# Pipeline: xml-output-advanced-compact

## Basic Information

- **Pipeline Name:** xml-output-advanced-compact
- **Description:** Compact output combined with force-create, default values, and the create-attribute-if-null / create-attribute-if-unmapped flags.
- **Source File:** `03-转换插件/输出类/samples/xml-output-advanced-compact.hpl`

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

This sample exercises the null-handling and unmapped flags:

* @currency has no mapped field at all but is still emitted, with its

default_value, because Create attribute if unmapped is on.

* @discontinued is force-created with default_value="false" when

its mapped field is null.

* <note> is force-created with a default text value.

* Compact mode strips whitespace between elements.

Output: ${java.io.tmpdir}/xml-output-advanced-compact.xml

---
