# Pipeline: 0011-simple-mapping-child

## Basic Information

- **Pipeline Name:** 0011-simple-mapping-child
- **Source File:** `03-转换插件/Beam大数据类/samples/0011-simple-mapping-child.hpl`

## Transforms

| Name | Type |
|------|------|
| Input | MappingInput |
| Output | MappingOutput |
| concat | ScriptValueMod |

## Hops

| From | To |
|------|----|
| Input | concat |
| concat | Output |
