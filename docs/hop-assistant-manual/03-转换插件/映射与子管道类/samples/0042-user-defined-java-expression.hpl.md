# Pipeline: 0042-user-defined-java-expression

## Basic Information

- **Pipeline Name:** 0042-user-defined-java-expression
- **Source File:** `03-转换插件/映射与子管道类/samples/0042-user-defined-java-expression.hpl`

## Transforms

| Name | Type |
|------|------|
| 0042-user-defined-java-expression-template.hpl | MetaInject |
| files/janino.xml fields | getXMLData |

## Hops

| From | To |
|------|----|
| files/janino.xml fields | 0042-user-defined-java-expression-template.hpl |
