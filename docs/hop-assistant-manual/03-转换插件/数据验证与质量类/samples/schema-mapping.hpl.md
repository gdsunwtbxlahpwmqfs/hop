# Pipeline: schema-mapping

## Basic Information

- **Pipeline Name:** schema-mapping
- **Source File:** `03-转换插件/数据验证与质量类/samples/schema-mapping.hpl`

## Transforms

| Name | Type |
|------|------|
| CSV file input | CSVInput |
| Dummy (do nothing) | Dummy |
| Schema Mapping | SchemaMapping |

## Hops

| From | To |
|------|----|
| CSV file input | Schema Mapping |
| Schema Mapping | Dummy (do nothing) |

## Notes

Mapps columns to match the structure

defined in the schema 'Test Schema', referencing the

metadata in 'Static Schema Definition'.

---
