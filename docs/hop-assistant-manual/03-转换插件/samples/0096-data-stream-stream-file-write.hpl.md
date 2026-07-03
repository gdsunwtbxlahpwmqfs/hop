# Pipeline: New pipeline

## Basic Information

- **Pipeline Name:** New pipeline
- **Source File:** `03-转换插件/samples/0096-data-stream-stream-file-write.hpl`

## Transforms

| Name | Type |
|------|------|
| golden-data-stream | DataSetInput |
| arrow-stream-file | DataStreamOutput |

## Hops

| From | To |
|------|----|
| golden-data-stream | arrow-stream-file |
