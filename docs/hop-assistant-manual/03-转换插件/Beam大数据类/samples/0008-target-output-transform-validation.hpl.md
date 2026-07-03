# Pipeline: 0008-target-output-transform-validation

## Basic Information

- **Pipeline Name:** 0008-target-output-transform-validation
- **Source File:** `03-转换插件/Beam大数据类/samples/0008-target-output-transform-validation.hpl`

## Transforms

| Name | Type |
|------|------|
| /tmp/0008/target-output*.csv | TextFileInput2 |
| Validate | Dummy |

## Hops

| From | To |
|------|----|
| /tmp/0008/target-output*.csv | Validate |
