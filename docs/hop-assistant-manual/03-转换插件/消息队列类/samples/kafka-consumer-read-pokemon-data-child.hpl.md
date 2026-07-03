# Pipeline: process-pokemon-data-child

## Basic Information

- **Pipeline Name:** process-pokemon-data-child
- **Source File:** `03-转换插件/消息队列类/samples/kafka-consumer-read-pokemon-data-child.hpl`

## Transforms

| Name | Type |
|------|------|
| Get messages from Kafka | Injector |
| OUTPUT | Dummy |
| Read JSON message | JsonInput |
| Remove JSON message | SelectValues |

## Hops

| From | To |
|------|----|
| Get messages from Kafka | Read JSON message |
| Read JSON message | Remove JSON message |
| Remove JSON message | OUTPUT |
