# Pipeline: HOP3504-kafka-consumer-read-record-error

## Basic Information

- **Pipeline Name:** HOP3504-kafka-consumer-read-record-error
- **Source File:** `03-转换插件/消息队列类/samples/HOP3504-kafka-consumer-read-record-error.hpl`

## Transforms

| Name | Type |
|------|------|
| Dummy (do nothing) | Dummy |
| Kafka Consumer | KafkaConsumer |
| Log Output | WriteToLog |
| Stop Kafka Processing | Abort |

## Hops

| From | To |
|------|----|
| Kafka Consumer | Log Output |
| Kafka Consumer | Dummy (do nothing) |
| Log Output | Stop Kafka Processing |
