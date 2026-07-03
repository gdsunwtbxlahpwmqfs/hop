# Pipeline: 0003-json-test-block-size

## Basic Information

- **Pipeline Name:** 0003-json-test-block-size
- **Source File:** `03-转换插件/输入类/samples/0003-json-test-block-size.hpl`

## Transforms

| Name | Type |
|------|------|
| JSON input | JsonInput |
| Set JSON_ROWCOUNT | SetVariable |
| count rows | GroupBy |

## Hops

| From | To |
|------|----|
| JSON input | count rows |
| count rows | Set JSON_ROWCOUNT |
