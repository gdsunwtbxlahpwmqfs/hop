# Pipeline: javascript-cross-convert-between-julian-dates-and-iso-dates

## Basic Information

- **Pipeline Name:** javascript-cross-convert-between-julian-dates-and-iso-dates
- **Source File:** `03-转换插件/脚本与编程类/samples/javascript-cross-convert-between-julian-dates-and-iso-dates.hpl`

## Transforms

| Name | Type |
|------|------|
| Convert to and from JDE Julian CYYDDD and  ISO dates yyyy-MM-dd | ScriptValueMod |
| Test date cases | DataGrid |
| Polish Date Formats (yyyy-MM-dd) | SelectValues |

## Hops

| From | To |
|------|----|
| Test date cases | Convert to and from JDE Julian CYYDDD and  ISO dates yyyy-MM-dd |
| Convert to and from JDE Julian CYYDDD and  ISO dates yyyy-MM-dd | Polish Date Formats (yyyy-MM-dd) |

## Notes

Conversion to and from ISO and JDE style julian dates

---
