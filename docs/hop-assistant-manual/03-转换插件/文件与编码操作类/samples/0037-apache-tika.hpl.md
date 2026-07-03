# Pipeline: 0037-apache-tika

## Basic Information

- **Pipeline Name:** 0037-apache-tika
- **Source File:** `03-转换插件/文件与编码操作类/samples/0037-apache-tika.hpl`

## Transforms

| Name | Type |
|------|------|
| Apache Tika | Tika |
| contentChecksum | CheckSum |
| remove content | SelectValues |
| validate | Dummy |

## Hops

| From | To |
|------|----|
| Apache Tika | contentChecksum |
| contentChecksum | remove content |
| remove content | validate |
