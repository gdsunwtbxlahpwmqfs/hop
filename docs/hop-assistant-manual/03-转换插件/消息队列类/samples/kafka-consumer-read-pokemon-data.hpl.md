# Pipeline: kafka-consumer-read-pokemon-data

## Basic Information

- **Pipeline Name:** kafka-consumer-read-pokemon-data
- **Source File:** `03-转换插件/消息队列类/samples/kafka-consumer-read-pokemon-data.hpl`

## Transforms

| Name | Type |
|------|------|
| Kafka Consumer | KafkaConsumer |
| Output | Dummy |

## Hops

| From | To |
|------|----|
| Kafka Consumer | Output |

## Notes

This sample reads JSON messages from a Kafka topic.

A batch of messages is read and sent to the child pipeline.

If processing was a success the messages are flagged as read on the Kafka server topic and a next batch of records is taken.

Please set the following variables in your environment configuration file(s):

KAFKA_SERVER

KAFKA_TOPIC

KAFKA_CLIENT_ID

---
