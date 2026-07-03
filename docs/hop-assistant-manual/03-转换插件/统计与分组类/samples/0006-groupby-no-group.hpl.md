# Pipeline: 0006-groupby-no-group

## Basic Information

- **Pipeline Name:** 0006-groupby-no-group
- **Source File:** `03-转换插件/统计与分组类/samples/0006-groupby-no-group.hpl`

## Transforms

| Name | Type |
|------|------|
| Aggregate all rows | GroupBy |
| Output | Dummy |
| files/customers-100.txt | CSVInput |
| numbers to string | SelectValues |

## Hops

| From | To |
|------|----|
| Aggregate all rows | numbers to string |
| numbers to string | Output |
| files/customers-100.txt | Aggregate all rows |

## Notes

We convert floating point numbers

to strings to avoid rounding issues

when comparing data.

---
