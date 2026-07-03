# Pipeline: analyticquery-basic

## Basic Information

- **Pipeline Name:** analyticquery-basic
- **Source File:** `03-转换插件/统计与分组类/samples/analyticquery-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Output | Dummy |
| Previous and next rows | AnalyticQuery |
| Test Data | DataGrid |

## Hops

| From | To |
|------|----|
| Test Data | Previous and next rows |
| Previous and next rows | Output |

## Notes

Fetches the previous and next description from a set of rows without grouping

---
