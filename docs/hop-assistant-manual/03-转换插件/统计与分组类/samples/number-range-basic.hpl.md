# Pipeline: number-range-basic

## Basic Information

- **Pipeline Name:** number-range-basic
- **Source File:** `03-转换插件/统计与分组类/samples/number-range-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Test Data | DataGrid |
| Age Groups | NumberRange |
| Output | Dummy |

## Hops

| From | To |
|------|----|
| Test Data | Age Groups |
| Age Groups | Output |

## Notes

Specify number ranges and check in which range each value for a field belongs

---
