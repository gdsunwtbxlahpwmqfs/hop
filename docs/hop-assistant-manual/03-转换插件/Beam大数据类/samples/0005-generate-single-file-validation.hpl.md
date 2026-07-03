# Pipeline: 0005-generate-single-file-validation

## Basic Information

- **Pipeline Name:** 0005-generate-single-file-validation
- **Source File:** `03-转换插件/Beam大数据类/samples/0005-generate-single-file-validation.hpl`

## Transforms

| Name | Type |
|------|------|
| /tmp/0005/*.avro | GetFileNames |
| /tmp/0005/*.csv | TextFileInput2 |
| /tmp/0005/*.json | JsonInput |
| /tmp/0005/*.parquet | GetFileNames |
| /tmp/0005/*.xlsx | ExcelInput |
| Avro Decode | AvroDecode |
| avro | Dummy |
| csv | Dummy |
| json | Dummy |
| parquet | Dummy |
| read Avro file | AvroFileInput |
| read Parquet file | ParquetFileInput |
| xlsx | Dummy |

## Hops

| From | To |
|------|----|
| /tmp/0005/*.json | json |
| /tmp/0005/*.csv | csv |
| /tmp/0005/*.xlsx | xlsx |
| /tmp/0005/*.parquet | read Parquet file |
| read Parquet file | parquet |
| /tmp/0005/*.avro | read Avro file |
| read Avro file | Avro Decode |
| Avro Decode | avro |
