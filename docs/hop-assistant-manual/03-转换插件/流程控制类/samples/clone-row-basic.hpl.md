# Pipeline: Clone row

## Basic Information

- **Pipeline Name:** Clone row
- **Source File:** `03-转换插件/流程控制类/samples/clone-row-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| add unique id | Sequence |
| duplicate rows | CloneRow |
| 5 static rows | RowGenerator |
| Wait 1s | Delay |
| add system date | SystemInfo |

## Hops

| From | To |
|------|----|
| add unique id | duplicate rows |
| 5 static rows | Wait 1s |
| Wait 1s | add system date |
| add system date | add unique id |

## Notes

Duplicate input rows with clone rows.

The 'Flagfield' output field contains:

>> N  : it’s the original row

>> Y  : cloned row, a copy of the original row

---
