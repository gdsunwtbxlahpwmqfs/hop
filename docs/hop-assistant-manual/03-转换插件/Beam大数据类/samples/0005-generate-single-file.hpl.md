# Pipeline: 0005-generate-single-file

## Basic Information

- **Pipeline Name:** 0005-generate-single-file
- **Source File:** `03-转换插件/Beam大数据类/samples/0005-generate-single-file.hpl`

## Transforms

| Name | Type |
|------|------|
| FL and CA | FilterRows |
| customers-ca-fl.avro | AvroOutput |
| customers-ca-fl.csv | TextFileOutput |
| customers-ca-fl.json | JsonOutput |
| customers-ca-fl.xlsx | TypeExitExcelWriterTransform |
| customers-fl-ca.parquet | ParquetFileOutput |
| input/customers-noheader-1k.txt | BeamInput |

## Hops

| From | To |
|------|----|
| input/customers-noheader-1k.txt | FL and CA |
| FL and CA | customers-ca-fl.json |
| FL and CA | customers-ca-fl.csv |
| FL and CA | customers-ca-fl.xlsx |
| FL and CA | customers-fl-ca.parquet |
| FL and CA | customers-ca-fl.avro |
