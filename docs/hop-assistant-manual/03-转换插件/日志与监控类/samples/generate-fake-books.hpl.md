# Pipeline: pipeline-probe-generate-fake-books

## Basic Information

- **Pipeline Name:** pipeline-probe-generate-fake-books
- **Source File:** `03-转换插件/日志与监控类/samples/generate-fake-books.hpl`

## Transforms

| Name | Type |
|------|------|
| fake books | Fake |
| generate 10k rows | RowGenerator |
| set length | SelectValues |
| dummy | Dummy |

## Hops

| From | To |
|------|----|
| generate 10k rows | fake books |
| fake books | set length |
| set length | dummy |
