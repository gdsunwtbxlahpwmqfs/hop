# Pipeline: 0057-filter-rows-basic

## Basic Information

- **Pipeline Name:** 0057-filter-rows-basic
- **Source File:** `03-转换插件/流程控制类/samples/0057-filter-rows-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| FL and housenr>100 | FilterRows |
| Verify | Dummy |
| files/customers-100.txt | CSVInput |

## Hops

| From | To |
|------|----|
| files/customers-100.txt | FL and housenr>100 |
| FL and housenr>100 | Verify |
