# Pipeline: 0011-calculator-basics

## Basic Information

- **Pipeline Name:** 0011-calculator-basics
- **Source File:** `03-转换插件/计算与字段操作类/samples/0011-calculator-basics.hpl`

## Transforms

| Name | Type |
|------|------|
| Calculator | Calculator |
| Verify | Dummy |
| test-data | DataGrid |

## Hops

| From | To |
|------|----|
| test-data | Calculator |
| Calculator | Verify |
