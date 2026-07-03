# Pipeline: json-normalize-root-array

## Basic Information

- **Pipeline Name:** json-normalize-root-array
- **Source File:** `03-转换插件/数据验证与质量类/samples/json-normalize-root-array.hpl`

## Transforms

| Name | Type |
|------|------|
| preview | Dummy |
| read normalized events | JsonNormalizeInput |

## Hops

| From | To |
|------|----|
| read normalized events | preview |

## Notes

The JSON file is a root-level array. Record JsonPath is $[*] so each array element becomes one row. Optional field meta.amount is missing on some rows; ignore missing field paths is enabled.

---
