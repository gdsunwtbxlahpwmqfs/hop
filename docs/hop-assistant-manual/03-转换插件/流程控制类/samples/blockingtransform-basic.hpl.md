# Pipeline: blockingtransform-basic

## Basic Information

- **Pipeline Name:** blockingtransform-basic
- **Source File:** `03-转换插件/流程控制类/samples/blockingtransform-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Generate 100k rows | RowGenerator |
| Generate random UUID | RandomValue |
| Blocking transform | BlockingTransform |
| Output | Dummy |

## Hops

| From | To |
|------|----|
| Generate 100k rows | Generate random UUID |
| Generate random UUID | Blocking transform |
| Blocking transform | Output |

## Notes

Generate 100k random UUIDs, block until done

---
