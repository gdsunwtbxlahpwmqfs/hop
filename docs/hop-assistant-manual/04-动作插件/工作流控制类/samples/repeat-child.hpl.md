# Pipeline: repeat-child

## Basic Information

- **Pipeline Name:** repeat-child
- **Source File:** `04-动作插件/工作流控制类/samples/repeat-child.hpl`

## Transforms

| Name | Type |
|------|------|
| counter + 1 | Calculator |
| counter >5 | FilterRows |
| generate dummy row | RowGenerator |
| get counter variable | GetVariable |
| set counter variable | SetVariable |
| set end loop | SetVariable |
| write counter to log | WriteToLog |

## Hops

| From | To |
|------|----|
| generate dummy row | get counter variable |
| counter + 1 | counter >5 |
| counter >5 | set counter variable |
| get counter variable | write counter to log |
| write counter to log | counter + 1 |
| counter >5 | set end loop |

## Notes

This is a child pipeline called by repeat_action.hwf

---
