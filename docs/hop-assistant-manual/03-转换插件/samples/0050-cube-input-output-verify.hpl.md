# Pipeline: 0050-cube-input-output-verify

## Basic Information

- **Pipeline Name:** 0050-cube-input-output-verify
- **Source File:** `03-转换插件/samples/0050-cube-input-output-verify.hpl`

## Transforms

| Name | Type |
|------|------|
| Abort | Abort |
| De-serialize from file | CubeInput |
| Merge rows (diff) | MergeRows |
| files/customers-100.txt | CSVInput |
| remove identical | FilterRows |

## Hops

| From | To |
|------|----|
| De-serialize from file | Merge rows (diff) |
| files/customers-100.txt | Merge rows (diff) |
| Merge rows (diff) | remove identical |
| remove identical | Abort |
