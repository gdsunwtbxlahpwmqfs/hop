# Pipeline: Block this transform until transforms finish

## Basic Information

- **Pipeline Name:** Block this transform until transforms finish
- **Source File:** `03-转换插件/流程控制类/samples/block-until-transforms-finish-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Fake data | Fake |
| Generate rows | RowGenerator |
| Delay row | Delay |
| Found GOD! | Dummy |
| 1 row | RowGenerator |
| Block this transform until transforms finish | BlockUntilTransformsFinish |
| Alert JS | ScriptValueMod |

## Hops

| From | To |
|------|----|
| Generate rows | Fake data |
| Fake data | Delay row |
| Delay row | Found GOD! |
| 1 row | Block this transform until transforms finish |
| Block this transform until transforms finish | Alert JS |

## Notes

Block transformation waits until Found GOD step is completely executed!

You can visualise that by dotted rectangle as both fake data and block transform transforms are running continuously.

---
