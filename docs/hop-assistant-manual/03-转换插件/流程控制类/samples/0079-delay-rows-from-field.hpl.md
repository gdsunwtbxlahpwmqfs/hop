# Pipeline: delay-rows-from-field

## Basic Information

- **Pipeline Name:** delay-rows-from-field
- **Source File:** `03-转换插件/流程控制类/samples/0079-delay-rows-from-field.hpl`

## Transforms

| Name | Type |
|------|------|
| add counter | Sequence |
| Delay row | Delay |
| generate 10 rows | RowGenerator |
| 5 -> null | NullIf |
| add time scales | ValueMapper |
| counter = 5? | FilterRows |

## Hops

| From | To |
|------|----|
| generate 10 rows | add counter |
| counter = 5? | Delay row |
| counter = 5? | 5 -> null |
| 5 -> null | Delay row |
| add counter | add time scales |
| add time scales | counter = 5? |
