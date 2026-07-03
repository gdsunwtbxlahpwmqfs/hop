# Pipeline: 0001-prepare-kafka-test

## Basic Information

- **Pipeline Name:** 0001-prepare-kafka-test
- **Source File:** `03-转换插件/消息队列类/samples/prepare-kafka-test-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Generate rows | RowGenerator |
| Kafka Producer | KafkaProducerOutput |

## Hops

| From | To |
|------|----|
| Generate rows | Kafka Producer |
