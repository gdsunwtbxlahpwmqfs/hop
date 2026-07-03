# Pipeline: 0029-parquet-output

## Basic Information

- **Pipeline Name:** 0029-parquet-output
- **Source File:** `03-转换插件/输出类/samples/0029-parquet-output.hpl`

## Transforms

| Name | Type |
|------|------|
| ${java.io.tmpdir}/customers.parquet.snappy | ParquetFileOutput |
| customers-100.txt | CSVInput |

## Hops

| From | To |
|------|----|
| customers-100.txt | ${java.io.tmpdir}/customers.parquet.snappy |
