# Pipeline: 0009-build-documentation-check

## Basic Information

- **Pipeline Name:** 0009-build-documentation-check
- **Source File:** `04-动作插件/其他动作/samples/0009-build-documentation-check.hpl`

## Transforms

| Name | Type |
|------|------|
| Check Hop Doc files | ScriptValueMod |
| X | JoinRows |
| count hop files | GroupBy |
| count md files | GroupBy |
| count svg files | GroupBy |
| get md files | GetFileNames |
| get pipelines/workflows | GetFileNames |
| get svg files | GetFileNames |

## Hops

| From | To |
|------|----|
| get md files | count md files |
| get svg files | count svg files |
| get pipelines/workflows | count hop files |
| count md files | X |
| count svg files | X |
| count hop files | X |
| X | Check Hop Doc files |

## Notes

We expect one svg file per pipeline or workflow in this project:

We expect 2 extra md files for the index and metadata.

---
