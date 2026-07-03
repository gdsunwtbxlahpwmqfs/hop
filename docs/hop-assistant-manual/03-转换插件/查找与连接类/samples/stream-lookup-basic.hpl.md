# Pipeline: stream-lookup-basic

## Basic Information

- **Pipeline Name:** stream-lookup-basic
- **Source File:** `03-转换插件/查找与连接类/samples/stream-lookup-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Lookup desc | StreamLookup |
| Output | Dummy |
| Test Data | DataGrid |
| Test Data Lookup | DataGrid |

## Hops

| From | To |
|------|----|
| Test Data | Lookup desc |
| Lookup desc | Output |
| Test Data Lookup | Lookup desc |

## Notes

Read "Test Data" and lookup the desc field from the corresponding "Test Data Lookup" data grid through the "Lookup desc" Stream Lookup.

---
