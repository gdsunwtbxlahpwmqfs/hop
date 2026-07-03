# Pipeline: 0007-group-by-validation

## Basic Information

- **Pipeline Name:** 0007-group-by-validation
- **Source File:** `03-转换插件/Beam大数据类/samples/0007-group-by-validation.hpl`

## Transforms

| Name | Type |
|------|------|
| /tmp/0007/group-by*.csv | TextFileInput2 |
| Validate | Dummy |

## Hops

| From | To |
|------|----|
| /tmp/0007/group-by*.csv | Validate |
