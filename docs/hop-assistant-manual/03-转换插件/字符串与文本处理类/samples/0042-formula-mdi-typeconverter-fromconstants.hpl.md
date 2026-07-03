# Pipeline: 0042-formula-mdi-typeconverter-fromconstants

## Basic Information

- **Pipeline Name:** 0042-formula-mdi-typeconverter-fromconstants
- **Source File:** `03-转换插件/字符串与文本处理类/samples/0042-formula-mdi-typeconverter-fromconstants.hpl`

## Transforms

| Name | Type |
|------|------|
| ETL metadata injection | MetaInject |
| Generate rows | RowGenerator |

## Hops

| From | To |
|------|----|
| Generate rows | ETL metadata injection |
