# Pipeline: 0001-input-output-validation

## Basic Information

- **Pipeline Name:** 0001-input-output-validation
- **Source File:** `03-转换插件/云服务类/samples/0001-input-output-validation.hpl`

## Transforms

| Name | Type |
|------|------|
| Group by | GroupBy |
| Readback of output | TextFileInput2 |
| Verify | Dummy |
| gs://apache-hop-it/output/0001-*.csv | GetFileNames |

## Hops

| From | To |
|------|----|
| Readback of output | Group by |
| Group by | Verify |
| gs://apache-hop-it/output/0001-*.csv | Readback of output |
