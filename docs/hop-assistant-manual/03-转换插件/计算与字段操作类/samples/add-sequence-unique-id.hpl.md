# Pipeline: Add Sequence - Add a unique ID

## Basic Information

- **Pipeline Name:** Add Sequence - Add a unique ID
- **Source File:** `03-转换插件/计算与字段操作类/samples/add-sequence-unique-id.hpl`

## Transforms

| Name | Type |
|------|------|
| 100 rows | RowGenerator |
| Add sequence | Sequence |
| Log values | WriteToLog |

## Hops

| From | To |
|------|----|
| 100 rows | Add sequence |
| Add sequence | Log values |

## Notes

In this case the Add Sequence transform creates an ID from 1 to 100

---
