# Pipeline: 0048-stream-lookup

## Basic Information

- **Pipeline Name:** 0048-stream-lookup
- **Source File:** `03-转换插件/映射与子管道类/samples/0048-stream-lookup.hpl`

## Transforms

| Name | Type |
|------|------|
| 0048-stream-lookup.hpl | MetaInject |
| files/stream-lookup.xml | getXMLData |
| files/stream-lookup.xml keys | getXMLData |
| files/stream-lookup.xml values | getXMLData |

## Hops

| From | To |
|------|----|
| files/stream-lookup.xml | 0048-stream-lookup.hpl |
| files/stream-lookup.xml keys | 0048-stream-lookup.hpl |
| files/stream-lookup.xml values | 0048-stream-lookup.hpl |
