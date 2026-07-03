# Pipeline: 0081-repeat-fields-previous-when-null

## Basic Information

- **Pipeline Name:** 0081-repeat-fields-previous-when-null
- **Source File:** `03-转换插件/流程控制类/samples/0082-repeat-fields-previous-when-null.hpl`

## Transforms

| Name | Type |
|------|------|
| Repeat Fields | RepeatFields |
| Result | Dummy |
| source | DataGrid |

## Hops

| From | To |
|------|----|
| Repeat Fields | Result |
| source | Repeat Fields |
