# Pipeline: getfilenames-from-field

## Basic Information

- **Pipeline Name:** getfilenames-from-field
- **Source File:** `03-转换插件/输入类/samples/getfilenames-from-field.hpl`

## Transforms

| Name | Type |
|------|------|
| Get file names | GetFileNames |
| Get ${PROJECT_HOME} | GetVariable |
| Wildcards | DataGrid |
| Output | Dummy |

## Hops

| From | To |
|------|----|
| Wildcards | Get ${PROJECT_HOME} |
| Get ${PROJECT_HOME} | Get file names |
| Get file names | Output |

## Notes

This is similar as getfilenames-read-transform-sample-files.hpl, but the information is provided through fields

Reads all pipelines from the current ${PROJECT_HOME}

*) '.*.hpl' regular expression to include all pipeline files.

*) '.*merge.*.hpl' regular expression to exclude the 'merge join' sample

*) sub folders are included

---
