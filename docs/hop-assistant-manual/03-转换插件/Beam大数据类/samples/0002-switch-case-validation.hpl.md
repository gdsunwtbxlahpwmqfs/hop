# Pipeline: 0002-switch-case-validation

## Basic Information

- **Pipeline Name:** 0002-switch-case-validation
- **Source File:** `03-转换插件/Beam大数据类/samples/0002-switch-case-validation.hpl`

## Transforms

| Name | Type |
|------|------|
| /tmp/0002-switch-case/*.csv | TextFileInput2 |
| Validate | Dummy |

## Hops

| From | To |
|------|----|
| /tmp/0002-switch-case/*.csv | Validate |
