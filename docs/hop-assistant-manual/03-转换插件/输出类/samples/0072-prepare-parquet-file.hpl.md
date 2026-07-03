# Pipeline: 0072-prepare-parquet-file

## Basic Information

- **Pipeline Name:** 0072-prepare-parquet-file
- **Source File:** `03-转换插件/输出类/samples/0072-prepare-parquet-file.hpl`

## Transforms

| Name | Type |
|------|------|
| Add sequence | Sequence |
| Calculator | Calculator |
| Fake data | Fake |
| Generate random value | RandomValue |
| Generate rows | RowGenerator |
| Parquet File Output | ParquetFileOutput |

## Hops

| From | To |
|------|----|
| Generate rows | Fake data |
| Fake data | Generate random value |
| Generate random value | Add sequence |
| Add sequence | Calculator |
| Calculator | Parquet File Output |
