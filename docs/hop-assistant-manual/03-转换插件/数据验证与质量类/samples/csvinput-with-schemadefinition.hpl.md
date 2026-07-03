# Pipeline: csvinput-with-schemadefinition

## Basic Information

- **Pipeline Name:** csvinput-with-schemadefinition
- **Source File:** `03-转换插件/数据验证与质量类/samples/csvinput-with-schemadefinition.hpl`

## Transforms

| Name | Type |
|------|------|
| Output | Dummy |
| Read Test File | CSVInput |

## Hops

| From | To |
|------|----|
| Read Test File | Output |

## Notes

Loads a CSV file and adapts its columns to match the structure

defined in the schema 'Test Schema', referencing the

metadata in 'Static Schema Definition'.

---
