# Pipeline: append-streams-basic

## Basic Information

- **Pipeline Name:** append-streams-basic
- **Source File:** `03-转换插件/流程控制类/samples/append-streams-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Append streams | Append |
| First Stream | RowGenerator |
| Second Stream | RowGenerator |
| Results | Dummy |

## Hops

| From | To |
|------|----|
| First Stream | Append streams |
| Second Stream | Append streams |
| Append streams | Results |

## Notes

Append two streams

Make sure to keep the row layout of both input streams the same:

--> same fields with the same data types in the same order.

---
