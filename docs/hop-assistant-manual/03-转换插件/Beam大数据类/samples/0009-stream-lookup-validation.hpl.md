# Pipeline: 0009-stream-lookup-validation

## Basic Information

- **Pipeline Name:** 0009-stream-lookup-validation
- **Source File:** `03-转换插件/Beam大数据类/samples/0009-stream-lookup-validation.hpl`

## Transforms

| Name | Type |
|------|------|
| /tmp/0009/*.csv | TextFileInput2 |
| Validate | Dummy |

## Hops

| From | To |
|------|----|
| /tmp/0009/*.csv | Validate |
