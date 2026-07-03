# Pipeline: 0039-csv-input-schema-definition

## Basic Information

- **Pipeline Name:** 0039-csv-input-schema-definition
- **Source File:** `03-转换插件/输入类/samples/0039-csv-input-schema-definition.hpl`

## Transforms

| Name | Type |
|------|------|
| Metadata structure of stream | TransformMetaStructure |
| Verify | Dummy |
| files/static-schema-testfile1.csv | CSVInput |

## Hops

| From | To |
|------|----|
| files/static-schema-testfile1.csv | Metadata structure of stream |
| Metadata structure of stream | Verify |
