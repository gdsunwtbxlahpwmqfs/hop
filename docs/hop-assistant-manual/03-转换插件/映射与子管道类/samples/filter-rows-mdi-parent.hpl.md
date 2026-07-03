# Pipeline: filter-rows-mdi-parent

## Basic Information

- **Pipeline Name:** filter-rows-mdi-parent
- **Source File:** `03-转换插件/映射与子管道类/samples/filter-rows-mdi-parent.hpl`

## Transforms

| Name | Type |
|------|------|
| filter-rows-mdi-child.hpl | MetaInject |
| metadata | DataGrid |

## Hops

| From | To |
|------|----|
| metadata | filter-rows-mdi-child.hpl |
