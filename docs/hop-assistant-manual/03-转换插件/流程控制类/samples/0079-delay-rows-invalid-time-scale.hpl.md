# Pipeline: delay-rows-invalid-time-scale

## Basic Information

- **Pipeline Name:** delay-rows-invalid-time-scale
- **Source File:** `03-转换插件/流程控制类/samples/0079-delay-rows-invalid-time-scale.hpl`

## Transforms

| Name | Type |
|------|------|
| generate 1 row | RowGenerator |
| Delay row | Delay |

## Hops

| From | To |
|------|----|
| generate 1 row | Delay row |
