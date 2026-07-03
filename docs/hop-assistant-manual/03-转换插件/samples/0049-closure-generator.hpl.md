# Pipeline: 0049-closure-generator

## Basic Information

- **Pipeline Name:** 0049-closure-generator
- **Source File:** `03-转换插件/samples/0049-closure-generator.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| Generate closure | ClosureGenerator |
| Output | Dummy |

## Hops

| From | To |
|------|----|
| Data grid | Generate closure |
| Generate closure | Output |
