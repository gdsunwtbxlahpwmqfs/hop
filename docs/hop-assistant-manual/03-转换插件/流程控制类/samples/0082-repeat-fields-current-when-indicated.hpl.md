# Pipeline: 0082-repeat-fields-current-when-indicated

## Basic Information

- **Pipeline Name:** 0082-repeat-fields-current-when-indicated
- **Source File:** `03-转换插件/流程控制类/samples/0082-repeat-fields-current-when-indicated.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| Repeat Fields | RepeatFields |
| grab results | SelectValues |
| result | Dummy |

## Hops

| From | To |
|------|----|
| Data grid | Repeat Fields |
| Repeat Fields | grab results |
| grab results | result |
