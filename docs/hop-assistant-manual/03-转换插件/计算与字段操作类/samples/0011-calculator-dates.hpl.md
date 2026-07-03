# Pipeline: 0011-calculator-dates

## Basic Information

- **Pipeline Name:** 0011-calculator-dates
- **Source File:** `03-转换插件/计算与字段操作类/samples/0011-calculator-dates.hpl`

## Transforms

| Name | Type |
|------|------|
| test-data | DataGrid |
| Calculator | Calculator |
| Verify | Dummy |

## Hops

| From | To |
|------|----|
| test-data | Calculator |
| Calculator | Verify |
