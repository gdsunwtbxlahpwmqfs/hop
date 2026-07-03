# Pipeline: 0018-combination-lookup-verify

## Basic Information

- **Pipeline Name:** 0018-combination-lookup-verify
- **Source File:** `03-转换插件/映射与子管道类/samples/0018-combination-lookup-verify.hpl`

## Transforms

| Name | Type |
|------|------|
| Validate | Dummy |
| h2.dim_junk | TableInput |

## Hops

| From | To |
|------|----|
| h2.dim_junk | Validate |
