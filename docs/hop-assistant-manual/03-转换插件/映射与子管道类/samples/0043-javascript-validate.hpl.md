# Pipeline: 0043-javascript-validate

## Basic Information

- **Pipeline Name:** 0043-javascript-validate
- **Source File:** `03-转换插件/映射与子管道类/samples/0043-javascript-validate.hpl`

## Transforms

| Name | Type |
|------|------|
| /tmp/javascript.hpl | getXMLData |
| validate | Dummy |
| /tmp/javascript.hpl scripts | getXMLData |
| validate scripts | Dummy |
| /tmp/javascript.hpl fields | getXMLData |
| validate fields | Dummy |

## Hops

| From | To |
|------|----|
| /tmp/javascript.hpl | validate |
| /tmp/javascript.hpl scripts | validate scripts |
| /tmp/javascript.hpl fields | validate fields |
