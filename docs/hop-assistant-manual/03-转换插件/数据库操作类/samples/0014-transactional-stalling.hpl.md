# Pipeline: 0014-transactional-stalling

## Basic Information

- **Pipeline Name:** 0014-transactional-stalling
- **Source File:** `03-转换插件/数据库操作类/samples/0014-transactional-stalling.hpl`

## Transforms

| Name | Type |
|------|------|
| 0014-transactional-stalling-freezed.hpl | PipelineExecutor |
| 1 row | RowGenerator |

## Hops

| From | To |
|------|----|
| 1 row | 0014-transactional-stalling-freezed.hpl |
