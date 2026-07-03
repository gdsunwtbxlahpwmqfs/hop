# Pipeline: 0003-input-bigtable-validate

## Basic Information

- **Pipeline Name:** 0003-input-bigtable-validate
- **Source File:** `03-转换插件/云服务类/samples/0003-input-bigtable-validate.hpl`

## Transforms

| Name | Type |
|------|------|
| Validate | Dummy |
| gs://apache-hop-it/output/0003-input-bigtable*.csv | TextFileInput2 |

## Hops

| From | To |
|------|----|
| gs://apache-hop-it/output/0003-input-bigtable*.csv | Validate |
