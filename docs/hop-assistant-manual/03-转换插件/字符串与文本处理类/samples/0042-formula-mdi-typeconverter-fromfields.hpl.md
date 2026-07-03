# Pipeline: 0042-formula-mdi-typeconverter-fromfields

## Basic Information

- **Pipeline Name:** 0042-formula-mdi-typeconverter-fromfields
- **Source File:** `03-转换插件/字符串与文本处理类/samples/0042-formula-mdi-typeconverter-fromfields.hpl`

## Transforms

| Name | Type |
|------|------|
| ETL metadata injection | MetaInject |
| Generate rows | RowGenerator |

## Hops

| From | To |
|------|----|
| Generate rows | ETL metadata injection |
