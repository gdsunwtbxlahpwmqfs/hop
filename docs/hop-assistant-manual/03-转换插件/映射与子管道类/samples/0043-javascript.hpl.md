# Pipeline: 0043-javascript

## Basic Information

- **Pipeline Name:** 0043-javascript
- **Source File:** `03-转换插件/映射与子管道类/samples/0043-javascript.hpl`

## Transforms

| Name | Type |
|------|------|
| /tmp/0043-javascript-template.hpl | MetaInject |
| files/javascript.xml | getXMLData |
| files/javascript.xml fields | getXMLData |
| files/javascript.xml scripts | getXMLData |

## Hops

| From | To |
|------|----|
| files/javascript.xml | /tmp/0043-javascript-template.hpl |
| files/javascript.xml scripts | /tmp/0043-javascript-template.hpl |
| files/javascript.xml fields | /tmp/0043-javascript-template.hpl |
