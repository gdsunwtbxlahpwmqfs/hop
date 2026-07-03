# Pipeline: getfilenames-read-sample-transform-files

## Basic Information

- **Pipeline Name:** getfilenames-read-sample-transform-files
- **Source File:** `03-转换插件/输入类/samples/getfilenames-read-sample-transform-files.hpl`

## Transforms

| Name | Type |
|------|------|
| Get transform sample file names | GetFileNames |
| Output | Dummy |

## Hops

| From | To |
|------|----|
| Get transform sample file names | Output |

## Notes

Reads all pipelines from the current ${PROJECT_HOME}

*) '.*.hpl' regular expression to include all pipeline files.

*) '.*merge.*.hpl' regular expression to exclude the 'merge join' sample

*) these files are required

*) sub folders are included

---
