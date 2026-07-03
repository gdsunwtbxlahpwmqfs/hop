# Pipeline: 0004-concat-with-trim

## Basic Information

- **Pipeline Name:** 0004-concat-with-trim
- **Source File:** `03-转换插件/samples/0004-concat-with-trim.hpl`

## Transforms

| Name | Type |
|------|------|
| Dummy (do nothing) | Dummy |
| OUTPUT | Dummy |
| Set trim type to both on meta | UserDefinedJavaClass |
| concat with nothing | ConcatFields |
| dummy data | DataGrid |

## Hops

| From | To |
|------|----|
| concat with nothing | OUTPUT |
| Dummy (do nothing) | concat with nothing |
| dummy data | Set trim type to both on meta |
| Set trim type to both on meta | Dummy (do nothing) |

## Notes

This concat should left trim the first field and trim both the second field

---
