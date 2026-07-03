# Pipeline: add-value-fields-changing-sequence

## Basic Information

- **Pipeline Name:** add-value-fields-changing-sequence
- **Source File:** `03-转换插件/计算与字段操作类/samples/add-value-fields-changing-sequence.hpl`

## Transforms

| Name | Type |
|------|------|
| add change_counter | FieldsChangeSequence |
| add counter | Sequence |
| counter/10 | Calculator |
| generate 100 rows | RowGenerator |

## Hops

| From | To |
|------|----|
| generate 100 rows | add counter |
| add counter | counter/10 |
| counter/10 | add change_counter |

## Notes

reset change_counter for each multiple of 10

---
