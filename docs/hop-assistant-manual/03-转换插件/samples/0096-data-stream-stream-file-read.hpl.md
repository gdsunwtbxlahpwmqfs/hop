# Pipeline: New pipeline

## Basic Information

- **Pipeline Name:** New pipeline
- **Source File:** `03-转换插件/samples/0096-data-stream-stream-file-read.hpl`

## Transforms

| Name | Type |
|------|------|
| arrow-stream-file | DataStreamInput |
| validate | Dummy |

## Hops

| From | To |
|------|----|
| arrow-stream-file | validate |
