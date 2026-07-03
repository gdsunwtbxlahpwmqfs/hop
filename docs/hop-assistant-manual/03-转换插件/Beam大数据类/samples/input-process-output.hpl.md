# Pipeline: input-process-output

## Basic Information

- **Pipeline Name:** input-process-output
- **Source File:** `03-转换插件/Beam大数据类/samples/input-process-output.hpl`

## Transforms

| Name | Type |
|------|------|
| Customers | BeamInput |
| Limit fields, re-order | SelectValues |
| Only CA | FilterRows |
| input-process-output | BeamOutput |

## Hops

| From | To |
|------|----|
| Limit fields, re-order | input-process-output |
| Only CA | Limit fields, re-order |
| Customers | Only CA |
