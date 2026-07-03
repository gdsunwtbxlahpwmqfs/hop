# Pipeline: New pipeline

## Basic Information

- **Pipeline Name:** New pipeline
- **Source File:** `03-转换插件/映射与子管道类/samples/0047-kafka-consumer-input.hpl`

## Transforms

| Name | Type |
|------|------|
| 0047-kafka-consumer-input-template.hpl | MetaInject |
| files/kafka-consumer-input.xml | getXMLData |
| files/kafka-consumer-input.xml topics | getXMLData |
| files/kafka-consumer-input.xml options | getXMLData |
| String key, Avro message | DataGrid |

## Hops

| From | To |
|------|----|
| files/kafka-consumer-input.xml | 0047-kafka-consumer-input-template.hpl |
| files/kafka-consumer-input.xml topics | 0047-kafka-consumer-input-template.hpl |
| files/kafka-consumer-input.xml options | 0047-kafka-consumer-input-template.hpl |
| String key, Avro message | 0047-kafka-consumer-input-template.hpl |
