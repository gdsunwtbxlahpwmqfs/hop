# Pipeline: 0081-repeat-fields-previous

## Basic Information

- **Pipeline Name:** 0081-repeat-fields-previous
- **Source File:** `03-转换插件/流程控制类/samples/0082-repeat-fields-previous.hpl`

## Transforms

| Name | Type |
|------|------|
| Repeat Fields | RepeatFields |
| Result | Dummy |
| source | DataGrid |

## Hops

| From | To |
|------|----|
| source | Repeat Fields |
| Repeat Fields | Result |
