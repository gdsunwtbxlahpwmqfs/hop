# Pipeline: json-output-generate-nested-structure

## Basic Information

- **Pipeline Name:** json-output-generate-nested-structure
- **Source File:** `03-转换插件/输出类/samples/json-output-generate-nested-structure.hpl`

## Transforms

| Name | Type |
|------|------|
| Final Complex Data structure | EnhancedJsonOutput |
| Input data | DataGrid |
| Intermediate Structures | EnhancedJsonOutput |
| Sort the fields | SortRows |

## Hops

| From | To |
|------|----|
| Input data | Sort the fields |
| Sort the fields | Intermediate Structures |
| Intermediate Structures | Final Complex Data structure |

## Notes

This sample gets a table of values and generates a complex nested JSON structure by looping over a predefined key.

The second 'Enhanced JSON Output' transform uses a JSON fragment

from the first 'Enhanced JSON Output' transform and uses it as value of an attribute of the resulting final

JSON value.

---
