# Pipeline: 0070-schema-mapping

## Basic Information

- **Pipeline Name:** 0070-schema-mapping
- **Source File:** `03-转换插件/数据验证与质量类/samples/0070-schema-mapping.hpl`

## Transforms

| Name | Type |
|------|------|
| Metadata structure of stream | TransformMetaStructure |
| Schema Mapping | SchemaMapping |
| Verify | Dummy |
| files/static-schema-testfile1.csv | CSVInput |

## Hops

| From | To |
|------|----|
| Metadata structure of stream | Verify |
| files/static-schema-testfile1.csv | Schema Mapping |
| Schema Mapping | Metadata structure of stream |
