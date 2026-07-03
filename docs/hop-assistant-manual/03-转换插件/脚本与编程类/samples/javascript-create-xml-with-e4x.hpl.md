# Pipeline: javascript-create-xml-with-e4x

## Basic Information

- **Pipeline Name:** javascript-create-xml-with-e4x
- **Source File:** `03-转换插件/脚本与编程类/samples/javascript-create-xml-with-e4x.hpl`

## Transforms

| Name | Type |
|------|------|
| Data grid | DataGrid |
| E4X | ScriptValueMod |

## Hops

| From | To |
|------|----|
| Data grid | E4X |

## Notes

The E4X transform creates a simple XML document for every input row.

It is using EMCAScript for XML: E4X

https://www.ecma-international.org/publications-and-standards/standards/ecma-357/

---
