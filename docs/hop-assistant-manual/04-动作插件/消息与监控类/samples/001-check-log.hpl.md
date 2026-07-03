# Pipeline: 001-check-log

## Basic Information

- **Pipeline Name:** 001-check-log
- **Source File:** `04-动作插件/消息与监控类/samples/001-check-log.hpl`

## Transforms

| Name | Type |
|------|------|
| Message not found | Abort |
| Read snmp log | TextFileInput2 |
| check for message | FilterRows |
| combine data lines | GroupBy |

## Hops

| From | To |
|------|----|
| Read snmp log | combine data lines |
| combine data lines | check for message |
| check for message | Message not found |
