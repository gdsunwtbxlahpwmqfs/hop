# Pipeline: 0014-transactional-stalling-freezed

## Basic Information

- **Pipeline Name:** 0014-transactional-stalling-freezed
- **Source File:** `03-转换插件/数据库操作类/samples/0014-transactional-stalling-freezed.hpl`

## Transforms

| Name | Type |
|------|------|
| Attempt to read from non-working db | TableInput |
| Nothing | Dummy |

## Hops

| From | To |
|------|----|
| Attempt to read from non-working db | Nothing |
