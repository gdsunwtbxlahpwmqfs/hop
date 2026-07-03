# Pipeline: sub-mapping

## Basic Information

- **Pipeline Name:** sub-mapping
- **Source File:** `03-转换插件/映射与子管道类/samples/simple-mapping-child.hpl`

## Transforms

| Name | Type |
|------|------|
| Mapping Input | MappingInput |
| Mapping Output | MappingOutput |
| calculate name | ScriptValueMod |

## Hops

| From | To |
|------|----|
| Mapping Input | calculate name |
| calculate name | Mapping Output |

## Notes

This mapping pipeline accepts a first and last name field and

concatenates it with a space in between to output the full name.

It can be re-used by other pipelines in a generic way.

---
