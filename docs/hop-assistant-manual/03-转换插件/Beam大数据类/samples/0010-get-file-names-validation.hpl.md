# Pipeline: 0010-get-file-names-validation

## Basic Information

- **Pipeline Name:** 0010-get-file-names-validation
- **Source File:** `03-转换插件/Beam大数据类/samples/0010-get-file-names-validation.hpl`

## Transforms

| Name | Type |
|------|------|
| ${java.io.tmpdir}/0010/count*.txt | TextFileInput2 |
| Output | Dummy |

## Hops

| From | To |
|------|----|
| ${java.io.tmpdir}/0010/count*.txt | Output |
