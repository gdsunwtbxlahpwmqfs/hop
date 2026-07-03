# Pipeline: json-normalize-orders

## Basic Information

- **Pipeline Name:** json-normalize-orders
- **Source File:** `03-转换插件/数据验证与质量类/samples/json-normalize-orders.hpl`

## Transforms

| Name | Type |
|------|------|
| preview | Dummy |
| read normalized orders | JsonNormalizeInput |

## Hops

| From | To |
|------|----|
| read normalized orders | preview |

## Notes

JSON normalize input: one row per element of $.orders[*], nested objects flattened with dot paths (pandas json_normalize style). Data file lives in ../data/json-normalize-orders.json next to this pipeline.

---
