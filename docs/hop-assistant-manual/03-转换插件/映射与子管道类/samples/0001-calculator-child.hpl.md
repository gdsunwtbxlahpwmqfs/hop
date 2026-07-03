# Pipeline: 0001-calculator-child

## Basic Information

- **Pipeline Name:** 0001-calculator-child
- **Source File:** `03-转换插件/映射与子管道类/samples/0001-calculator-child.hpl`

## Transforms

| Name | Type |
|------|------|
| Calculator | Calculator |
| Log results | WriteToLog |
| Sample data | DataGrid |

## Hops

| From | To |
|------|----|
| Sample data | Calculator |
| Calculator | Log results |
