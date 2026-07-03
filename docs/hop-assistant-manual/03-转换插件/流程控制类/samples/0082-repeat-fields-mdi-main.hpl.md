# Pipeline: 0082-repeat-fields-mdi-main

## Basic Information

- **Pipeline Name:** 0082-repeat-fields-mdi-main
- **Source File:** `03-转换插件/流程控制类/samples/0082-repeat-fields-mdi-main.hpl`

## Transforms

| Name | Type |
|------|------|
| ETL metadata injection | MetaInject |
| Results | Dummy |
| groups | DataGrid |
| repeats | DataGrid |

## Hops

| From | To |
|------|----|
| groups | ETL metadata injection |
| repeats | ETL metadata injection |
| ETL metadata injection | Results |
