# Pipeline: 0047-multiway-merge-join

## Basic Information

- **Pipeline Name:** 0047-multiway-merge-join
- **Source File:** `03-转换插件/查找与连接类/samples/0047-multiway-merge-join.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid - Airport | DataGrid |
| Data grid - Country | DataGrid |
| Dummy (do nothing) | Dummy |
| Multiway merge join | MultiwayMergeJoin |
| Sort rows | SortRows |
| Sort rows Country | SortRows |

## Hops

| From | To |
|------|----|
| Data grid - Airport | Sort rows |
| Data grid - Country | Sort rows Country |
| Sort rows Country | Multiway merge join |
| Multiway merge join | Dummy (do nothing) |
| Sort rows | Multiway merge join |

## Notes

Work with FULL OUTER JOIN but not with INNER JOIN

---
