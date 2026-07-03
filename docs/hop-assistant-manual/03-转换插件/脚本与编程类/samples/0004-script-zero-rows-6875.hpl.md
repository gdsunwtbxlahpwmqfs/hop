# Pipeline: 0004-script-zero-rows-6875

## Basic Information

- **Pipeline Name:** 0004-script-zero-rows-6875
- **Description:** Verify that a Groovy Script transform with type 'Transform Script' does not fail when the pipeline receives 0 rows (GitHub #6875)
- **Source File:** `03-转换插件/脚本与编程类/samples/0004-script-zero-rows-6875.hpl`

## Transforms

| Name | Type |
|------|------|
| 0 rows | RowGenerator |
| Process data | SuperScript |
| Output | Dummy |

## Hops

| From | To |
|------|----|
| 0 rows | Process data |
| Process data | Output |
