# Pipeline: 0008-target-output-transform

## Basic Information

- **Pipeline Name:** 0008-target-output-transform
- **Source File:** `03-转换插件/Beam大数据类/samples/0008-target-output-transform.hpl`

## Transforms

| Name | Type |
|------|------|
| /tmp/0008/target-output.csv | BeamOutput |
| CA Only | FilterRows |
| input/customers-noheader-1k.txt | BeamInput |

## Hops

| From | To |
|------|----|
| input/customers-noheader-1k.txt | CA Only |
| CA Only | /tmp/0008/target-output.csv |
