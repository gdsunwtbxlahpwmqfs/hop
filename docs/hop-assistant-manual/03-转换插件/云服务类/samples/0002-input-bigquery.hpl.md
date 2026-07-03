# Pipeline: 0002-input-bigquery

## Basic Information

- **Pipeline Name:** 0002-input-bigquery
- **Source File:** `03-转换插件/云服务类/samples/0002-input-bigquery.hpl`

## Transforms

| Name | Type |
|------|------|
| apache-hop.it.customers | BeamBQOutput |
| customers | BeamInput |

## Hops

| From | To |
|------|----|
| customers | apache-hop.it.customers |

## Notes

This truncates table 'customers'

---
