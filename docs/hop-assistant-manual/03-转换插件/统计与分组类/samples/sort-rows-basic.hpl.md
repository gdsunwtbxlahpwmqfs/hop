# Pipeline: sort-rows-basic

## Basic Information

- **Pipeline Name:** sort-rows-basic
- **Source File:** `03-转换插件/统计与分组类/samples/sort-rows-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Fake book data | Fake |
| Generate 100 rows | RowGenerator |
| Sort by author, title, genre, publisher | SortRows |
| Output | Dummy |

## Hops

| From | To |
|------|----|
| Generate 100 rows | Fake book data |
| Fake book data | Sort by author, title, genre, publisher |
| Sort by author, title, genre, publisher | Output |

## Notes

Generate 100 rows of fake book data and sort by author, title, genre, publisher

---
