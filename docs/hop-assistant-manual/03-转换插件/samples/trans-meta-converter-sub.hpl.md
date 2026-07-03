# Pipeline: pipeline-meta-converter-sub

## Basic Information

- **Pipeline Name:** pipeline-meta-converter-sub
- **Source File:** `03-转换插件/samples/trans-meta-converter-sub.hpl`

## Transforms

| Name | Type |
|------|------|
| Get rows from result | RowsFromResult |
| Write to log | WriteToLog |

## Hops

| From | To |
|------|----|
| Get rows from result | Write to log |
