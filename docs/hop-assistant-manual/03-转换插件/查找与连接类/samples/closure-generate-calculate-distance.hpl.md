# Pipeline: Closure Generator - Sample

## Basic Information

- **Pipeline Name:** Closure Generator - Sample
- **Source File:** `03-转换插件/查找与连接类/samples/closure-generate-calculate-distance.hpl`

## Transforms

| Name | Type |
|------|------|
| Closure generator | ClosureGenerator |
| Hierarchical data | DataGrid |
| Sort by ids and distance | SortRows |

## Hops

| From | To |
|------|----|
| Hierarchical data | Closure generator |
| Closure generator | Sort by ids and distance |

## Notes

Generate a Reflexive Transitive Closure Table

It calculates the distance to the parent element in a hierarchy

---
