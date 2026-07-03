# Pipeline: csvinput-basic

## Basic Information

- **Pipeline Name:** csvinput-basic
- **Source File:** `03-转换插件/输入类/samples/csvinput-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Output | Dummy |
| Read Test File | CSVInput |

## Hops

| From | To |
|------|----|
| Read Test File | Output |

## Notes

Reads a simple csv file and output the results.

First run 'textfileoutput-tiny-file.hpl', the file needed for this example is generated there

---
