# Pipeline: fuzzy-match-rename

## Basic Information

- **Pipeline Name:** fuzzy-match-rename
- **Source File:** `03-转换插件/查找与连接类/samples/fuzzy-match-rename.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| Data grid 2 | DataGrid |
| Fuzzy match | FuzzyMatch |
| Write to log | WriteToLog |

## Hops

| From | To |
|------|----|
| Data grid | Fuzzy match |
| Fuzzy match | Write to log |
| Data grid 2 | Fuzzy match |
