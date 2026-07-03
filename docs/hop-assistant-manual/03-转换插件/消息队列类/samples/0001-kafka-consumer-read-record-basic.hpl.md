# Pipeline: 0001-kafka-consumer-read-record-basic

## Basic Information

- **Pipeline Name:** 0001-kafka-consumer-read-record-basic
- **Source File:** `03-转换插件/消息队列类/samples/0001-kafka-consumer-read-record-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Kafka Consumer | KafkaConsumer |
| Log Output | WriteToLog |
| Stop Kafka Processing | Abort |

## Hops

| From | To |
|------|----|
| Kafka Consumer | Log Output |
| Log Output | Stop Kafka Processing |
