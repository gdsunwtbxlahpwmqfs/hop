# Pipeline: repeat-sample1-work

## Basic Information

- **Pipeline Name:** repeat-sample1-work
- **Source File:** `04-动作插件/工作流控制类/samples/repeat-sample1-work.hpl`

## Transforms

| Name | Type |
|------|------|
| Get ${NR} | GetVariable |
| increase NR | ScriptValueMod |
| Do Work | WriteToLog |

## Hops

| From | To |
|------|----|
| Get ${NR} | Do Work |
| Do Work | increase NR |

## Notes

This pipeline can use variable NR

It increases its value in the parent.

---
