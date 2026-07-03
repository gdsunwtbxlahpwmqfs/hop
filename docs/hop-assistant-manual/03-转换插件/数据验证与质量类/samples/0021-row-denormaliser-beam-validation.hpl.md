# Pipeline: 0021-row-denormaliser-beam-validation

## Basic Information

- **Pipeline Name:** 0021-row-denormaliser-beam-validation
- **Source File:** `03-转换插件/数据验证与质量类/samples/0021-row-denormaliser-beam-validation.hpl`

## Transforms

| Name | Type |
|------|------|
| /tmp/0021/*.csv | TextFileInput2 |
| Validate | Dummy |

## Hops

| From | To |
|------|----|
| /tmp/0021/*.csv | Validate |
