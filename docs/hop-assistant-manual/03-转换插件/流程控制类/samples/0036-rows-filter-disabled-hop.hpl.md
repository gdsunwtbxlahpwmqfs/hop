# Pipeline: 0036-rows-filter-disabled-hop

## Basic Information

- **Pipeline Name:** 0036-rows-filter-disabled-hop
- **Source File:** `03-转换插件/流程控制类/samples/0036-rows-filter-disabled-hop.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| FALSE 1 | Dummy |
| FALSE 2 | Dummy |
| FALSE 3 | Dummy |
| FALSE 4 | Dummy |
| Filter <= 50 | FilterRows |
| Filter > 0 | FilterRows |
| Filter >= 10 | FilterRows |
| RowsFilter 4 | FilterRows |
| TRUE 1 | Dummy |
| TRUE 2 | Dummy |
| TRUE 3 | Dummy |
| TRUE 4 | Dummy |

## Hops

| From | To |
|------|----|
| Filter <= 50 | TRUE 2 |
| Filter <= 50 | FALSE 2 |
| Filter >= 10 | TRUE 3 |
| Filter >= 10 | FALSE 3 |
| Data grid | Filter > 0 |
| Filter > 0 | TRUE 1 |
| Filter > 0 | FALSE 1 |
| RowsFilter 4 | TRUE 4 |
| RowsFilter 4 | FALSE 4 |
| TRUE 2 | Filter >= 10 |
| TRUE 1 | Filter <= 50 |
| FALSE 3 | RowsFilter 4 |
