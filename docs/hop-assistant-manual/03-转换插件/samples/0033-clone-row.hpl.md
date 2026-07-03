# Pipeline: 0033-clone-row

## Basic Information

- **Pipeline Name:** 0033-clone-row
- **Source File:** `03-转换插件/samples/0033-clone-row.hpl`

## Transforms

| Name | Type |
|------|------|
| Clone row nr constant | CloneRow |
| Clone row nr in field | CloneRow |
| Data grid | DataGrid |
| Result constant | Dummy |
| Result dynamic | Dummy |

## Hops

| From | To |
|------|----|
| Data grid | Clone row nr constant |
| Data grid | Clone row nr in field |
| Clone row nr constant | Result constant |
| Clone row nr in field | Result dynamic |
