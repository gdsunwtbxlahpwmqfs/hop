# Pipeline: 0034-unique-rows

## Basic Information

- **Pipeline Name:** 0034-unique-rows
- **Source File:** `03-转换插件/统计与分组类/samples/0034-unique-rows.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| Duplicate row | Dummy |
| Result | Dummy |
| Unique rows | Unique |
| sort | SortRows |

## Hops

| From | To |
|------|----|
| Data grid | sort |
| sort | Unique rows |
| Unique rows | Result |
| Unique rows | Duplicate row |

## Notes

Warning, row are sorted in case insentive order and dataset is case sensitive order

---
