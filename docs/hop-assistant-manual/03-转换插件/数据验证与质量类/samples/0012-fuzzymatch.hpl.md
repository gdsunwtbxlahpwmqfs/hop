# Pipeline: 0012-fuzzymatch

## Basic Information

- **Pipeline Name:** 0012-fuzzymatch
- **Source File:** `03-转换插件/数据验证与质量类/samples/0012-fuzzymatch.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| Fuzzy match | FuzzyMatch |
| Output | Dummy |
| files/customers-100.txt | CSVInput |

## Hops

| From | To |
|------|----|
| Fuzzy match | Output |
| files/customers-100.txt | Fuzzy match |
| Data grid | Fuzzy match |
