# Pipeline: 0015-apache-tika-child

## Basic Information

- **Pipeline Name:** 0015-apache-tika-child
- **Source File:** `03-转换插件/映射与子管道类/samples/0015-apache-tika-child.hpl`

## Transforms

| Name | Type |
|------|------|
| Apache Tika | Tika |
| OUTPUT | Dummy |
| contentChecksum | CheckSum |
| remove content | SelectValues |

## Hops

| From | To |
|------|----|
| Apache Tika | contentChecksum |
| contentChecksum | remove content |
| remove content | OUTPUT |
