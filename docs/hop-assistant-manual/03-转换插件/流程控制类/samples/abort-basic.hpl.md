# Pipeline: abort-basic

## Basic Information

- **Pipeline Name:** abort-basic
- **Source File:** `03-转换插件/流程控制类/samples/abort-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Test Data | DataGrid |
| id = 4? | FilterRows |
| Abort | Abort |

## Hops

| From | To |
|------|----|
| Test Data | id = 4? |
| id = 4? | Abort |

## Notes

Aborts this pipeline based on the result from a Filter Rows transform

---
