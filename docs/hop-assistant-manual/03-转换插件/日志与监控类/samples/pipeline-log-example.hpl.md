# Pipeline: pipeline-log-example

## Basic Information

- **Pipeline Name:** pipeline-log-example
- **Source File:** `03-转换插件/日志与监控类/samples/pipeline-log-example.hpl`

## Transforms

| Name | Type |
|------|------|
| Pipeline Logging | PipelineLogging |
| get some stats | MemoryGroupBy |
| pipeline logs | SelectValues |
| pipeline-log-transforms.csv out | TextFileOutput |
| pipeline-log.csv out | TextFileOutput |
| r/s in | Calculator |
| transform LU | StreamLookup |
| transform logs | SelectValues |
| unique pipeline data | SortRows |

## Hops

| From | To |
|------|----|
| Pipeline Logging | pipeline logs |
| pipeline logs | unique pipeline data |
| Pipeline Logging | transform logs |
| transform logs | pipeline-log-transforms.csv out |
| transform logs | get some stats |
| unique pipeline data | transform LU |
| get some stats | transform LU |
| transform LU | r/s in |
| r/s in | pipeline-log.csv out |
