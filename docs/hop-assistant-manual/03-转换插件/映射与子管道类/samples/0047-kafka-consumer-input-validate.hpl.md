# Pipeline: New pipeline

## Basic Information

- **Pipeline Name:** New pipeline
- **Source File:** `03-转换插件/映射与子管道类/samples/0047-kafka-consumer-input-validate.hpl`

## Transforms

| Name | Type |
|------|------|
| /tmp/kafka-consumer-input.hpl | getXMLData |
| validate | Dummy |
| /tmp/kafka-consumer-input.hpl topics | getXMLData |
| validate topics | Dummy |
| /tmp/kafka-consumer-input.hpl options | getXMLData |
| validate options | Dummy |

## Hops

| From | To |
|------|----|
| /tmp/kafka-consumer-input.hpl | validate |
| /tmp/kafka-consumer-input.hpl topics | validate topics |
| /tmp/kafka-consumer-input.hpl options | validate options |
