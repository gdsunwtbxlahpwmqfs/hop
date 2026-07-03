# Pipeline: 0042-formula-variables

## Basic Information

- **Pipeline Name:** 0042-formula-variables
- **Source File:** `03-转换插件/字符串与文本处理类/samples/0042-formula-variables.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| BAR | bar |  |
| CONCATENATE | CONCATENATE |  |
| FORMULA | CONCATENATE("foo", "bar") |  |
| FORMULAFIELDS | CONCATENATE([foo],"${BAR}") |  |

## Transforms

| Name | Type |
|------|------|
| Verify | Dummy |
| information formulas | Formula |
| information grid | DataGrid |

## Hops

| From | To |
|------|----|
| information grid | information formulas |
| information formulas | Verify |
