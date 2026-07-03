# Pipeline: group-by-median-and-percentile

## Basic Information

- **Pipeline Name:** group-by-median-and-percentile
- **Source File:** `03-转换插件/统计与分组类/samples/group-by-median-and-percentile.hpl`

## Transforms

| Name | Type |
|------|------|
| Add sequence | Sequence |
| Dummy (do nothing) | Dummy |
| Dummy (do nothing) 2 | Dummy |
| Generate rowsx10 | RowGenerator |
| Group by | GroupBy |
| Join rows (cartesian product) | JoinRows |
| Memory group by | MemoryGroupBy |
| Sort rows | SortRows |
| data input | DataGrid |
| random integer | RandomValue |

## Hops

| From | To |
|------|----|
| Generate rowsx10 | Add sequence |
| Add sequence | Join rows (cartesian product) |
| data input | Join rows (cartesian product) |
| Join rows (cartesian product) | random integer |
| random integer | Sort rows |
| Join rows (cartesian product) | Group by |
| Sort rows | Memory group by |
| Group by | Dummy (do nothing) |
| Memory group by | Dummy (do nothing) 2 |

## Notes

These simple median and percentiles are calculated in group by transformation

---
