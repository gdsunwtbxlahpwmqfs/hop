# Pipeline: 0041-json-enhance-output

## Basic Information

- **Pipeline Name:** 0041-json-enhance-output
- **Source File:** `03-转换插件/映射与子管道类/samples/0041-json-enhance-output.hpl`

## Transforms

| Name | Type |
|------|------|
| ${java.io.tmpdir}/json-enhanced-output.hpl | MetaInject |
| Data Set Input | DataSetInput |
| golden-json-enhanced-output-fields | DataSetInput |
| golden-json-enhanced-output-keys | DataSetInput |

## Hops

| From | To |
|------|----|
| golden-json-enhanced-output-keys | ${java.io.tmpdir}/json-enhanced-output.hpl |
| Data Set Input | ${java.io.tmpdir}/json-enhanced-output.hpl |
| golden-json-enhanced-output-fields | ${java.io.tmpdir}/json-enhanced-output.hpl |
