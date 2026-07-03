# Pipeline: New pipeline

## Basic Information

- **Pipeline Name:** New pipeline
- **Source File:** `03-转换插件/映射与子管道类/samples/0049-kafka-producer-output.hpl`

## Transforms

| Name | Type |
|------|------|
| 0049-kafka-producer-output-template.hpl | MetaInject |
| files/kafka-producer-output.xml | getXMLData |
| files/kafka-producer-output.xml options | getXMLData |

## Hops

| From | To |
|------|----|
| files/kafka-producer-output.xml | 0049-kafka-producer-output-template.hpl |
| files/kafka-producer-output.xml options | 0049-kafka-producer-output-template.hpl |
