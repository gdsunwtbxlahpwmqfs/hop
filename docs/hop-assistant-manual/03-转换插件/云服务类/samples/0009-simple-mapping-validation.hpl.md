# Pipeline: 0009-simple-mapping-validation

## Basic Information

- **Pipeline Name:** 0009-simple-mapping-validation
- **Source File:** `03-转换插件/云服务类/samples/0009-simple-mapping-validation.hpl`

## Transforms

| Name | Type |
|------|------|
| Verify | Dummy |
| output/0009-simple-mapping*.csv | TextFileInput2 |

## Hops

| From | To |
|------|----|
| output/0009-simple-mapping*.csv | Verify |
