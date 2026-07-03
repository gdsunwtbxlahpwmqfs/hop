# Pipeline: filter-rows-basic

## Basic Information

- **Pipeline Name:** filter-rows-basic
- **Source File:** `03-转换插件/流程控制类/samples/filter-rows-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| False Ouput | Dummy |
| Filter rows - True False | FilterRows |
| Main Output | Dummy |
| Filter Rows - Main Output | FilterRows |
| Test Data | DataGrid |
| True Ouput | Dummy |

## Hops

| From | To |
|------|----|
| Test Data | Filter rows - True False |
| Filter rows - True False | True Ouput |
| Filter rows - True False | False Ouput |
| Test Data | Filter Rows - Main Output |
| Filter Rows - Main Output | Main Output |

## Notes

Filters an incoming data set based on a set of conditions

*) Filter Rows - True False: continue in different streams for data that meets or doesn't meet the filter condition

*) Filter Rows - Main Output: continue with data that meets the filter condition only, data that doesn't meet the filter condition is ignored.

---
