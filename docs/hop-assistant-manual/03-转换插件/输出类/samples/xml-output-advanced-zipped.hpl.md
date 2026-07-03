# Pipeline: xml-output-advanced-zipped

## Basic Information

- **Pipeline Name:** xml-output-advanced-zipped
- **Description:** Wraps the output XML in a zip archive and stamps a custom date/time pattern into the archive name. The sibling XSD is written next to the .zip, not inside it.
- **Source File:** `03-转换插件/输出类/samples/xml-output-advanced-zipped.hpl`

## Transforms

| Name | Type |
|------|------|
| transactions grid | DataGrid |
| XML Output (Advanced) | AdvancedXMLOutput |

## Hops

| From | To |
|------|----|
| transactions grid | XML Output (Advanced) |

## Notes

The "specify format" toggle replaces the date/time toggles with a free-form

SimpleDateFormat pattern. Combined with zipped output, the archive lands at:

${java.io.tmpdir}/transactions-yyyy-MM-dd_HH-mm-ss.zip

Inside the zip, one entry "transactions-...xml" holds the data. The sibling

.xsd is written next to the .zip, never inside it.

---
