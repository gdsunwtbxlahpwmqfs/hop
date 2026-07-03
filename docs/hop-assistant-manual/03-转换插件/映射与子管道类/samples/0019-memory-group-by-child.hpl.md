# Pipeline: 0019-memory-group-by-child

## Basic Information

- **Pipeline Name:** 0019-memory-group-by-child
- **Source File:** `03-转换插件/映射与子管道类/samples/0019-memory-group-by-child.hpl`

## Transforms

| Name | Type |
|------|------|
| Memory group by | MemoryGroupBy |
| Output | MappingOutput |
| Input | MappingInput |

## Hops

| From | To |
|------|----|
| Memory group by | Output |
| Input | Memory group by |
