# Pipeline: New pipeline

## Basic Information

- **Pipeline Name:** New pipeline
- **Source File:** `03-转换插件/映射与子管道类/samples/0049-kafka-producer-output-validate.hpl`

## Transforms

| Name | Type |
|------|------|
| /tmp/kafka-producer-output.hpl | getXMLData |
| validate | Dummy |
| /tmp/kafka-producer-output.hpl options | getXMLData |
| validate options | Dummy |

## Hops

| From | To |
|------|----|
| /tmp/kafka-producer-output.hpl | validate |
| /tmp/kafka-producer-output.hpl options | validate options |
