# Pipeline: 0034-unique-rows-hashset

## Basic Information

- **Pipeline Name:** 0034-unique-rows-hashset
- **Source File:** `03-转换插件/统计与分组类/samples/0034-unique-rows-hashset.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| Duplicate row | Dummy |
| Result | Dummy |
| Unique rows (HashSet) | UniqueRowsByHashSet |
| sort | SortRows |

## Hops

| From | To |
|------|----|
| Data grid | sort |
| sort | Unique rows (HashSet) |
| Unique rows (HashSet) | Result |
| Unique rows (HashSet) | Duplicate row |

## Notes

Warning, row are sorted in case insentive order and dataset is case sensitive order

---
