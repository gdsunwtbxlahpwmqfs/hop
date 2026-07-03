# Pipeline: 0009-blocking-transform

## Basic Information

- **Pipeline Name:** 0009-blocking-transform
- **Source File:** `03-转换插件/流程控制类/samples/0009-blocking-transform.hpl`

## Transforms

| Name | Type |
|------|------|
| 10k rows | RowGenerator |
| id | Sequence |
| set MAX_ID | ScriptValueMod |
| Blocking transform | BlockingTransform |
| get MAX_ID | ScriptValueMod |
| id<>maxId | FilterRows |
| Abort | Abort |

## Hops

| From | To |
|------|----|
| 10k rows | id |
| set MAX_ID | Blocking transform |
| id | set MAX_ID |
| Blocking transform | get MAX_ID |
| get MAX_ID | id<>maxId |
| id<>maxId | Abort |
