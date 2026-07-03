# Pipeline: 0004-repeat_pipeline

## Basic Information

- **Pipeline Name:** 0004-repeat_pipeline
- **Source File:** `03-转换插件/samples/0004-repeat_pipeline.hpl`

## Transforms

| Name | Type |
|------|------|
| Generate rows | RowGenerator |
| Get variables | GetVariable |
| Set variables | SetVariable |
| Set variables 2 | SetVariable |
| Write to log | WriteToLog |
| counter + 1 | Calculator |
| counter >5 | FilterRows |

## Hops

| From | To |
|------|----|
| Generate rows | Get variables |
| counter + 1 | counter >5 |
| counter >5 | Set variables |
| Get variables | Write to log |
| Write to log | counter + 1 |
| counter >5 | Set variables 2 |
