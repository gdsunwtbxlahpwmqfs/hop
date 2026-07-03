# Pipeline: textfileoutput-huge-file

## Basic Information

- **Pipeline Name:** textfileoutput-huge-file
- **Source File:** `03-转换插件/输出类/samples/textfileoutput-huge-file.hpl`

## Transforms

| Name | Type |
|------|------|
| Generate 25M rows | RowGenerator |
| Fake book data | Fake |
| Write books file | TextFileOutput |

## Hops

| From | To |
|------|----|
| Generate 25M rows | Fake book data |
| Fake book data | Write books file |

## Notes

Generate 25M rows of fake book data and write to csv file.

Use 4 copies of the Fake Data transform to speed up the process

This pipeline will create a 1.5GB file and may take a couple of minutes to run.

---
