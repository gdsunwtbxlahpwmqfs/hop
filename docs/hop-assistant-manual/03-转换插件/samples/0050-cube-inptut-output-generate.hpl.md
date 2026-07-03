# Pipeline: 0050-cube-inptut-output-generate

## Basic Information

- **Pipeline Name:** 0050-cube-inptut-output-generate
- **Source File:** `03-转换插件/samples/0050-cube-inptut-output-generate.hpl`

## Transforms

| Name | Type |
|------|------|
| files/customers-100.txt | CSVInput |
| /tmp/file-0050.cube | CubeOutput |

## Hops

| From | To |
|------|----|
| files/customers-100.txt | /tmp/file-0050.cube |
