# Pipeline: 0048-simple-mapping-child-01

## Basic Information

- **Pipeline Name:** 0048-simple-mapping-child-01
- **Source File:** `03-转换插件/映射与子管道类/samples/0048-simple-mapping-child-01.hpl`

## Transforms

| Name | Type |
|------|------|
| fieldA, fieldB | MappingInput |
| fieldSum | MappingOutput |
| fieldSum = fieldA+fieldB | ScriptValueMod |

## Hops

| From | To |
|------|----|
| fieldSum = fieldA+fieldB | fieldSum |
| fieldA, fieldB | fieldSum = fieldA+fieldB |
