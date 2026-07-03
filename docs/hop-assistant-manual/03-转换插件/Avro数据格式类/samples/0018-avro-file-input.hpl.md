# Pipeline: 0018-avro-file-input

## Basic Information

- **Pipeline Name:** 0018-avro-file-input
- **Source File:** `03-转换插件/Avro数据格式类/samples/0018-avro-file-input.hpl`

## Transforms

| Name | Type |
|------|------|
| Avro Decode | AvroDecode |
| Avro File Input | AvroFileInput |
| Output | SelectValues |
| Reservoir sampling | ReservoirSampling |
| Verify | Dummy |
| filename only | SelectValues |
| files/userdata1.avro | GetFileNames |

## Hops

| From | To |
|------|----|
| files/userdata1.avro | filename only |
| filename only | Avro File Input |
| Avro File Input | Avro Decode |
| Avro Decode | Output |
| Output | Reservoir sampling |
| Reservoir sampling | Verify |
