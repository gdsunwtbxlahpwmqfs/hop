# Pipeline: 0007-partitioned-unit-test

## Basic Information

- **Pipeline Name:** 0007-partitioned-unit-test
- **Source File:** `03-转换插件/流程控制类/samples/0007-partitioned-unit-test.hpl`

## Transforms

| Name | Type |
|------|------|
| input-data | DataGrid |
| process | Dummy |
| input-data 2 | DataGrid |
| process 2 | Dummy |

## Hops

| From | To |
|------|----|
| input-data | process |
| input-data 2 | process 2 |
