# Pipeline: 0009-json-normalize-root-array

## Basic Information

- **Pipeline Name:** 0009-json-normalize-root-array
- **Source File:** `03-转换插件/数据验证与质量类/samples/0009-json-normalize-root-array.hpl`

## Transforms

| Name | Type |
|------|------|
| Validate | Dummy |
| JSON normalize events | JsonNormalizeInput |

## Hops

| From | To |
|------|----|
| JSON normalize events | Validate |
