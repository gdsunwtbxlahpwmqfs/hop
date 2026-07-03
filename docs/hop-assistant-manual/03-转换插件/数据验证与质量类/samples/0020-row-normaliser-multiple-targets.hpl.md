# Pipeline: 0020-row-normaliser-multiple-targets

## Basic Information

- **Pipeline Name:** 0020-row-normaliser-multiple-targets
- **Source File:** `03-转换插件/数据验证与质量类/samples/0020-row-normaliser-multiple-targets.hpl`

## Transforms

| Name | Type |
|------|------|
| Normalise country | Normaliser |
| Sample data | DataGrid |
| Verify | Dummy |

## Hops

| From | To |
|------|----|
| Sample data | Normalise country |
| Normalise country | Verify |
