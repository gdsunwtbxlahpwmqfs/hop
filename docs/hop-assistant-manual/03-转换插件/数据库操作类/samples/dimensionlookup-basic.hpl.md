# Pipeline: dimensionlookup-basic

## Basic Information

- **Pipeline Name:** dimensionlookup-basic
- **Source File:** `03-转换插件/数据库操作类/samples/dimensionlookup-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Dimension lookup/update | DimensionLookup |
| Test Data | DataGrid |

## Hops

| From | To |
|------|----|
| Test Data | Dimension lookup/update |

## Notes

Creates a basic slowly changing dimension

*) id as key

*) name punch through (updates all previous versions)

*) street, number, zip as inserts (create new versions)

*) city as update (updates latest version without creating new version)

---
