# Pipeline: csvinput-huge-file

## Basic Information

- **Pipeline Name:** csvinput-huge-file
- **Source File:** `03-转换插件/输入类/samples/csvinput-huge-file.hpl`

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

Reads a large csv file and output the results.

Check the 'running in parallel' option and increase 'number of copies' for the CSV Input transform.

First run 'textfileoutput-huge-file.hpl', the file needed for this example is generated there

---
