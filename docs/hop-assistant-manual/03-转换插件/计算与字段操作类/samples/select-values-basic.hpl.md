# Pipeline: select-values-basic

## Basic Information

- **Pipeline Name:** select-values-basic
- **Source File:** `03-转换插件/计算与字段操作类/samples/select-values-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Test Data | DataGrid |
| Rename, reorder fields | SelectValues |
| Remove date | SelectValues |
| Cast data types | SelectValues |
| Output Rename, reorder | Dummy |
| Output Remove | Dummy |
| Output Cast | Dummy |
| Output Cast Errors | Dummy |

## Hops

| From | To |
|------|----|
| Test Data | Rename, reorder fields |
| Test Data | Remove date |
| Test Data | Cast data types |
| Rename, reorder fields | Output Rename, reorder |
| Remove date | Output Remove |
| Cast data types | Output Cast |
| Cast data types | Output Cast Errors |

## Notes

Modifies the fields and metadata in the pipeline stream in a variety of ways:

*) Rename reorder fields (Select & Alter tab): rename fields, change order of the desc and date fields

*) Remove date (Remove tab): remove the date field

*) Cast data types (Metadata tab): cast the String to a date

and

*) Output Cast Errors: handle errors thrown by 'Cast data types'. Click on the 'Cast data types' transform and choose 'Error Handling' to configure

---
