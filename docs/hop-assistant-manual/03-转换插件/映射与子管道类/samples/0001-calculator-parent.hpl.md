# Pipeline: 0001-calculator-parent

## Basic Information

- **Pipeline Name:** 0001-calculator-parent
- **Source File:** `03-转换插件/映射与子管道类/samples/0001-calculator-parent.hpl`

## Transforms

| Name | Type |
|------|------|
| 0001-calculator-child.hpl | MetaInject |
| Calculator Metadata | DataGrid |

## Hops

| From | To |
|------|----|
| Calculator Metadata | 0001-calculator-child.hpl |
