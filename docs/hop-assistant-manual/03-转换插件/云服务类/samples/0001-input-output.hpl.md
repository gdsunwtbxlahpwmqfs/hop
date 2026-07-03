# Pipeline: 0001-input-output

## Basic Information

- **Pipeline Name:** 0001-input-output
- **Source File:** `03-转换插件/云服务类/samples/0001-input-output.hpl`

## Transforms

| Name | Type |
|------|------|
| 0001-input-output | BeamOutput |
| CA, FL, NY | FilterRows |
| customers | BeamInput |

## Hops

| From | To |
|------|----|
| customers | CA, FL, NY |
| CA, FL, NY | 0001-input-output |
