# Pipeline: 0031-stringcut-basics

## Basic Information

- **Pipeline Name:** 0031-stringcut-basics
- **Source File:** `03-转换插件/字符串与文本处理类/samples/0031-stringcut-basics.hpl`

## Transforms

| Name | Type |
|------|------|
| Generate input string | RowGenerator |
| Strings cut | StringCut |
| Verify | Dummy |

## Hops

| From | To |
|------|----|
| Generate input string | Strings cut |
| Strings cut | Verify |
