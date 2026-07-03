# Pipeline: New pipeline

## Basic Information

- **Pipeline Name:** New pipeline
- **Source File:** `03-转换插件/samples/0095-data-stream-random-access-file-write.hpl`

## Transforms

| Name | Type |
|------|------|
| golden-data-stream | DataSetInput |
| arrow-random-access-file | DataStreamOutput |

## Hops

| From | To |
|------|----|
| golden-data-stream | arrow-random-access-file |
