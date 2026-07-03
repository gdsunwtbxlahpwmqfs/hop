# Pipeline: 0094-xml-output

## Basic Information

- **Pipeline Name:** 0094-xml-output
- **Source File:** `03-转换插件/输出类/samples/0094-xml-output.hpl`

## Transforms

| Name | Type |
|------|------|
| ${java.io.tmpdir}/xml-output-file.xml | XMLOutput |
| test-data | DataGrid |

## Hops

| From | To |
|------|----|
| test-data | ${java.io.tmpdir}/xml-output-file.xml |
