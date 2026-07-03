# Pipeline: 0087-javascript-basics

## Basic Information

- **Pipeline Name:** 0087-javascript-basics
- **Source File:** `03-转换插件/脚本与编程类/samples/0087-javascript-basics.hpl`

## Transforms

| Name | Type |
|------|------|
| 10 rows | RowGenerator |
| min,max,avg,sum | ScriptValueMod |
| nr | Sequence |
| validate | Dummy |

## Hops

| From | To |
|------|----|
| 10 rows | nr |
| nr | min,max,avg,sum |
| min,max,avg,sum | validate |
