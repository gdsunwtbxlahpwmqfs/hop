# Pipeline: 0006-generate-bundle-files-validation

## Basic Information

- **Pipeline Name:** 0006-generate-bundle-files-validation
- **Source File:** `03-转换插件/Beam大数据类/samples/0006-generate-bundle-files-validation.hpl`

## Transforms

| Name | Type |
|------|------|
| /tmp/0006/*.avro | GetFileNames |
| /tmp/0006/*.csv | TextFileInput2 |
| /tmp/0006/*.json | JsonInput |
| /tmp/0006/*.parquet | GetFileNames |
| /tmp/0006/*.xlsx | ExcelInput |
| Avro Decode | AvroDecode |
| avro | Dummy |
| cvs | Dummy |
| json | Dummy |
| parquet | Dummy |
| read Avro file | AvroFileInput |
| read Parquet file | ParquetFileInput |
| xlsx | Dummy |

## Hops

| From | To |
|------|----|
| /tmp/0006/*.json | json |
| /tmp/0006/*.csv | cvs |
| /tmp/0006/*.xlsx | xlsx |
| /tmp/0006/*.parquet | read Parquet file |
| read Parquet file | parquet |
| /tmp/0006/*.avro | read Avro file |
| Avro Decode | avro |
| read Avro file | Avro Decode |
