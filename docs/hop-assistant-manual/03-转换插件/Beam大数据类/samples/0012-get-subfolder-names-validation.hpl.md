# Pipeline: 0012-get-subfolder-names-validation

## Basic Information

- **Pipeline Name:** 0012-get-subfolder-names-validation
- **Source File:** `03-转换插件/Beam大数据类/samples/0012-get-subfolder-names-validation.hpl`

## Transforms

| Name | Type |
|------|------|
| /tmp/0012-get-subfolder-names*.csv | TextFileInput2 |
| Verify | Dummy |

## Hops

| From | To |
|------|----|
| /tmp/0012-get-subfolder-names*.csv | Verify |
