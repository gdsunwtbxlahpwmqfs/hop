# Pipeline: join-rows

## Basic Information

- **Pipeline Name:** join-rows
- **Source File:** `03-转换插件/查找与连接类/samples/join-rows.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| Data grid 2 | DataGrid |
| Data grid 2 2 | DataGrid |
| Data grid 3 | DataGrid |
| Join | JoinRows |
| Join rows with condition | JoinRows |

## Hops

| From | To |
|------|----|
| Data grid | Join |
| Data grid 2 | Join |
| Data grid 3 | Join rows with condition |
| Data grid 2 2 | Join rows with condition |

## Notes

### Note 1

Example 1:

"Generates the Cartesian product of the values a, b, c with a, b, c."

---

### Note 2

Example 2:

"Generates the Cartesian product of the values a, b, c with a, b, c, applying the condition field1 = field2 and field1 = a."

---
