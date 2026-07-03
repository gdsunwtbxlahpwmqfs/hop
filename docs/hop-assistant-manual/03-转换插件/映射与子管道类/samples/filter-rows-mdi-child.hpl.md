# Pipeline: 0022-filter-rows-child

## Basic Information

- **Pipeline Name:** 0022-filter-rows-child
- **Source File:** `03-转换插件/映射与子管道类/samples/filter-rows-mdi-child.hpl`

## Transforms

| Name | Type |
|------|------|
| FL and housenr>100 | FilterRows |
| False | Dummy |
| True | Dummy |
| files/customers-100.txt | CSVInput |

## Hops

| From | To |
|------|----|
| files/customers-100.txt | FL and housenr>100 |
| FL and housenr>100 | True |
| FL and housenr>100 | False |
