# Pipeline: 0005-udjc-render-all-actions-in-svg

## Basic Information

- **Pipeline Name:** 0005-udjc-render-all-actions-in-svg
- **Source File:** `03-转换插件/脚本与编程类/samples/0005-udjc-render-all-actions-in-svg.hpl`

## Transforms

| Name | Type |
|------|------|
| /tmp/actions.svg | TextFileOutput |
| 1 row | RowGenerator |
| SVG of all actions | UserDefinedJavaClass |

## Hops

| From | To |
|------|----|
| 1 row | SVG of all actions |
| SVG of all actions | /tmp/actions.svg |
