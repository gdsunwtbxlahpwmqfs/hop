# Pipeline: kafka-aggregation-pipeline

## Basic Information

- **Pipeline Name:** kafka-aggregation-pipeline
- **Source File:** `03-转换插件/Beam大数据类/samples/kafka-aggregation-pipeline.hpl`

## Transforms

| Name | Type |
|------|------|
| Beam Kafka Consume | BeamKafkaConsume |
| Beam Kafka Produce | BeamKafkaProduce |
| Beam Window | BeamWindow |
| String to Rows | JsonInput |
| Rows to String | JsonOutput |
| Memory group by | MemoryGroupBy |

## Hops

| From | To |
|------|----|
| Beam Kafka Consume | String to Rows |
| String to Rows | Beam Window |
| Beam Window | Memory group by |
| Memory group by | Rows to String |
| Rows to String | Beam Kafka Produce |

## Notes

Before running this pipeline, make sure a kafka server is running with two topics: hop-in and hop-out.

Example run of producer:

# bin/kafka-console-producer.sh --topic hop-in --bootstrap-server localhost:9092 --property "parse.key=true" --property "key.separator=:"

produce messages into in-topic in json format (e.g. {"name":"toni"}), the pipeline counts the number of times each name is shown

---
