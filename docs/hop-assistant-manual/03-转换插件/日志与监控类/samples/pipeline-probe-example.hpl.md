# Pipeline: pipeline-probe-example

## Basic Information

- **Pipeline Name:** pipeline-probe-example
- **Source File:** `03-转换插件/日志与监控类/samples/pipeline-probe-example.hpl`

## Transforms

| Name | Type |
|------|------|
| ${PROJECT_HOME}/books-per-genre/probe-data.csv out | TextFileOutput |
| Pipeline Data Probe | PipelineDataProbe |
| book data to fields | Denormaliser |
| get nb_books_in_genre | MemoryGroupBy |
| sort pipeline, genre | SortRows |

## Hops

| From | To |
|------|----|
| Pipeline Data Probe | book data to fields |
| book data to fields | get nb_books_in_genre |
| get nb_books_in_genre | sort pipeline, genre |
| sort pipeline, genre | ${PROJECT_HOME}/books-per-genre/probe-data.csv out |
