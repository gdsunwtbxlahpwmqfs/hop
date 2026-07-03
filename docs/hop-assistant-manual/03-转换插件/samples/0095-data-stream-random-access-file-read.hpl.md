# Pipeline: New pipeline

## Basic Information

- **Pipeline Name:** New pipeline
- **Source File:** `03-转换插件/samples/0095-data-stream-random-access-file-read.hpl`

## Transforms

| Name | Type |
|------|------|
| arrow-random-access-file | DataStreamInput |
| validate | Dummy |

## Hops

| From | To |
|------|----|
| arrow-random-access-file | validate |
