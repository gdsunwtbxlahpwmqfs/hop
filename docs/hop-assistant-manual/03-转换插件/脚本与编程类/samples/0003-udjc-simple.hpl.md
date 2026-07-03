# Pipeline: 00200-udjc-simple

## Basic Information

- **Pipeline Name:** 00200-udjc-simple
- **Source File:** `03-转换插件/脚本与编程类/samples/0003-udjc-simple.hpl`

## Transforms

| Name | Type |
|------|------|
| 10k rows | RowGenerator |
| name | UserDefinedJavaClass |
| values | UniqueRowsByHashSet |
| More than 1 row | Abort |
| name <> "Apache Hop" | FilterRows |

## Hops

| From | To |
|------|----|
| 10k rows | name |
| name | values |
| values | name <> "Apache Hop" |
| name <> "Apache Hop" | More than 1 row |
