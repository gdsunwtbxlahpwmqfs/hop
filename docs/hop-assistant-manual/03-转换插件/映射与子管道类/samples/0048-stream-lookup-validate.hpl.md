# Pipeline: 0048-stream-lookup-validate

## Basic Information

- **Pipeline Name:** 0048-stream-lookup-validate
- **Source File:** `03-转换插件/映射与子管道类/samples/0048-stream-lookup-validate.hpl`

## Transforms

| Name | Type |
|------|------|
| /tmp/stream-lookup.hpl | getXMLData |
| /tmp/stream-lookup.hpl keys | getXMLData |
| validate | Dummy |
| validate keys | Dummy |
| /tmp/stream-lookup.hpl values | getXMLData |
| validate values | Dummy |

## Hops

| From | To |
|------|----|
| /tmp/stream-lookup.hpl | validate |
| /tmp/stream-lookup.hpl keys | validate keys |
| /tmp/stream-lookup.hpl values | validate values |
