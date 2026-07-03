# Pipeline: user-defined-java-class-calculate-date-of-easter

## Basic Information

- **Pipeline Name:** user-defined-java-class-calculate-date-of-easter
- **Source File:** `03-转换插件/脚本与编程类/samples/user-defined-java-class-calculate-date-of-easter.hpl`

## Transforms

| Name | Type |
|------|------|
| 1M rows | RowGenerator |
| UDJC: calculate Easter date | UserDefinedJavaClass |

## Hops

| From | To |
|------|----|
| 1M rows | UDJC: calculate Easter date |
