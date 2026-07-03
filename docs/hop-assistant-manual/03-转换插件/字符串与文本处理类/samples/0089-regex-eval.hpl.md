# Pipeline: 0089-regex-eval

## Basic Information

- **Pipeline Name:** 0089-regex-eval
- **Source File:** `03-转换插件/字符串与文本处理类/samples/0089-regex-eval.hpl`

## Transforms

| Name | Type |
|------|------|
| strings | DataGrid |
| match hop | RegexEval |
| Validate | Dummy |
| capture groups | RegexEval |
| Validate capture groups | Dummy |

## Hops

| From | To |
|------|----|
| strings | match hop |
| match hop | Validate |
| capture groups | Validate capture groups |
| strings | capture groups |
