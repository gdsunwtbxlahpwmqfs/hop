# Pipeline: 0004-xsl-transformation

## Basic Information

- **Pipeline Name:** 0004-xsl-transformation
- **Source File:** `03-转换插件/输出类/samples/0004-xsl-transformation.hpl`

## Transforms

| Name | Type |
|------|------|
| Get file names | GetFileNames |
| Load file content in memory | LoadFileInput |
| XSL Transformation | XSLT |

## Hops

| From | To |
|------|----|
| Get file names | Load file content in memory |
| Load file content in memory | XSL Transformation |
