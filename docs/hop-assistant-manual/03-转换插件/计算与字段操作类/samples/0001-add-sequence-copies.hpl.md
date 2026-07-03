# Pipeline: 0001-add-sequence-copies

## Basic Information

- **Pipeline Name:** 0001-add-sequence-copies
- **Source File:** `03-转换插件/计算与字段操作类/samples/0001-add-sequence-copies.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| COUNT | 1000000 | Number of IDs to generate |

## Transforms

| Name | Type |
|------|------|
| ${COUNT} rows | RowGenerator |
| Abort | Abort |
| count distinct | GroupBy |
| expected count | GetVariable |
| id | Sequence |
| not expected? | FilterRows |

## Hops

| From | To |
|------|----|
| ${COUNT} rows | id |
| not expected? | Abort |
| id | count distinct |
| count distinct | expected count |
| expected count | not expected? |
