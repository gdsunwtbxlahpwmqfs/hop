# Pipeline: generate-1000-rows

## Basic Information

- **Pipeline Name:** generate-1000-rows
- **Source File:** `03-转换插件/映射与子管道类/samples/child-generate-1000-rows.hpl`

## Transforms

| Name | Type |
|------|------|
| generate 1k rows | RowGenerator |
| generate random strings | RandomValue |
| loops-get-rows-from-result.hpl | PipelineExecutor |

## Hops

| From | To |
|------|----|
| generate 1k rows | generate random strings |
| generate random strings | loops-get-rows-from-result.hpl |

## Notes

send rows to child pipeline in groups of 100

---
