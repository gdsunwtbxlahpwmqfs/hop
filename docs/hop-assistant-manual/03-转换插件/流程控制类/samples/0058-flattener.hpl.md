# Pipeline: 0058-flattener

## Basic Information

- **Pipeline Name:** 0058-flattener
- **Source File:** `03-转换插件/流程控制类/samples/0058-flattener.hpl`

## Transforms

| Name | Type |
|------|------|
| Flatten data | Flattener |
| Verify | Dummy |
| sample data | DataGrid |

## Hops

| From | To |
|------|----|
| sample data | Flatten data |
| Flatten data | Verify |
