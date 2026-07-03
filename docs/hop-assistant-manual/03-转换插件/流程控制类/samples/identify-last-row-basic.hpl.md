# Pipeline: identify-last-row-basic

## Basic Information

- **Pipeline Name:** identify-last-row-basic
- **Source File:** `03-转换插件/流程控制类/samples/identify-last-row-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Identify last row in a stream | DetectLastRow |
| Test Data | DataGrid |
| Output | Dummy |

## Hops

| From | To |
|------|----|
| Test Data | Identify last row in a stream |
| Identify last row in a stream | Output |

## Notes

Adds a boolean field that flags the last row in a stream as 'Y', all other rows are 'N'.

---
