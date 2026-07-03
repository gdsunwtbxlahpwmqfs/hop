# Pipeline: 0006-multi-part-fixed-name-check

## Basic Information

- **Pipeline Name:** 0006-multi-part-fixed-name-check
- **Source File:** `03-转换插件/输入类/samples/0006-multi-part-fixed-name-check.hpl`

## Transforms

| Name | Type |
|------|------|
| Dummy (do nothing) | Dummy |
| Memory group by | MemoryGroupBy |
| Microsoft Excel input | ExcelInput |

## Hops

| From | To |
|------|----|
| Microsoft Excel input | Memory group by |
| Memory group by | Dummy (do nothing) |
