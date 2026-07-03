# Pipeline: textfileinput-basic

## Basic Information

- **Pipeline Name:** textfileinput-basic
- **Source File:** `03-转换插件/输入类/samples/textfileinput-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Output | Dummy |
| Text file input | TextFileInput2 |

## Hops

| From | To |
|------|----|
| Text file input | Output |

## Notes

Reads a simple csv file and output the results.

First run 'textfileoutput-tiny-file.hpl', the file needed for this example is generated there

---
