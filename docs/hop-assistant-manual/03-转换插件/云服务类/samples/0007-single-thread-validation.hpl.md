# Pipeline: 0007-single-thread-validation

## Basic Information

- **Pipeline Name:** 0007-single-thread-validation
- **Source File:** `03-转换插件/云服务类/samples/0007-single-thread-validation.hpl`

## Transforms

| Name | Type |
|------|------|
| Avro Decode | AvroDecode |
| avro | Dummy |
| csv | Dummy |
| gs:///apache-hop-it/output/0007-*.xlsx | ExcelInput |
| gs://apache-hop-it/output/*.avro | GetFileNames |
| gs://apache-hop-it/output/0007-*.csv | TextFileInput2 |
| gs://apache-hop-it/output/0007-*.json | JsonInput |
| gs://apache-hop-it/output/0007-*.parquet | GetFileNames |
| json | Dummy |
| parquet | Dummy |
| read Avro file | AvroFileInput |
| read Parquet file | ParquetFileInput |
| xlsx | Dummy |

## Hops

| From | To |
|------|----|
| gs://apache-hop-it/output/0007-*.json | json |
| gs://apache-hop-it/output/0007-*.csv | csv |
| gs:///apache-hop-it/output/0007-*.xlsx | xlsx |
| gs://apache-hop-it/output/0007-*.parquet | read Parquet file |
| read Parquet file | parquet |
| gs://apache-hop-it/output/*.avro | read Avro file |
| Avro Decode | avro |
| read Avro file | Avro Decode |
