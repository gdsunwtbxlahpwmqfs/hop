# Pipeline: avro-file-input

## Basic Information

- **Pipeline Name:** avro-file-input
- **Source File:** `03-转换插件/Avro数据格式类/samples/avro-file-input.hpl`

## Transforms

| Name | Type |
|------|------|
| Avro Decode | AvroDecode |
| Avro File Input | AvroFileInput |
| generate 1 row | RowGenerator |
| clean | SelectValues |

## Hops

| From | To |
|------|----|
| generate 1 row | Avro File Input |
| Avro File Input | Avro Decode |
| Avro Decode | clean |
