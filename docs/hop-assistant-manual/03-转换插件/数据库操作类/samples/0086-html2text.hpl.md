# Pipeline: 0086-html2text

## Basic Information

- **Pipeline Name:** 0086-html2text
- **Source File:** `03-转换插件/数据库操作类/samples/0086-html2text.hpl`

## Transforms

| Name | Type |
|------|------|
| HTML to Text | Html2Text |
| basic-html | DataGrid |
| Verify | Dummy |

## Hops

| From | To |
|------|----|
| basic-html | HTML to Text |
| HTML to Text | Verify |
