# Pipeline: xml-output-advanced-split

## Basic Information

- **Pipeline Name:** xml-output-advanced-split
- **Description:** Roll over to a new output file every N input rows; the transform copy number and a 5-digit split sequence are appended to the filename.
- **Source File:** `03-转换插件/输出类/samples/xml-output-advanced-split.hpl`

## Transforms

| Name | Type |
|------|------|
| events grid | DataGrid |
| XML Output (Advanced) | AdvancedXMLOutput |

## Hops

| From | To |
|------|----|
| events grid | XML Output (Advanced) |

## Notes

Twelve events are emitted as five-row splits: three files in total. Each

file is closed and a fresh XML prolog/root is opened on the next split.

Filename pattern: xml-output-advanced-split_{copyNr}_{00001..} .xml

Output: ${java.io.tmpdir}/xml-output-advanced-split_0_00001.xml (etc.)

---
