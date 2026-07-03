# Pipeline: 0008-json-normalize-orders

## Basic Information

- **Pipeline Name:** 0008-json-normalize-orders
- **Source File:** `03-转换插件/数据验证与质量类/samples/0008-json-normalize-orders.hpl`

## Transforms

| Name | Type |
|------|------|
| Validate | Dummy |
| JSON normalize orders | JsonNormalizeInput |

## Hops

| From | To |
|------|----|
| JSON normalize orders | Validate |
