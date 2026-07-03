# Pipeline: 0057-filter-rows-targets

## Basic Information

- **Pipeline Name:** 0057-filter-rows-targets
- **Source File:** `03-转换插件/流程控制类/samples/0057-filter-rows-targets.hpl`

## Transforms

| Name | Type |
|------|------|
| Abort | Abort |
| FL and housenr>100 | FilterRows |
| False | Dummy |
| True | Dummy |
| count | MemoryGroupBy |
| count <> 98 | FilterRows |
| files/customers-100.txt | CSVInput |

## Hops

| From | To |
|------|----|
| files/customers-100.txt | FL and housenr>100 |
| FL and housenr>100 | True |
| FL and housenr>100 | False |
| False | count |
| count | count <> 98 |
| count <> 98 | Abort |
