# Pipeline: 0057-filter-rows-basic-old

## Basic Information

- **Pipeline Name:** 0057-filter-rows-basic-old
- **Source File:** `03-转换插件/流程控制类/samples/0057-filter-rows-basic-old.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| Dummy (do nothing) | Dummy |
| Filter rows | FilterRows |

## Hops

| From | To |
|------|----|
| Data grid | Filter rows |
| Filter rows | Dummy (do nothing) |

## Notes

Test made with Hop 2.1 to check on regressions

---
