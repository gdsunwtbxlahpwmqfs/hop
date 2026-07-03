# Pipeline: 0008-simple-mapping-child

## Basic Information

- **Pipeline Name:** 0008-simple-mapping-child
- **Source File:** `03-转换插件/云服务类/samples/0009-simple-mapping-child.hpl`

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
