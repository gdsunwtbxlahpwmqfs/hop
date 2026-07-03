# Pipeline: 0042-user-defined-java-expression-validate

## Basic Information

- **Pipeline Name:** 0042-user-defined-java-expression-validate
- **Source File:** `03-转换插件/映射与子管道类/samples/0042-user-defined-java-expression-validate.hpl`

## Transforms

| Name | Type |
|------|------|
| /tmp/janino.hpl | getXMLData |
| Validate | Dummy |

## Hops

| From | To |
|------|----|
| /tmp/janino.hpl | Validate |
