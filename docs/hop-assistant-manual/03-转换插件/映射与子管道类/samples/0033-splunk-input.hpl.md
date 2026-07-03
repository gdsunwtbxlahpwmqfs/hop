# Pipeline: 0033-splunk-input

## Basic Information

- **Pipeline Name:** 0033-splunk-input
- **Source File:** `03-转换插件/映射与子管道类/samples/0033-splunk-input.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| connection / query | DataGrid |
| inject into Splunk transform | MetaInject |

## Hops

| From | To |
|------|----|
| connection / query | inject into Splunk transform |
| Data grid | inject into Splunk transform |
