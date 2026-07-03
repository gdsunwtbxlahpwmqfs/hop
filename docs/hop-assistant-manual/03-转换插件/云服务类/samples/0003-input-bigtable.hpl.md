# Pipeline: 0003-input-bigtable

## Basic Information

- **Pipeline Name:** 0003-input-bigtable
- **Source File:** `03-转换插件/云服务类/samples/0003-input-bigtable.hpl`

## Transforms

| Name | Type |
|------|------|
| apache-hop-it.customers | BeamBigtableOutput |
| customers | BeamInput |

## Hops

| From | To |
|------|----|
| customers | apache-hop-it.customers |
