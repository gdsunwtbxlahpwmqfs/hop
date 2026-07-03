# Pipeline: delay-row-basic

## Basic Information

- **Pipeline Name:** delay-row-basic
- **Source File:** `03-转换插件/流程控制类/samples/delay-row-basic.hpl`

## Transforms

| Name | Type |
|------|------|
| Wait 5s | Delay |
| 10 empty rows | RowGenerator |
| Log every 5s | WriteToLog |

## Hops

| From | To |
|------|----|
| 10 empty rows | Wait 5s |
| Wait 5s | Log every 5s |

## Notes

Delay Row

Pauze for the specified time whenever a new row is received.

When you run this example you'll see a log line appear every 5s.

You can use this to slow down output for example to prevent

an overload on certain services like mail servers, REST services and so on.

---
