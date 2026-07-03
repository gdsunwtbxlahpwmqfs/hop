# Pipeline: 0037-apache-tika-from-getfilenames

## Basic Information

- **Pipeline Name:** 0037-apache-tika-from-getfilenames
- **Source File:** `03-转换插件/文件与编码操作类/samples/0037-apache-tika-from-getfilenames.hpl`

## Transforms

| Name | Type |
|------|------|
| Apache Tika | Tika |
| Get file names | GetFileNames |
| Select values | SelectValues |
| contentChecksum | CheckSum |
| remove content | SelectValues |
| validate | Dummy |

## Hops

| From | To |
|------|----|
| Apache Tika | contentChecksum |
| contentChecksum | remove content |
| remove content | validate |
| Get file names | Select values |
| Select values | Apache Tika |
