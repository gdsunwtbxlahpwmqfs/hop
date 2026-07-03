# Pipeline: 0020-row-normaliser-single-target

## Basic Information

- **Pipeline Name:** 0020-row-normaliser-single-target
- **Source File:** `03-转换插件/数据验证与质量类/samples/0020-row-normaliser-single-target.hpl`

## Transforms

| Name | Type |
|------|------|
| Normalise fieldA-D | Normaliser |
| Sample data | DataGrid |
| Verify | Dummy |

## Hops

| From | To |
|------|----|
| Sample data | Normalise fieldA-D |
| Normalise fieldA-D | Verify |
