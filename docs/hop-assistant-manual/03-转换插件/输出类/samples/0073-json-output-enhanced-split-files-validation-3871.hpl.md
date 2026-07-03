# Pipeline: 0073-json-output-enhanced-split-files-validation-3871

## Basic Information

- **Pipeline Name:** 0073-json-output-enhanced-split-files-validation-3871
- **Source File:** `03-转换插件/输出类/samples/0073-json-output-enhanced-split-files-validation-3871.hpl`

## Transforms

| Name | Type |
|------|------|
| 10 files? | FilterRows |
| 100k lines? | FilterRows |
| D1 | Dummy |
| D2 | Dummy |
| Identify last row in a stream | DetectLastRow |
| JSON input | JsonInput |
| abort nb_files | Abort |
| abort row count | Abort |
| filename count | MemoryGroupBy |
| keep filename | SelectValues |
| keep last row | FilterRows |

## Hops

| From | To |
|------|----|
| 10 files? | D2 |
| 10 files? | abort nb_files |
| 100k lines? | D1 |
| 100k lines? | abort row count |
| Identify last row in a stream | keep last row |
| JSON input | Identify last row in a stream |
| JSON input | keep filename |
| filename count | 10 files? |
| keep filename | filename count |
| keep last row | 100k lines? |
