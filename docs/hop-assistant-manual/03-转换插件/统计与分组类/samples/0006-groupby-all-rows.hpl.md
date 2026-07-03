# Pipeline: 0006-groupby-all-rows

## Basic Information

- **Pipeline Name:** 0006-groupby-all-rows
- **Source File:** `03-转换插件/统计与分组类/samples/0006-groupby-all-rows.hpl`

## Transforms

| Name | Type |
|------|------|
| Group by state | GroupBy |
| Output | Dummy |
| files/customers-100.txt | CSVInput |
| numbers to string | SelectValues |
| sort by state | SortRows |

## Hops

| From | To |
|------|----|
| sort by state | Group by state |
| files/customers-100.txt | sort by state |
| Group by state | numbers to string |
| numbers to string | Output |

## Notes

We convert floating point numbers

to strings to avoid rounding issues

when comparing data.

---
