# Pipeline: generate-pokemon-data

## Basic Information

- **Pipeline Name:** generate-pokemon-data
- **Source File:** `03-转换插件/消息队列类/samples/kafka-producer-create-pokemon-data.hpl`

## Transforms

| Name | Type |
|------|------|
| Fake pokemon data | Fake |
| Kafka Producer | KafkaProducerOutput |
| int,id | RandomValue |
| json | JsonOutput |
| oo rows | RowGenerator |
| userId | ScriptValueMod |

## Hops

| From | To |
|------|----|
| Fake pokemon data | json |
| int,id | userId |
| json | Kafka Producer |
| oo rows | int,id |
| userId | Fake pokemon data |

## Notes

This sample generates a new JSON message every second and sends it to a Kafka server.

Please set the following variables in your environment configuration file(s):

KAFKA_SERVER

KAFKA_TOPIC

KAFKA_CLIENT_ID

---
