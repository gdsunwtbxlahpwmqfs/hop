# Pipeline: unique-rows-basic

## Basic Information

- **Pipeline Name:** unique-rows-basic
- **Source File:** `03-转换插件/统计与分组类/samples/unique-rows-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Test data | DataGrid |
| Sort by value | SortRows |
| Unique by value | Unique |

## Hops

| From | To |
|------|----|
| Sort by value | Unique by value |
| Test data | Sort by value |

## Notes

Obtain unique values from a data set with duplicate values.

---
