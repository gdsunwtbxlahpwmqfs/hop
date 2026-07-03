# Pipeline: Add Sequence - Add a cyclic number

## Basic Information

- **Pipeline Name:** Add Sequence - Add a cyclic number
- **Source File:** `03-转换插件/计算与字段操作类/samples/add-sequence-cyclic-id.hpl`

## Transforms

| Name | Type |
|------|------|
| 100 rows | RowGenerator |
| Add sequence | Sequence |

## Hops

| From | To |
|------|----|
| 100 rows | Add sequence |

## Notes

In this case the Add Sequence transform adds a field containing the value 1 to 10

After 10 it starts to count at 1 again

---
