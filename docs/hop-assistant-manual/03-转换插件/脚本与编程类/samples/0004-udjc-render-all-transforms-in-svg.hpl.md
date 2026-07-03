# Pipeline: 0004-udjc-render-all-transforms-in-svg

## Basic Information

- **Pipeline Name:** 0004-udjc-render-all-transforms-in-svg
- **Source File:** `03-转换插件/脚本与编程类/samples/0004-udjc-render-all-transforms-in-svg.hpl`

## Transforms

| Name | Type |
|------|------|
| /tmp/transforms.svg | TextFileOutput |
| 1 row | RowGenerator |
| SVG of all transforms | UserDefinedJavaClass |

## Hops

| From | To |
|------|----|
| SVG of all transforms | /tmp/transforms.svg |
| 1 row | SVG of all transforms |
