# Pipeline: textfileoutput-tiny-file

## Basic Information

- **Pipeline Name:** textfileoutput-tiny-file
- **Source File:** `03-转换插件/输出类/samples/textfileoutput-tiny-file.hpl`

## Transforms

| Name | Type |
|------|------|
| Generate 100 rows | RowGenerator |
| Fake book data | Fake |
| Write books file | TextFileOutput |

## Hops

| From | To |
|------|----|
| Generate 100 rows | Fake book data |
| Fake book data | Write books file |
