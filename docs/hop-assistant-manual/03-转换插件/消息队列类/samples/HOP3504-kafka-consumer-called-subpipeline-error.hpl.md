# Pipeline: 0001-kafka-consumer-called-subpipeline-error

## Basic Information

- **Pipeline Name:** 0001-kafka-consumer-called-subpipeline-error
- **Source File:** `03-转换插件/消息队列类/samples/HOP3504-kafka-consumer-called-subpipeline-error.hpl`

## Transforms

| Name | Type |
|------|------|
| Injector | Injector |
| Generate error | WriteToLog |

## Hops

| From | To |
|------|----|
| Injector | Generate error |
