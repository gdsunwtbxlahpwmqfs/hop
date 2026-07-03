# Pipeline: repeating-work

## Basic Information

- **Pipeline Name:** repeating-work
- **Source File:** `03-转换插件/映射与子管道类/samples/0002-repeating-work.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| NUMBER |  | Just a number to log |

## Transforms

| Name | Type |
|------|------|
| 1 row | RowGenerator |
| Write ${NUMBER} to the log | WriteToLog |

## Hops

| From | To |
|------|----|
| 1 row | Write ${NUMBER} to the log |
