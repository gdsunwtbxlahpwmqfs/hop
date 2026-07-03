# Pipeline: avro-file-output

## Basic Information

- **Pipeline Name:** avro-file-output
- **Source File:** `03-转换插件/Avro数据格式类/samples/avro-file-output.hpl`

## Transforms

| Name | Type |
|------|------|
| Avro File Output | AvroOutput |
| read customers-100.txt | TextFileInput2 |

## Hops

| From | To |
|------|----|
| read customers-100.txt | Avro File Output |
