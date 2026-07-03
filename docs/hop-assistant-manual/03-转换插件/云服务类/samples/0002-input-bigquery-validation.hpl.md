# Pipeline: 0002-input-bigquery-validation

## Basic Information

- **Pipeline Name:** 0002-input-bigquery-validation
- **Source File:** `03-转换插件/云服务类/samples/0002-input-bigquery-validation.hpl`

## Transforms

| Name | Type |
|------|------|
| Validate | Dummy |
| apache-hop.it.customers | BeamBQInput |

## Hops

| From | To |
|------|----|
| apache-hop.it.customers | Validate |
