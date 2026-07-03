# Pipeline: delay-rows-default-behavior

## Basic Information

- **Pipeline Name:** delay-rows-default-behavior
- **Source File:** `03-转换插件/流程控制类/samples/0079-delay-rows-default-behavior.hpl`

## Transforms

| Name | Type |
|------|------|
| Delay row | Delay |
| Generate rows | RowGenerator |

## Hops

| From | To |
|------|----|
| Generate rows | Delay row |
