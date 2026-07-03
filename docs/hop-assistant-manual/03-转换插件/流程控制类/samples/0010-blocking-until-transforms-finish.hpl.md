# Pipeline: 0010-blocking-until-transforms-finish

## Basic Information

- **Pipeline Name:** 0010-blocking-until-transforms-finish
- **Source File:** `03-转换插件/流程控制类/samples/0010-blocking-until-transforms-finish.hpl`

## Transforms

| Name | Type |
|------|------|
| 1k rows | RowGenerator |
| 2k rows | RowGenerator |
| 3k rows | RowGenerator |
| Abort | Abort |
| Block until finished | BlockUntilTransformsFinish |
| get MAX_ID1,2,3 | ScriptValueMod |
| id1 | Sequence |
| id1->id | SelectValues |
| id2 | Sequence |
| id2->id | SelectValues |
| id3 | Sequence |
| id3->id | SelectValues |
| Unexpected values? | FilterRows |
| set MAX_ID1 | ScriptValueMod |
| set MAX_ID2 | ScriptValueMod |
| set MAX_ID3 | ScriptValueMod |
| last values | GroupBy |

## Hops

| From | To |
|------|----|
| 1k rows | id1 |
| id1 | set MAX_ID1 |
| Unexpected values? | Abort |
| 2k rows | id2 |
| id2 | set MAX_ID2 |
| 3k rows | id3 |
| id3 | set MAX_ID3 |
| set MAX_ID1 | id1->id |
| set MAX_ID2 | id2->id |
| set MAX_ID3 | id3->id |
| id1->id | Block until finished |
| id2->id | Block until finished |
| id3->id | Block until finished |
| Block until finished | get MAX_ID1,2,3 |
| get MAX_ID1,2,3 | last values |
| last values | Unexpected values? |
