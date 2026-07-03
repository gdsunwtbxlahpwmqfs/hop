# Pipeline: 0011-simple-mapping-validation

## Basic Information

- **Pipeline Name:** 0011-simple-mapping-validation
- **Source File:** `03-转换插件/Beam大数据类/samples/0011-simple-mapping-validation.hpl`

## Transforms

| Name | Type |
|------|------|
| /tmp/0011/simple-mapping*.csv | TextFileInput2 |
| Verify | Dummy |

## Hops

| From | To |
|------|----|
| /tmp/0011/simple-mapping*.csv | Verify |
