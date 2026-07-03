# Pipeline: javascript-sum-over-all-rows

## Basic Information

- **Pipeline Name:** javascript-sum-over-all-rows
- **Source File:** `03-转换插件/脚本与编程类/samples/javascript-sum-over-all-rows.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| JavaScript | ScriptValueMod |

## Hops

| From | To |
|------|----|
| Data grid | JavaScript |

## Notes

This pipeline uses JavaScript to calculate the sum of all values.

3 types of scripts are used: Start, End and Transform.

- Start: set the sum to 0 and decide not to output rows

- Transform: add to the sum

- End: output the sum in a single row

---
