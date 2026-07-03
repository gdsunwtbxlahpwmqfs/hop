# Pipeline: merge-join-basic

## Basic Information

- **Pipeline Name:** merge-join-basic
- **Source File:** `03-转换插件/查找与连接类/samples/merge-join-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Test Data 1 | DataGrid |
| Test Data 2 | DataGrid |
| Merge join Left Outer | MergeJoin |
| Merge join Right Outer | MergeJoin |
| Merge join Full Outer | MergeJoin |
| Merge join Inner | MergeJoin |
| Output Inner | Dummy |
| Output Left Outer | Dummy |
| Output Right Outer | Dummy |
| Output Full Outer | Dummy |

## Hops

| From | To |
|------|----|
| Test Data 1 | Merge join Left Outer |
| Test Data 2 | Merge join Left Outer |
| Test Data 2 | Merge join Full Outer |
| Test Data 1 | Merge join Right Outer |
| Test Data 2 | Merge join Right Outer |
| Test Data 1 | Merge join Full Outer |
| Test Data 1 | Merge join Inner |
| Test Data 2 | Merge join Inner |
| Merge join Inner | Output Inner |
| Merge join Left Outer | Output Left Outer |
| Merge join Right Outer | Output Right Outer |
| Merge join Full Outer | Output Full Outer |

## Notes

Join the rows from "Data Set 1" and "Data Set 2", based on the "id" field. The 4 Merge Join transforms show the possible join behavior (similar to SQL):

*) INNER

*) LEFT OUTER

*) RIGHT OUTER

*) FULL OUTER

---
