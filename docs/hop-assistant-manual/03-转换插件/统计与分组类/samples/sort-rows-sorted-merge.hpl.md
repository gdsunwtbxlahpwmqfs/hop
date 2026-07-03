# Pipeline: sort-rows-sorted-merge

## Basic Information

- **Pipeline Name:** sort-rows-sorted-merge
- **Source File:** `03-转换插件/统计与分组类/samples/sort-rows-sorted-merge.hpl`

## Transforms

| Name | Type |
|------|------|
| Fake book data | Fake |
| Generate 1M rows | RowGenerator |
| Sort by author, title, genre, publisher | SortRows |
| Output | Dummy |
| Sorted merge | SortedMerge |

## Hops

| From | To |
|------|----|
| Generate 1M rows | Fake book data |
| Fake book data | Sort by author, title, genre, publisher |
| Sort by author, title, genre, publisher | Sorted merge |
| Sorted merge | Output |

## Notes

Generate 1M rows of fake book data and sort by author, title, genre, publisher

Increase the number of copies on the Sort Rows transform and add a Sorted Merge transform to speed up the process.

---
