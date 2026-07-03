# Pipeline: avro-encode-decode-basic

## Basic Information

- **Pipeline Name:** avro-encode-decode-basic
- **Source File:** `03-转换插件/Avro数据格式类/samples/avro-encode-decode-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Avro Decode | AvroDecode |
| Avro Encode | AvroEncode |
| data grid | DataGrid |

## Hops

| From | To |
|------|----|
| data grid | Avro Encode |
| Avro Encode | Avro Decode |
