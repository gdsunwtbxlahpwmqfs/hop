# Pipeline: analyticquery-grouped

## Basic Information

- **Pipeline Name:** analyticquery-grouped
- **Source File:** `03-转换插件/统计与分组类/samples/analyticquery-grouped.hpl`

## Transforms

| Name | Type |
|------|------|
| Test Data | DataGrid |
| Previous and next rows | AnalyticQuery |
| Output | Dummy |

## Hops

| From | To |
|------|----|
| Test Data | Previous and next rows |
| Previous and next rows | Output |

## Notes

Fetches the previous and next description from a set of rows, grouped by id

---
