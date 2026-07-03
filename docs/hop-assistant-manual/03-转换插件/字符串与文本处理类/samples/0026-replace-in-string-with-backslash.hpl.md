# Pipeline: 0026-replace-in-string-with-backslash

## Basic Information

- **Pipeline Name:** 0026-replace-in-string-with-backslash
- **Source File:** `03-转换插件/字符串与文本处理类/samples/0026-replace-in-string-with-backslash.hpl`

## Parameters

| Parameter | Default | Description |
|-----------|---------|-------------|
| REPLACE_PARAM | xx\zz | Variable with backslash |

## Transforms

| Name | Type |
|------|------|
| Replace in string | ReplaceString |
| Verify | Dummy |
| sample data | DataGrid |

## Hops

| From | To |
|------|----|
| sample data | Replace in string |
| Replace in string | Verify |
