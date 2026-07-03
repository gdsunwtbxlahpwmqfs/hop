# Pipeline: 0007-single-thread

## Basic Information

- **Pipeline Name:** 0007-single-thread
- **Source File:** `03-转换插件/云服务类/samples/0007-single-thread.hpl`

## Transforms

| Name | Type |
|------|------|
| 0007-customers-ca-fl.avro | AvroOutput |
| 0007-customers-ca-fl.csv | TextFileOutput |
| 0007-customers-ca-fl.json | JsonOutput |
| 0007-customers-ca-fl.xlsx | TypeExitExcelWriterTransform |
| 0007-customers-fl-ca.parquet | ParquetFileOutput |
| FL,CA | FilterRows |
| input/customers-noheader-1k.txt | BeamInput |

## Hops

| From | To |
|------|----|
| FL,CA | 0007-customers-ca-fl.json |
| input/customers-noheader-1k.txt | FL,CA |
| FL,CA | 0007-customers-ca-fl.csv |
| FL,CA | 0007-customers-ca-fl.xlsx |
| FL,CA | 0007-customers-fl-ca.parquet |
| FL,CA | 0007-customers-ca-fl.avro |

## Notes

There is a google storage bug somewhere.

Reading back a newly created xlsx file fails for some reason.

---
